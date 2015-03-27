import scala.annotation.tailrec

def reverse[A](xs: List[A]): List[A] = {
  @tailrec
  def reverseHelper(xs: List[A], acc: List[A]): List[A] = xs match {
    case Nil => acc
    case x :: rest => reverseHelper(rest, x :: acc)
  }

  reverseHelper(xs, Nil)
}

def map[A, B](xs: List[A])(f: A => B): List[B] = {
  @tailrec
  def mapHelper(xs: List[A], acc: List[B]): List[B] = xs match {
    case Nil => acc
    case x :: rest => mapHelper(rest, f(x) :: acc)
  }

  reverse(mapHelper(xs, Nil))
}

def filter[A](xs: List[A])(f: A => Boolean): List[A] = {
  @tailrec
  def filterHelper(xs: List[A], acc: List[A]): List[A] = xs match {
    case Nil => acc
    case x :: rest => {
      val newAcc = if (f(x)) x :: acc else acc
      filterHelper(rest, newAcc)
    }
  }

  reverse(filterHelper(xs, Nil))
}

import fpscala.people

map(filter(people)(_.age < 25))(_.name)

people.filter(_.age < 25).map(_.name)
people.par.filter(_.age < 25).map(_.name)
people.view.filter(_.age < 25).map(_.name).take(2)

Nil == List()
1 :: List(2, 3, 4) == List(1, 2, 3, 4)
1 :: 2 :: 3 :: 4 :: Nil == List(1, 2, 3, 4)

(1 to 100).filter(_ % 15 != 0).map { x =>
  if (x % 3 == 0) "Fizz"
  else if (x % 5 == 0) "Buzz"
  else x
}

val fibs: Stream[Long] = 1 #:: 1 #:: fibs.zip(fibs.tail).map {case (x, y) => x + y}

fibs.take(20).map(x => x * x).toList

