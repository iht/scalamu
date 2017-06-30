package org.scalamu.testapi.scalatest

import org.scalamu.testapi._
import org.scalatest.{Status, Suite, WrapWith}

class ScalaTestFramework(override val arguments: String) extends TestingFramework {
  type R = Status

  override def name: String          = "ScalaTest"
  override def runner: TestRunner[R] = new ScalaTestRunner
  
  override def classFilter: TestClassFilter = new CompositeTestClassFilter(
    new SuperclassBasedFilter(classOf[Suite], this) with NotAModule with HasNoArgConstructor with NotAbstract,
    new SuperclassBasedFilter(classOf[WrapWith], this) with NotAModule with NotAbstract
  )
}

object ScalaTestFramework extends ScalaTestFramework("")
