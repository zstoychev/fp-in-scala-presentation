package figures.oop

// Adding a new type is easy:
case class Rectangle(a: Double, b: Double) extends Figure{
  def area: Double = a * b

  // Adding a new operation is hard:
  def circumference: Double = 2 * (a + b)
}
