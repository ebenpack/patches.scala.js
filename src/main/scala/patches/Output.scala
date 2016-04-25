package patches

import scala.reflect.ClassTag


class Output[A](name: String)(implicit val tag: ClassTag[A])  {

}