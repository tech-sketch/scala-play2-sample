package controllers

import views.html.helper.FieldConstructor
import views.html.xenlon.helper.twitterBootstrap.xenlonTwitterBootstrapFieldConstructor

object XenlonHelpers {

  implicit val xenlonFields = FieldConstructor(xenlonTwitterBootstrapFieldConstructor.f)

}