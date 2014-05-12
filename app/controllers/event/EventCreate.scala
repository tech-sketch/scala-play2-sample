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
      "eventId" -> nonEmptyText.verifying(fixLength(5)),
      "eventNm" -> nonEmptyText.verifying(maxLength(5)),
      "eventDate" -> optional(sqlDate),
      "homepage" -> optional(text))(EventForm.apply)(EventForm.unapply))

  /** 初期表示 */
  def index = Action { implicit request =>
    Ok(views.html.event.eventCreate(eventForm))
  }

  /** 登録 */
  def create = Action { implicit request =>
    eventForm.bindFromRequest.fold(
      hasErrors = { form =>
        Ok(views.html.event.eventCreate(form))
      },
      success = { form =>
        val event = Event(0, form.eventId, form.eventNm, form.eventDate, form.homepage)
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
