package terminal

import scala.collection.mutable.Queue
import scala.scalajs.js.JSApp
import scalatags.Text.all._
import org.scalajs.dom
import org.scalajs.dom.raw.HTMLElement
import scala.scalajs.js.Dynamic.global
import scala.scalajs.js

case class RenderTarget(id: String) {
  def element() = dom.document.getElementById(id).asInstanceOf[HTMLElement]
}

case class TerminalConfig(interval: Double)

class Terminal(implicit target: RenderTarget) {
  private val lineMarker = "terminal-current-line"

  case class LineCharactersQueue() extends Queue[Char]

  case class LinesQueue() extends Queue[LineCharactersQueue]

  private var buffer = LinesQueue()

  type Line = HTMLElement

  def defaultLineContainer() = dom.document.createElement("h1")

  var currentLineIndex = 0

  def currentLine(): Line = target.element.childNodes.item(currentLineIndex).asInstanceOf[Line]

  def pushNewLine(): Unit = {
    buffer.enqueue(LineCharactersQueue())
    target.element.appendChild(defaultLineContainer)
  }

  def println(text: String) = {
    pushNewLine()
    (text+'\n').foreach { char => buffer.front.enqueue(char) }
  }

  def lookForCharInQueue(): Option[Char] = {
    val line = buffer.front
    if(line.isEmpty) {
      buffer.dequeue
      return nextCharacter
    }
    Some(line.dequeue)
  }

  def nextCharacter(): Option[Char] = {
    if (buffer.isEmpty) return None else lookForCharInQueue
  }

  def intervalAction(): Unit = {
    nextCharacter match {
      case Some(char) => char match {
        case '\n' => currentLineIndex += 1
        case char => currentLine.innerHTML += char
      }
      case _ =>
    }
  }

  def start()(implicit config: TerminalConfig) = {
    dom.window.setInterval(() => intervalAction, config.interval)
  }
}

object App extends JSApp {
  implicit val target = RenderTarget(id = "scala-render-target")
  implicit val terminalConfig = TerminalConfig(interval = 100)

  def main(): Unit = {
    run()
    render()
  }

  def run() = {
    val terminal = new Terminal()
    terminal.start()


    terminal.println("Hello world!")
    terminal.println("------------")
    terminal.println("------------")
  }

  def render() = {
    target.element.className += " glow"
    //target.element.innerHTML = content.render
  }

  def content() = {
    //h1("-- terminal --"),
    //p("test")
  }
}
