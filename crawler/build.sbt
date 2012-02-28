// set the name of the project
name := "My Project"

version := "1.0"

organization := "net.coscolla.guiaTv.crawler"

// set the main Scala source directory to be <base>/src
scalaSource in Compile <<= baseDirectory(_ / "src")

libraryDependencies += "nu.validator.htmlparser" % "htmlparser" % "1.2.1"

libraryDependencies += "com.mongodb.casbah" %% "casbah" % "2.1.5-1"

libraryDependencies += "org.slf4j" % "slf4j-api" % "1.6.4"

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.0.0"

libraryDependencies += "net.liftweb" %% "lift-json" % "2.4"

libraryDependencies += "joda-time" % "joda-time" % "1.6" 
