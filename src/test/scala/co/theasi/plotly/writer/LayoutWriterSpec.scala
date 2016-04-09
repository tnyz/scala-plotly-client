package co.theasi.plotly.writer

import org.json4s._
import org.json4s.native.JsonMethods._

import org.scalatest._

import co.theasi.plotly._

class LayoutWriterSpec extends FlatSpec with Matchers {
  "Layout.toJson" should "serialize axis domains" in {
    val layout = GridLayout(1, 2)
    val x0Domain = layout.xAxes(0).domain
    val x1Domain = layout.xAxes(1).domain
    val jobj = LayoutWriter.toJson(layout)
    (jobj \ "xaxis" \ "domain")(0) shouldEqual
      JDouble(x0Domain._1)
    (jobj \ "xaxis" \ "domain")(1) shouldEqual
      JDouble(x0Domain._2)
    (jobj \ "xaxis2" \ "domain")(0) shouldEqual
      JDouble(x1Domain._1)
    (jobj \ "xaxis2" \ "domain")(1) shouldEqual
      JDouble(x1Domain._2)
    List("yaxis", "yaxis2").foreach { axis =>
      (jobj \ axis \ "domain")(0) shouldEqual
        JDouble(0.0)
      (jobj \ axis \ "domain")(1) shouldEqual
        JDouble(1.0)
    }
  }

  it should "serialize axis anchors" in {
    val layout = GridLayout(1, 2)
    val jobj = LayoutWriter.toJson(layout)
    (jobj \ "xaxis" \ "anchor") shouldEqual JString("y")
    (jobj \ "xaxis2" \ "anchor") shouldEqual JString("y2")
    (jobj \ "yaxis" \ "anchor") shouldEqual JString("x")
    (jobj \ "yaxis2" \ "anchor") shouldEqual JString("x2")
  }

  it should "serialize axis titles" in {
    val layout = SingleAxisLayout().xAxisOptions(AxisOptions().title("hello"))
    val jobj = LayoutWriter.toJson(layout)
    (jobj \ "xaxis" \ "title") shouldEqual JString("hello")
    (jobj \ "yaxis" \ "title") shouldEqual JNothing
  }

  it should "serialize legend options" in {
    val layout = SingleAxisLayout().legend(LegendOptions()
      .x(0.5)
      .y(0.6)
      .xAnchor(XAnchor.Left)
      .yAnchor(YAnchor.Top))
    val jobj = LayoutWriter.toJson(layout) \ "legend"
    jobj \ "x" shouldEqual JDouble(0.5)
    jobj \ "y" shouldEqual JDouble(0.6)
    jobj \ "xanchor" shouldEqual JString("left")
    jobj \ "yanchor" shouldEqual JString("top")
  }

  it should "not include 'legend' if the LegendOptions are empty" in {
    val layout = SingleAxisLayout()
    val jobj = LayoutWriter.toJson(layout) \ "legend"
    jobj shouldEqual JNothing
  }
}
