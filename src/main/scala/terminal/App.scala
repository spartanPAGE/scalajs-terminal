package terminal

import scala.scalajs.js.JSApp
import scalatags.Text.all._
import org.scalajs.dom

object App extends JSApp {
  def main(): Unit = {
    val target = dom.document.getElementById("scala-render-target")
    target.innerHTML = render()
  }

  def render() = {
    content().render
  }

  def content() = {
    h1("hello world!")
  }
}
