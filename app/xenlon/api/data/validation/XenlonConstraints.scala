package xenlon.api.data.validation

import play.api.data.validation.{ Constraints, Constraint, Valid, Invalid, ValidationError }

object XenlonConstraints extends Constraints {
  override def maxLength(length: Int): Constraint[String] = Constraint[String]("constraint.maxLength", length) { o =>
    if (o.codePointCount(0, o.size) <= length) Valid else Invalid(ValidationError("error.maxLength", length))
  }
  def fixLength(length: Int): Constraint[String] = Constraint[String]("constraint.fixLength", length) { o =>
    if (o.isEmpty() || o.codePointCount(0, o.size) == length) Valid else Invalid(ValidationError("error.fixLength", length))
  }
}