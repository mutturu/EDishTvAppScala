import org.scalatest.funsuite.AnyFunSuite

class SimpleTestSuite extends AnyFunSuite {

  test("This should pass") {
    println("hello")
    assert(true)
  }
  test("This is ")
  {
    assertResult(true)(true)
  }

}