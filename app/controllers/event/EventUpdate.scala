package controllers.event

import play.api._
import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms._
import xenlon.api.data.validation.XenlonConstraints._
import models.{ EventForm, Event, Events }

object EventUpdate extends Controller {

  /** イベントフォーム */
  val eventForm = Form(
    mapping(
      "eventId" -> nonEmptyText.verifying(fixLength(5)),
      "eventNm" -> nonEmptyText.verifying(maxLength(5)),
      "eventDate" -> optional(sqlDate),
      "homepage" -> optional(text))(EventForm.apply)(EventForm.unapply))

  /** 初期表示 */
  def index(id: Int) = Action {
    val event = Events.findById(id)
    val form = EventForm(event.eventId, event.eventNm,  event.eventDate, event.homepage)
    Ok(views.html.event.eventUpdate(eventForm.fill(form), id))
  }

  /** 更新 */
  def update(id: Int) = Action { implicit request =>
    eventForm.bindFromRequest.fold(
      hasErrors = { form =>
        Ok(views.html.event.eventUpdate(form, id))
      },
      success = { form =>
        val event = Event(id, form.eventId, form.eventNm, form.eventDate, form.homepage)
        Events.update(event)
        Redirect(controllers.event.routes.EventUpdate.index(id))
      })
  }

}
