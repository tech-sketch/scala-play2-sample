package controllers.event

import play.api._
import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms._
import models.{ EventForm, Events }

object EventSearch extends Controller {

  /** イベントフォーム */
  val eventForm = Form(
    mapping(
      "eventId" -> text,
      "eventNm" -> text)(EventForm.apply)(EventForm.unapply))

  /** 初期表示 */
  def index = Action {
    Ok(views.html.event.eventSearch(eventForm, null))
  }

  /** 検索 */
  def search = Action { implicit request =>
    val form = eventForm.bindFromRequest
    val events = Events.find(form.get.eventId, form.get.eventNm)
    Ok(views.html.event.eventSearch(form, events))
  }

  /** 削除 */
  def delete(id: Int) = Action { implicit request =>
    Events.delete(id)
    Ok(views.html.event.eventSearch(eventForm, null))
  }
}