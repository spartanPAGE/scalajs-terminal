package terminal

import org.scalajs.dom
import org.scalajs.dom.raw.HTMLElement

case class RenderTarget(id: String) {
  def element(): HTMLElement = dom.document.getElementById(id).asInstanceOf[HTMLElement]
}

object RenderTarget {
  def create(parent: RenderTarget, tag: String, id: String = "", classes: String = "", innerHTML: String = ""): RenderTarget = {
    val target_element = dom.document.createElement(tag)
    target_element.id = id
    parent.element().appendChild(target_element)

    val target = target_element.asInstanceOf[HTMLElement]
    target.className = classes
    target.innerHTML = innerHTML
    RenderTarget(id)
  }
}
