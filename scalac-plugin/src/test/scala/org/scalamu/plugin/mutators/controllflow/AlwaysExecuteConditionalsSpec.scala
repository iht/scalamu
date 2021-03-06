package org.scalamu.plugin.mutators.controllflow

import org.scalamu.plugin.Mutator
import org.scalamu.plugin.testutil.SingleMutationSpec

class AlwaysExecuteConditionalsSpec extends SingleMutationSpec {
  override def mutation: Mutator = AlwaysExecuteConditionals

  "AlwaysExecuteConditionals" should "replace conditional block with \'then\' branch" in withScalamuCompiler {
    (global, reporter) =>
      val code =
        """
          |object Foo {
          |  val bool = false
          |  val a = 123
          |  val b = if (a < 100) {
          |    a + 1
          |  } else a - 1
          |  
          |  if (b.isWhole()) {
          |    if (a == b) {}
          |    else println(123)
          |  }
          |  
          |  class Bar(val x: Int = if (bool) 10 else 11)
          |}
        """.stripMargin
      val mutantsInfo = mutantsFor(code)(global, reporter)
      mutantsInfo should have size 4
  }
}
