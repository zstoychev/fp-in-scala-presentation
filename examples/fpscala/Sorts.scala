package fpscala

object Sorts {
  import scala.math.Ordered

  def quickSort[T <% Ordered[T]](xs: List[T]): List[T] = xs match {
    case Nil => Nil
    case x :: rest => {
      val (before, after) = rest partition { _ < x }
      quickSort(before) ++ (x :: quickSort(after))
    }
  }

  def mergeSort[T <% Ordered[T]](xs: List[T]): List[T] = {
    def merge(xs: List[T], ys: List[T]): List[T] = (xs, ys) match {
      case (Nil, _) => ys
      case (_, Nil) => xs
      case (x :: xrest, y :: yrest) =>
        if (x < y) x :: merge(xrest, ys)
        else y :: merge(xs, yrest)
    }

    if (xs.length == 0) xs
    else {
      val (left, right) = xs splitAt (xs.length / 2)
      merge(mergeSort(left), mergeSort(right))
    }
  }
}
