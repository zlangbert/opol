package opol

package object facades {

  implicit class BufferOps(b: Buffer) {

    def take(n: Int): Buffer = {
      b.slice(0, n)
    }

    def takeRight(n: Int): Buffer = {
      b.slice(-n)
    }

    def drop(n: Int): Buffer = {
      b.slice(n)
    }

    def dropRight(n: Int): Buffer = {
      b.slice(0, -n)
    }
  }
}
