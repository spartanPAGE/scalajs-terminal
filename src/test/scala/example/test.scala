package example

import utest._

object Test extends TestSuite {
  def tests = TestSuite {
    'HelloWorld {
      assert(true)
    }
  }
}
