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
  def index = Action { implicit request =>
    Ok(views.html.event.eventSearch(eventSearchForm, null))
  }

  /** 検索 */
  def search = Action { implicit request =>
    eventSearchForm.bindFromRequest.fold(
      hasErrors = { form =>
        Ok(views.html.event.eventSearch(form, null))
      },
      success = { form =>
        if (form.eventDateFrom.isDefined
          && form.eventDateTo.isDefined
          && form.eventDateFrom.get.after(form.eventDateTo.get)) {
          BadRequest(views.html.event.eventSearch(eventSearchForm.bindFromRequest
            .withError("eventDateFrom", "開催日（From）は開催日（To）より前の日付を入力してください。")
            .withError("eventDateTo", ""), null))
        } else {
          val events = Events.find(form.eventId, form.eventNm, form.eventDateFrom, form.eventDateTo)
          Ok(views.html.event.eventSearch(eventSearchForm.bindFromRequest, events))
        }
      })
  }

  /** 削除 */
  def delete(id: Int) = Action { implicit request =>
    Events.delete(id)
    Redirect(controllers.event.routes.EventSearch.index)
      .flashing("success" -> "削除しました。")
  }
}
