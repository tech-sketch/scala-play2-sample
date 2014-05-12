package controllers.event

import play.api._
import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms._
import xenlon.api.data.validation.XenlonConstraints._
import models.{ EventSearchForm, Events }

object EventSearch extends Controller {

  /** イベントフォーム */
  val eventSearchForm = Form(
    mapping(
      "eventId" -> text.verifying(fixLength(5)),
      "eventNm" -> text.verifying(maxLength(5)),
      "eventDateFrom" -> optional(sqlDate),
      "eventDateTo" -> optional(sqlDate))(EventSearchForm.apply)(EventSearchForm.unapply))

  /** 初期表示 */
  def index = Action {
    Ok(views.html.event.eventSearch(eventSearchForm, null))
  }

  /** 検索 */
  def search = Action { implicit request =>
    eventSearchForm.bindFromRequest.fold(
      hasErrors = { form =>
        Ok(views.html.event.eventSearch(form, null))
      },
      success = { form =>
        val events = Events.find(form.eventId, form.eventNm, form.eventDateFrom, form.eventDateTo)
        Ok(views.html.event.eventSearch(eventSearchForm.bindFromRequest, events))
      })
  }

  /** 削除 */
  def delete(id: Int) = Action {
    Events.delete(id)
    Redirect(controllers.event.routes.EventSearch.index)
  }
}
