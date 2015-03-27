package fpscala

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, blocking}
import scala.util.Random

// Simulates web service call
object WS {
  def get(url: String, data: Int = 0): Future[Int] = {
    val r = Random.nextInt(1000)
    val delay = r + 1000
    val result = r + data

    Future {
      blocking {
        // Just for the demonstration. Don't block like this in real code!
        Thread.sleep(delay);
        result
      }
    }
  }
}
