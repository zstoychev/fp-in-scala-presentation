package figures.oop

class Square(val side: Double) extends Figure {
  def area: Double = side * side

  // Adding a new operation is hard:
  def circumference: Double = 4 * side
}
