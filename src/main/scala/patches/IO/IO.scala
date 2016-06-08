package patches.IO

trait IO[T] {
  val name: String
  def update(value: T): Unit
}