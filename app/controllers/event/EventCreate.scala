package controllers.event

import play.api._
import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms._
import xenlon.api.data.validation.XenlonConstraints._
import models.{ EventForm, Event, Events }

object EventCreate extends Controller {

  /** イベントフォーム */
  val eventForm = Form(
    mapping(
      "eventId" -> nonEmptyText,
      "eventNm" -> nonEmptyText.verifying(maxLength(5)))(EventForm.apply)(EventForm.unapply))

  /** 初期表示 */
  def index = Action {
    Ok(views.html.event.eventCreate(eventForm))
  }

  /** 登録 */
  def create = Action { implicit request =>
    eventForm.bindFromRequest.fold(
      hasErrors = { form =>
        Ok(views.html.event.eventCreate(form))
      },
      success = { form =>
        val event = Event(0, form.eventId, form.eventNm)
        Events.create(event)
        Redirect(controllers.event.routes.EventCreate.index)
      })
  }

  /** テーブル作成 */
  def createTable = Action {
    Events.createTable
    Ok(views.html.event.eventCreate(eventForm))
  }

  /** テーブル削除 */
  def dropTable = Action {
    Events.dropTable
    Ok(views.html.event.eventCreate(eventForm))
  }

}
