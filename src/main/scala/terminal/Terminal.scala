package terminal

import org.scalajs.dom
import org.scalajs.dom.raw.HTMLElement

import scala.collection.mutable

case class TerminalConfig(target: RenderTarget, interval: Double)



object Terminal {
  val keystrokePath = "assets/audios/keystroke.ogg"

  val terminalRenderTargetId = "terminal-render-target"

  def createTarget(parent: RenderTarget, tag: String): RenderTarget = {
    val target = dom.document.createElement(tag)
    target.id = terminalRenderTargetId
    parent.element().appendChild(target)
    RenderTarget(terminalRenderTargetId)
  }
}

class Terminal(implicit config: TerminalConfig) {

  val buffer = new mutable.Queue[Char]()

  def println(text: String): Unit = {
    (text + '\n').foreach({ char => buffer.enqueue(char) })
  }

  def nextCharacter(): Option[Char] = {
    if(buffer.isEmpty) None else Some(buffer.dequeue())
  }

  def consumeCharacter(character: Char): Unit = {
    config.target.element().innerHTML += (character match {
      case '\n' => "<br>"
      case char    => char
    })

    jsbindings.Audio.sound(Terminal.keystrokePath)
  }

  def intervalAction(): Unit = nextCharacter() match {
    case Some(char) => consumeCharacter(char)
    case _          =>
  }

  def start()(implicit config: TerminalConfig): Handle = {
    Handle(id = dom.window.setInterval(() => intervalAction(), config.interval))
  }
}