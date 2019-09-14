package testing

import com.seung.testing.Calc
import org.scalatest.{DiagrammedAssertions, FlatSpec}
import org.scalatest.time.SpanSugar._
import org.scalatest.concurrent.TimeLimits._
import org.scalatestplus.mockito.MockitoSugar
import org.mockito.Mockito._

class CalcSpec extends FlatSpec with DiagrammedAssertions with MockitoSugar {
  val calc = Calc()

  "sum function" should "整数の配列を取得し、それらを足し合わせた整数を返すことができる" in {
    assert(calc.sum(Seq(1, 2, 3)) === 6)
    assert(calc.sum(Seq(0)) === 0)
    assert(calc.sum(Seq(-1, 1)) === 0)
    assert(calc.sum(Seq()) === 0)
  }

  it should "Intの最大を上回った際にはオーバーフローする" in {
    assert(calc.sum(Seq(Integer.MAX_VALUE, 1)) === Integer.MIN_VALUE)
  }

  "div function" should "Received two integer, the value that molecule divided by denominator should return float numbers" in {
    assert(calc.div(6, 3) === 2.0)
  }

  it should "Divided by zero should throw a runtime exception" in {
    intercept[ArithmeticException] {
      calc.div(1, 0)
    }
  }

  "isPrime function" should "the value return to be prime number or not" in {
    assert(calc.isPrime(0) === false)
    assert(calc.isPrime(-1) === false)
    assert(calc.isPrime(2))
    assert(calc.isPrime(17))
  }

  it should "Performance Test: About one million's prime numbers could finish to process within 1 sec" in {
    failAfter(1000 millis) {
      assert(calc.isPrime(9999991))
    }
  }

  "Calc Mock Object" should "imitate properly" in {
    val mockCalc = mock[Calc]
    when(mockCalc.sum(Seq(3, 4, 5))).thenReturn(12)
    assert(mockCalc.sum(Seq(3, 4, 5)) === 12)
  }
}
