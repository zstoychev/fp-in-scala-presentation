package figures.oop

abstract class Figure {
  def area: Double

  // Adding a new operation is hard:
  def circumference: Double
}
