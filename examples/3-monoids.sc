trait Monoid[T] {
  def op(a: T, b: T): T
  def zero: T
}

implicit val intAdditiveMonoid = new Monoid[Int] {
  def op(a: Int, b: Int): Int = a + b
  def zero: Int = 0
}

implicit val stringMonad = new Monoid[String] {
  def op(a: String, b: String): String = a + b
  def zero: String = ""
}

implicit def mapMonad[K, V] = new Monoid[Map[K, V]] {
  def op(a: Map[K, V], b: Map[K, V]): Map[K, V] = a ++ b
  def zero: Map[K, V] = Map()
}

// Because op is associative we may implement a sum
// that computes the value in parallell.
//
// The proper monoid implementation is choosen automatically
// by the scala compiler when sum is called
def sum[T](xs: List[T])(implicit m: Monoid[T]) = {
  xs.foldLeft(m.zero)((acc, next) => m.op(acc, next))
}

sum(List(5, 6, 7, 8, 9))
sum(List("Hello", " ", "World", "!!!"))
sum(List(
  Map(1 -> "a", 2 -> "b"),
  Map(3 -> "c", 2 -> "d", 4 -> "e")
))

val intMultiplicativeMonoid = new Monoid[Int] {
  def op(a: Int, b: Int): Int = a * b
  def zero: Int = 1
}

sum(List(5, 6, 7, 8, 9)) == 35
sum(List(5, 6, 7, 8, 9))(intMultiplicativeMonoid) == 15120