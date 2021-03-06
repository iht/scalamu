package org.scalamu.testutil

import cats.scalatest.{ValidatedMatchers, ValidatedValues}
import org.scalamu.core.api.ClassInfo
import org.scalamu.core.utils.{ASMUtils, ClassLoadingUtils, FileSystemUtils}
import org.scalatest._
import org.scalatest.concurrent.ScalaFutures

trait ScalamuSpec
    extends FlatSpec
    with Matchers
    with Inspectors
    with Inside
    with OptionValues
    with EitherValues
    with TryValues
    with ScalaFutures
    with FileSystemUtils
    with FileSystemSpec
    with ASMUtils
    with ClassLoadingUtils
    with TestSuiteResultMatchers
    with ValidatedValues
    with ValidatedMatchers {

  override def spanScaleFactor: Double = 20.0

  def classInfoForName(
    name: String
  )(implicit loader: ClassLoader = contextClassLoader, pos: org.scalactic.source.Position): ClassInfo =
    loadClassFileInfo(loader.getResourceAsStream(name)).success.value
}
