package opol.test.util

import opol.facades.Buffer
import opol.test.helpers.VirtualFs
import opol.util.Files
import org.scalatest._
import org.scalatest.concurrent.ScalaFutures

import scala.concurrent.ExecutionContext

class FilesSpec extends AsyncFlatSpec with Matchers with ScalaFutures
  with VirtualFs {

  override implicit def executionContext: ExecutionContext =
    scala.concurrent.ExecutionContext.Implicits.global

  "Files" should "read a file" in {
    withFs { implicit fs =>

      fs.writeFileSync("./foo.txt", Buffer.from("abc123"))
      Files.read("./foo.txt").map { b =>
        b.toString("utf-8") shouldEqual "abc123"
      }
    }
  }

  it should "throw on non-existent file" in {
    withFs { implicit fs =>

      Files.read("./foo.txt").map { b =>
        b.toString("utf-8") shouldEqual "abc123"
      }.map(_ => fail("future should be failure")).recover {
        case _: Files.FilesException => succeed
      }
    }
  }
}
