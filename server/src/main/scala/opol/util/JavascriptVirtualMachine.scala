package opol.util

import opol.facades.vm.Vm

import scala.scalajs.js

class JavascriptVirtualMachine {

  def run[T <: js.Object](code: String): T = {
    runDynamic(code).asInstanceOf[T]
  }

  def runDynamic(code: String): js.Dynamic = {

    val vm = Vm()

    val context = vm.createContext()
    val script = Vm.Script(code)

    script.runInContext(context)

    context.asInstanceOf[js.Dynamic]
  }
}
