package org.scalamu.core.workers

import java.io.{Console => _, _}
import java.net.Socket

import io.circe.Encoder
import io.circe.syntax._
import org.scalamu.core.runners._

trait Worker[R] {
  type Result = R

  type Configuration

  def name: String = getClass.getName.dropRight(1)

  protected def execute(
    args: Array[String]
  )(implicit encoder: Encoder[R]): Unit =
    tryWith(new Socket("localhost", args.head.toInt)) { socket =>
      val dis = new DataInputStream(new BufferedInputStream(socket.getInputStream))
      val dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream))

      val parseConfig = readConfigurationFromParent(dis)

      parseConfig match {
        case Left(parseError) =>
          Console.err.println(s"Error parsing worker configuration. $parseError")
          die(ExitCode.RuntimeFailure)
        case Right(config) =>
          run(config, dis).foreach(result => { dos.writeUTF(result.asJson.noSpaces); dos.flush() })
      }
    }

  protected def readCompiledSources(dis: DataInputStream): Map[String, Array[Byte]] =
    Iterator.continually {
      val name   = dis.readUTF()
      val length = dis.readInt()
      dis.readUTF()
      val bytes = Array.ofDim[Byte](length)
      dis.readFully(bytes)
      name -> bytes
    }.toMap

  protected def readConfigurationFromParent(dis: DataInputStream): Either[Throwable, Configuration]

  protected def run(config: Configuration, dis: DataInputStream): Iterator[R]
}
