package co.theasi.plotly.writer

import co.theasi.plotly.{AxisOptions, ThreeDPlot, ViewPort}
import org.json4s._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ThreeDPlotLayoutWriterSpec extends AnyFlatSpec with Matchers {

  val defaultViewPort = ViewPort((0.0, 0.5), (0.1, 0.6))

  "toJson" should "serialize scenes with the right domain" in {
    val axisIndex = 2
    val plot = ThreeDPlot()
    val jobj = ThreeDPlotLayoutWriter.toJson(axisIndex, defaultViewPort, plot)

    (jobj \ "scene2" \ "domain" \ "x") shouldEqual JArray(
      List(JDouble(0.0), JDouble(0.5)))
    (jobj \ "scene2" \ "domain" \ "y") shouldEqual JArray(
      List(JDouble(0.1), JDouble(0.6)))
  }

  it should "serialize axis options" in {
    val axisIndex = 2
    val axisOptions = AxisOptions().title("hello-x")

    val plot = ThreeDPlot().xAxisOptions(axisOptions)
    val jobj = ThreeDPlotLayoutWriter.toJson(axisIndex, defaultViewPort, plot)

    (jobj \ "scene2" \ "xaxis") shouldEqual AxisOptionsWriter.toJson(axisOptions)

  }

}
