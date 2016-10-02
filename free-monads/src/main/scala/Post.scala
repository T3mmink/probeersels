import cats.free.Free


/**
  * Trying to understand free monads using
  * http://blog.scalac.io/2016/06/02/overview-of-free-monad-in-cats.html
  *
  * @tparam T
  */
trait Container[T]
case class StringContainer(value: String) extends Container[String]

val lifted: Free[Container, String] = Free.liftF[Container, String](StringContainer("foo"))



