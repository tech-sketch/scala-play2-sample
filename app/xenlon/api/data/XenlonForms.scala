package xenlon.api.data

import play.api.data.Forms._
import play.api.data.Mapping
import xenlon.api.data.validation.XenlonConstraints._

object XenlonForms {
  val url: Mapping[String] = text verifying isUrl
}