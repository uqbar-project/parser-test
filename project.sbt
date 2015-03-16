name := "parser-test"

description := "Scala-test extension with matchers to test Parser Combinators"

scalaVersion := "2.11.6"

///////////////////////////////////////////////////////////////////////////////////////////////////

lazy val project = FDProject(
	"org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.3",
	"org.scalatest" %% "scalatest" % "2.2.4"
)

///////////////////////////////////////////////////////////////////////////////////////////////////

scalacOptions += "-feature"

unmanagedSourceDirectories in Compile := Seq((scalaSource in Compile).value)

unmanagedSourceDirectories in Test := Seq((scalaSource in Test).value)