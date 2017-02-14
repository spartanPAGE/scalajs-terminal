package terminal

import org.scalajs.dom
import org.scalajs.dom.raw.{Element, HTMLElement}

import scala.collection.mutable


case class TerminalConfig(interval: Double)

object Terminal {
  def keystrokePath(): String = "assets/audios/keystroke.ogg"
}

class Terminal(implicit target: RenderTarget) {

  case class LineCharactersQueue() extends mutable.Queue[Char]

  case class LinesQueue() extends mutable.Queue[LineCharactersQueue]

  private val buffer = LinesQueue()

  type Line = HTMLElement

  def defaultLineContainer(): Element = dom.document.createElement("h1")

  var currentLineIndex = 0

  def currentLine(): Line = target.element().childNodes.item(currentLineIndex).asInstanceOf[Line]

  def pushNewLine(): Unit = {
    buffer.enqueue(LineCharactersQueue())
    target.element().appendChild(defaultLineContainer())
  }

  def println(text: String): Unit = {
    pushNewLine()
    (text + '\n').foreach({ char => buffer.front.enqueue(char) })
  }

  def lookForCharInQueue(): Option[Char] = {
    val line = buffer.front
    if(line.isEmpty) {
      buffer.dequeue
      return nextCharacter()
    }
    Some(line.dequeue)
  }

  def nextCharacter(): Option[Char] = {
    if (buffer.isEmpty) None else lookForCharInQueue()
  }

  def consumeCharacter(character: Char): Unit = {
    currentLine().innerHTML += character
    jsbindings.Audio.sound(Terminal.keystrokePath())
  }

  def intervalAction(): Unit = {
    nextCharacter() match {
      case Some(char) => char match {
        case '\n' => currentLineIndex += 1
        case c => consumeCharacter(c)
      }
      case _ =>
    }
  }

  def start()(implicit config: TerminalConfig): Handle = {
    Handle(id = dom.window.setInterval(() => intervalAction(), config.interval))
  }
}