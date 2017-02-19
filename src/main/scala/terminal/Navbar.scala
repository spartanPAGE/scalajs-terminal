package terminal

import org.scalajs.dom

case class NavbarConfig(target: RenderTarget, elementTag: String, elementClasses: String, elements: Seq[String])

class Navbar(implicit config: NavbarConfig) {
  config.elements.foreach(element => {
    RenderTarget.create(parent=config.target, tag=config.elementTag, classes=config.elementClasses, innerHTML = element)
  })
}