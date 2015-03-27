val option = Some(4)
val none: Option[Int] = None

(option orElse Some(5)) == Some(4)
(none orElse Some(5)) == Some(5)
(option getOrElse 5) == 4
(none getOrElse 5) == 5

(option map { x => x + 3 }) == Some(7)
(none map { x => x + 3 }) == None

def f(a: Int): Option[Int] = Some(2 + a)
def g(a: Int): Option[Int] = Some(3 + a)
def h(a: Int): Option[Int] = Some(4 + a)

// But we can no longer compose like this: h(g(f(2))).

// A solution, similar to using null:
def doSomething(data: Int): Option[Int] = {
  val fResult = f(data)

  if (fResult != None) {
    val gResult = g(fResult.get)

    if (gResult != None) {
      val hResult = h(gResult.get)

      if (hResult != None) {
        Some(fResult.get + gResult.get + hResult.get)
      } else None
    } else None
  } else None
}

// We will solve this using flatMap and monads