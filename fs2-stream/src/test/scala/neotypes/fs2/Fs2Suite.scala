package neotypes.fs2

import cats.effect.IO
import neotypes.{Stream, StreamSuite, StreamTestkit}
import neotypes.cats.effect.IOTestkit
import neotypes.fs2.implicits._
import scala.concurrent.ExecutionContext

/** Implementation of the Stream Teskit for fs2. */
object Fs2Testkit extends StreamTestkit[Fs2IoStream, IO](IOTestkit) {
  override def createBehaviour(implicit ec: ExecutionContext): Behaviour =
    new Behaviour {
      override def streamToFList[T](stream: Fs2IoStream[T]): IO[List[T]] =
        stream.compile.toList

      override final val streamInstance: Stream.Aux[Fs2IoStream, IO] =
        implicitly
    }
}

/** Execute all the stream specs using fs2. */
final class Fs2Suite extends StreamSuite(Fs2Testkit)
