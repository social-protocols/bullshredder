// https://mill-build.com

import mill._, scalalib._, scalajslib._

import $repo.`https://oss.sonatype.org/content/repositories/snapshots`
import $repo.`https://oss.sonatype.org/content/repositories/public`

import mill.scalajslib._
import mill.scalajslib.api._

trait AppScalaModule extends ScalaModule {
  def scalaVersion = "2.13.14"
  val versions     = new {}
}

trait AppScalacOptions extends ScalaModule {
  val isCi = sys.env.get("CI").contains("true")

  def scalacOptions = T {
    super.scalacOptions() ++ Seq(
      "-encoding",
      "utf8",
      "-unchecked",
      "-deprecation",
      "-feature",
      "-language:higherKinds",
      "-Wnonunit-statement",
      "-Wunused:imports,privates,params,locals,implicits,explicits",
      // default imports in every scala file. we use the scala defaults + chaining + cps for direct syntax with lift/unlift/!
      // https://docs.scala-lang.org/overviews/compiler-options/
      // "-Yimports:java.lang,scala,scala.Predef,scala.util.chaining,cps.syntax.monadless,cps.monads.catsEffect",
    ) ++ Option.when(isCi)("-Xfatal-warnings")
  }
}

trait AppScalaJSModule extends AppScalaModule with ScalaJSModule {
  def scalaJSVersion = "1.16.0"

  def scalacOptions = T {
    // TODO: vite serves source maps from the out-folder. Fix the relative path to the source files:
    // TODO: for production builds, point sourcemap to github
    super.scalacOptions() ++ Seq(s"-scalajs-mapSourceURI:${T.workspace.toIO.toURI}->../../../.")
  }
}

object lib extends Module {
  trait SharedModule extends AppScalaModule with AppScalacOptions with PlatformScalaModule {
    def ivyDeps = super.ivyDeps() ++ Agg(
      ivy"com.lihaoyi::upickle::4.0.0", // json
      ivy"com.lihaoyi::requests:0.9.0", // http requests
      ivy"com.github.andyglow::scala-jsonschema::0.7.11",
      ivy"com.github.andyglow::scala-jsonschema-ujson::0.7.11",
    )
  }
  object jvm extends SharedModule
  // object js  extends SharedModule with AppScalaJSModule
}
