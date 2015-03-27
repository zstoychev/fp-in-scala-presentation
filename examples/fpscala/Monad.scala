package fpscala

import scala.concurrent.{Future, ExecutionContext}
import scala.util.Try

trait Monad[F[_]] {
  def flatMap[A, B](m: F[A])(f: A => F[B]): F[B]
  def unit[A](a: => A): F[A]

  def compose[A, B, C](f: A => F[B], g: B => F[C]): A => F[C] = a => flatMap(f(a))(g)
  def map[A, B](m: F[A])(f: A => B): F[B] = flatMap(m)(x => unit(f(x)))
  def map2[A, B, C](ma: F[A], mb: F[B])(f: (A, B) => C): F[C] = flatMap(ma)(a => map(mb)(b => f(a, b)))
}

object Monad {
  implicit def futureMonad(implicit ec: ExecutionContext) = new Monad[Future] {
    def flatMap[A, B](m: Future[A])(f: (A) => Future[B]): Future[B] = m.flatMap(f)
    def unit[A](a: => A): Future[A] = Future(a)
  }

  implicit val listMonad = new Monad[List] {
    def flatMap[A, B](m: List[A])(f: (A) => List[B]): List[B] = m.flatMap(f)
    def unit[A](a: => A): List[A] = List(a)
  }

  implicit val optionMonad = new Monad[Option] {
    def flatMap[A, B](m: Option[A])(f: (A) => Option[B]): Option[B] = m.flatMap(f)
    def unit[A](a: => A): Option[A] = Some(a)
  }

  implicit val tryMonad = new Monad[Try] {
    def flatMap[A, B](m: Try[A])(f: (A) => Try[B]): Try[B] = m.flatMap(f)
    def unit[A](a: => A): Try[A] = Try(a)
  }

  // The monad implementation is passed to the function automatically by the scala compiler
  def sequence[F[_], A](xs: List[F[A]])(implicit m: Monad[F]): F[List[A]] = {
    xs.foldRight(m.unit(List[A]()))((acc, next) => m.map2(acc, next)(_ :: _))
  }
}
