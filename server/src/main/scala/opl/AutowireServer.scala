package opl

import upickle._

object AutowireServer extends autowire.Server[String, Reader, Writer] {

  def read[Result : Reader](p: String): Result = {
    upickle.read[Result](p)
  }
  def write[Result : Writer](r: Result): String = {
    upickle.write(r)
  }
}