package models

import java.sql.Date

case class EventForm(eventId: String, eventNm: String, eventDate: Option[Date], homepage: Option[String])
case class EventSearchForm(eventId: String, eventNm: String, eventDateFrom: Option[Date], eventDateTo: Option[Date])