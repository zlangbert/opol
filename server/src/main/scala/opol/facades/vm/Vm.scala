package opol.facades.vm

import opol.util.Node

import scala.scalajs.js

@js.native
trait Vm extends js.Object {

  def createContext(): Vm.Context = js.native
}

object Vm {

  val _vm = Node.require("vm")

  def apply(): Vm = _vm.asInstanceOf[Vm]

  /**
    * A contextified sandbox. Is a javascript object
    */
  @js.native
  trait Context extends js.Object

  /**
    * A piece of code to be run in a vm
    */
  @js.native
  trait Script extends js.Object {

    def runInContext(context: Vm.Context): Unit = js.native
  }

  object Script {
    def apply(code: String): Script = {
      js.Dynamic.newInstance(Vm._vm.Script)(code).asInstanceOf[Script]
    }
  }
}
