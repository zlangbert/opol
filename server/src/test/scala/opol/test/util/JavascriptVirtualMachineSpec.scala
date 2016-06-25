package opol.test.util

import opol.util.JavascriptVirtualMachine
import org.scalatest._

import scala.scalajs.js

class JavascriptVirtualMachineSpec extends FlatSpec with Matchers {

  "The JavascriptVirtualMachine" should "execute a script" in {

    val code =
      """
        |var a = 5;
      """.stripMargin

    val vm = new JavascriptVirtualMachine
    val context = vm.run[JavascriptVirtualMachineSpec.Result](code)

    context.a shouldEqual 5
  }
}

object JavascriptVirtualMachineSpec {

  @js.native
  trait Result extends js.Object {
    val a: Int
  }
}
