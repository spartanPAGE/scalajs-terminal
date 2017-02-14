package terminal

import org.scalajs.dom
import org.scalajs.dom.raw.HTMLElement

case class RenderTarget(id: String) {
  def element(): HTMLElement = dom.document.getElementById(id).asInstanceOf[HTMLElement]
}
