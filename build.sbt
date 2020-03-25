scalaVersion := "2.13.1"
libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.5.30"
libraryDependencies += "com.typesafe.akka" %% "akka-discovery" % "2.5.30"
libraryDependencies += "com.typesafe.akka" %% "akka-http" % "10.1.11"
libraryDependencies +=  "com.typesafe.akka" %% "akka-http2-support" % "10.1.11"
libraryDependencies +=  "com.typesafe.akka" %% "akka-http2-support" % "10.1.11"

scalacOptions += "-Ymacro-annotations"
enablePlugins(AkkaGrpcPlugin)