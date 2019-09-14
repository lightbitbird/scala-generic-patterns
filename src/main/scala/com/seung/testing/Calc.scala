package com.seung.testing

// How to check code coverages ==============
// add following codes at project/plugins.sbt
// ----------------------------
// logLevel := Level.Warn
// addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.x.x")]
//
// add at build.sbt
// ----------------------------
// scalacOptions ++= Seq("-encoding", "UTF-8")
// ----------------------------
//
// $ sbt clean coverage test
// $ sbt coverageReport
// ==========================================

// How to check style =======================
// add following codes at project/plugins.sbt
// ----------------------------
// addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "1.x.x")
//
// add at build.sbt
// ----------------------------
// scalastyleSources in Compile := (unmanagedSourceDirectories in Compile).value
//
// $ sbt scalastyleGenerateConfig # once at first time only
// $ sbt scalastyle
// ==========================================

object Calc {
  def apply(): Calc = new Calc()
}

class Calc {

  def sum(seq: Seq[Int]): Int = seq.foldLeft(0)(_ + _)

  def div(numerator: Int, denominator: Int): Double = {
    if (denominator == 0) throw new ArithmeticException("/ by zero")
    numerator.toDouble / denominator.toDouble
  }

  // 整数値を受け取り、その値が素数であるかどうかを返す
  def isPrime(n: Int): Boolean = {
    if (n < 2) false else !((2 to math.sqrt(n).toInt) exists (n % _ == 0))
  }
}

