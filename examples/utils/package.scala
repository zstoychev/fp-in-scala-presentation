package object utils {
  def time[T](run: => T) = {
    val startTime = System.currentTimeMillis
    val result = run

    System.currentTimeMillis - startTime
  }

  lazy val fibs: Stream[Long] = 1 #:: 1 #:: fibs.zip(fibs.tail).map({case (a, b) => a + b})
}
