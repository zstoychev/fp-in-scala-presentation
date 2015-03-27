// See the implementations of the monad type class
// for different types in fpscala/Monad.scala
import fpscala.Monad

// Option monads
def f(a: Int): Option[Int] = Some(2 + a)
def g(a: Int): Option[Int] = Some(3 + a)
def h(a: Int): Option[Int] = Some(4 + a)
def i(a: Int): Option[Int] = None

// But we can no longer compose like this: h(g(f(2)))
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

val some3 = Some(3)
val none: Option[Int] = None
some3.flatMap(x => Some(x + 4)) == Some(7)
none.flatMap(x => Some(x + 4)) == None
some3.flatMap(x => None) == None
some3.map(x => x + 4) == Some(7)
none.map(x => x + 3) == None

def doSomething2(data: Int): Option[Int] = {
  f(data).flatMap { fResult =>
    g(fResult).flatMap { gResult =>
      h(gResult).map { hResult =>
        fResult + gResult + hResult
      }
    }
  }
}

def doSomething3(data: Int): Option[Int] = for {
  fResult <- f(data)
  gResult <- g(fResult)
  hResult <- h(gResult)
} yield fResult + gResult + hResult

doSomething(2) == doSomething2(2)
doSomething(2) == doSomething3(2)
Monad.sequence(List(f(2), g(2), h(2))) == Some(List(4, 5, 6))
Monad.sequence(List(f(2), g(2), None, h(2))) == None
Monad.sequence(List(f(2), i(2), h(2))) == None

// List monads
val listFlatmap = List(1, 2, 3).flatMap(x => List(x + 3, x + 4))
listFlatmap ==  List(1, 2, 3).map(x => List(x + 3, x + 4)).flatten
listFlatmap == List(4, 5, 5, 6, 6, 7)

List[Int]().flatMap(x => List(x + 3, x + 4)) == List() // == Nil
val forList = for {
  a <- List(1, 2, 3)
  b <- List(3, 4, 5)
  if (a + b < 6) // Can be used with all monads with a filter function, aka a Monad with zero. Option, List and Future have it.
  c <- List(5, 6, 7)
} yield a + b + c
forList == List(9, 10, 11, 10, 11, 12, 10, 11, 12)

// Future monads
import fpscala.WS
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Success, Failure}

val endResult = for {
  a <- WS.get("http://mydomain/service1")
  if (a < 10000)
  b <- WS.get("http://mydomain/service2", a)
  c <- WS.get("http://mydomain/service3", b)
} yield a + b + c

// endResult is of type Future[Int]
// If all service calls pass successfully it will eventually contain the sum of all responses,
// otherwise it will contain the error of the first failed service call (the subsequent ones won't be executed)

// Execute calls in parallell:
// Future will be completed when all calls are completed
val totalResult = Monad.sequence(List(WS.get("http://mydomain/service1"),
  WS.get("http://mydomain/service2"),
  WS.get("http://mydomain/service3")))
  
totalResult onComplete {
  case Success(results) => results.sum
  case Failure(e) => 0
}