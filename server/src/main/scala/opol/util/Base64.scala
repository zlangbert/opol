package opol.util

import opol.facades.BufferBuilder

object Base64 {

  def decode(s: String): String = {
    BufferBuilder.base64(s).toString("utf-8")
  }
}
