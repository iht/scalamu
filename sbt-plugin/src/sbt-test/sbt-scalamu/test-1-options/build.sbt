scalaVersion := "2.11.11"

libraryDependencies ++= Seq(
  "org.specs2"    %% "specs2-core" % "3.8.9" % Test,
  "org.scalatest" %% "scalatest"   % "3.0.1" % Test,
  "com.lihaoyi"   %% "utest"       % "0.4.5" % Test,
  "junit"         % "junit"        % "4.12"  % Test
)

ScalamuKeys.targetClasses := Seq("example.Foo.*".r, "example.Bar.*".r)

testOptions ++= Seq(
  Tests.Argument(TestFrameworks.ScalaTest, "-l", "tags.Ignored"),
  Tests.Argument(TestFrameworks.Specs2, "exclude", "Ignored")
)

lazy val check = TaskKey[Unit]("check")

check := {
  val reportDir = file("target/mutation-analysis-report")
  if (!reportDir.exists()) sys.error("Report directory doesn't exist.")

  val barAnnSource = reportDir / "example" / "Bar.scala.html"
  val bazAnnSource = reportDir / "example" / "Baz.scala.html"
  val fooAnnSource = reportDir / "example" / "Foo.scala.html"

  Seq(barAnnSource, fooAnnSource, bazAnnSource).foreach { f =>
    if (!f.exists()) sys.error(s"Annotated source file: ${f.getName} doesn't exist.")
  }
}