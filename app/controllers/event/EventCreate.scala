package controllers.event

import play.api._
import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms._
import models.EventForm

object EventCreate extends Controller {

  /** イベントフォーム */
  val eventForm = Form(
    mapping(
      "eventId" -> text,
      "eventNm" -> text)(EventForm.apply)(EventForm.unapply))

  /** 初期表示 */
  def index = Action {
    Ok(views.html.event.eventCreate(eventForm))
  }
}