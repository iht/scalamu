package org.scalamu.testapi.specs2

import org.scalamu.core.ClassInfo
import org.scalamu.testapi.{SuiteResultTypeConverter, TestRunner, TestSuiteResult}
import org.specs2.control
import org.specs2.reporter.NotifierPrinter
import org.specs2.runner.Runner
import org.specs2.specification.core.{Env, SpecificationStructure}
import org.specs2.specification.process.Stats

import scala.util.Try

final case class InternalSpecs2Error(message: String) extends RuntimeException(message)

class Specs2Runner extends TestRunner[Stats] {
  private val notifier = new Specs2Notifier
  override protected val converter: SuiteResultTypeConverter[Stats] = new Specs2Converters(
    notifier
  )

  override def run(testClass: ClassInfo): TestSuiteResult = {
    val suite      = testClass.name
    val suiteClass = suite.loadFromContextClassLoader
    val spec       = suiteClass.flatMap(cl => Try(cl.newInstance().asInstanceOf[SpecificationStructure]))
    val suiteResult = spec.map { s =>
      val errorOrStats = control.runAction(
        Runner.runSpecStructure(
          s.is,
          Env(),
          Thread.currentThread().getContextClassLoader,
          List(NotifierPrinter.printer(notifier))
        )
      )
      errorOrStats.fold(
        err =>
          err.fold(
            TestSuiteResult.Aborted(suite, _),
            desciption => TestSuiteResult.Aborted(suite, InternalSpecs2Error(desciption))
        ),
        converter.fromResult(suite)
      )
    }
    suiteResult.fold(
      TestSuiteResult.Aborted(suite, _),
      identity
    )
  }
}