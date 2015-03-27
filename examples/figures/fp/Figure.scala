package figures.fp

sealed abstract class Figure
case class Circle(radius: Double) extends Figure
case class Square(side: Double) extends Figure

// Adding a new type is hard:
case class Rectangle(a: Double, b: Double) extends Figure