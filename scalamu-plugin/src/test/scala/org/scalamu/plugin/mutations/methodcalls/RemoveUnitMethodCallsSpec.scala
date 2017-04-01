package org.scalamu.plugin.mutations.methodcalls

import org.scalamu.plugin.{Mutation, SingleMutationSpec}

class RemoveUnitMethodCallsSpec extends SingleMutationSpec {

  override def mutations: Seq[Mutation] = List(RemoveUnitMethodCalls)

  "RemoveUnitMethodCalls" should "remove calls to methods returning Unit" in withScalamuCompiler {
    implicit global =>
      val code =
        """
          |object Foo {
          |  println(1)
          |  
          |  def foo[A](a: A): Unit = ???
          |  
          |  List(1, 2, 3).foreach { x => }
          |  
          |  var mut = 0
          |  Some(1).map(x => mut += x)
          |  
          |  foo("123")
          |  
          |}
        """.stripMargin
      val mutationsInfo = mutationsFor(code)
      mutationsInfo should have size 6
  }
}