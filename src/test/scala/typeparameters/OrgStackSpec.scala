package typeparameters

import com.seung.typeparameters.{EmptyStackOrg$, StackOrg}
import org.scalatest.{DiagrammedAssertions, FlatSpec}

class OrgStackSpec extends FlatSpec with DiagrammedAssertions {

  "Stack" should "FILO" in {
    val stack = StackOrg()
    val stackPushed1 = stack.push(1)
    val stackPushed2 = stackPushed1.push(2)
    val (popped2, stackPopped2) = stackPushed2.pop
    val (popped1, stackPopped1) = stackPopped2.pop

    assert(popped2 === 2)
    assert(popped1 === 1)
    assert(stackPopped1 === EmptyStackOrg$)
  }

  "isEmpty function" should "whether return empty or not" in {
    val stack = StackOrg()
    assert(stack.isEmpty === true)
    val stackPushed1 = stack.push(1)
    assert(stackPushed1.isEmpty === false)
    val (_, stackPopped1) = stackPushed1.pop
    assert(stackPopped1.isEmpty === true)
  }

  "EmptyStack" should "throw an exception when it pops" in {
    intercept[IllegalArgumentException] {
      StackOrg().pop
    }
  }
}
