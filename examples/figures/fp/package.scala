package figures

import scala.math.Pi

package object fp {
  def area(figure: Figure) = figure match {
    case Circle(radius) => Pi * radius * radius
    case Square(side) => side * side

    // Adding a new type is hard:
    case Rectangle(a, b) => a * b
  }

  // Adding a new operation is easy:
  def circumference(figure: Figure) = figure match {
    case Circle(radius) => 2 * Pi * radius
    case Square(side) => 4 * side

    // Adding a new type is hard:
    case Rectangle(a, b) => 2 * (a + b)
  }
}
