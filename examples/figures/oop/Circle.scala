package figures.oop

import scala.math.Pi

class Circle(val radius: Double) extends Figure {
  def area: Double = Pi * radius * radius

  // Adding a new operation is hard:
  def circumference: Double = 2 * Pi * radius
}
