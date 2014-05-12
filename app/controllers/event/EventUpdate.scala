package controllers.event

import play.api._
import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms._
import models.{ EventForm, Event, Events }

object EventUpdate extends Controller {

  /** イベントフォーム */
  val eventForm = Form(
    mapping(
      "eventId" -> text,
      "eventNm" -> text)(EventForm.apply)(EventForm.unapply))

  /** 初期表示 */
  def index(id: Int) = Action {
    val event = Events.findById(id)
    val form = EventForm(event.eventId, event.eventNm)
    Ok(views.html.event.eventUpdate(eventForm.fill(form), id))
  }

  /** 更新 */
  def update(id: Int) = Action { implicit request =>
    val form = eventForm.bindFromRequest.get
    val event = Event(id, form.eventId, form.eventNm)
    Events.update(event)
    Ok(views.html.event.eventUpdate(eventForm, id))
  }

}