package terminal

import org.scalajs.dom
import org.scalajs.dom.raw.HTMLElement

import scala.collection.mutable

case class TerminalConfig(target: RenderTarget, interval: Double)

object Terminal {
  val keystrokePath      = "assets/audios/keystroke.ogg"
  val keystrokeEnterPath = "assets/audios/keystroke-enter.ogg"

  val terminalRenderTargetId = "terminal-render-target"

  def createTarget(parent: RenderTarget, tag: String): RenderTarget = {
    RenderTarget.create(parent, tag, terminalRenderTargetId, "")
  }
}

class CountingIgnorer {
  var count = 0

  def shouldIgnore(): Boolean = count != 0
  def increase(by: Int): Unit = count += by
  def unignoreOne(): Unit = count -= 1
}

class Terminal(implicit config: TerminalConfig) {

  val buffer = new mutable.Queue[Char]()

  val intervalIgnorer = new CountingIgnorer()

  def println(text: String): Unit = {
    (text + '\n').foreach({ char => buffer.enqueue(char) })
  }

  def nextCharacter(): Option[Char] = {
    if (buffer.isEmpty) None else Some(buffer.dequeue())
  }

  def playKeystrokeSound(character: Char): Unit = jsbindings.Audio.sound(character match {
    case '\n' => Terminal.keystrokeEnterPath
    case char => Terminal.keystrokePath
  })

  def ignoreIntervalsCountForGivenChar(character: Char): Int = character match {
    case '\n' => 3
    case _    => 0
  }

  def consumeCharacter(character: Char): Unit = {
    config.target.element().innerHTML += (character match {
      case '\n' => "<br>"
      case char => char
    })

    playKeystrokeSound(character)
    intervalIgnorer.increase(by = ignoreIntervalsCountForGivenChar(character))
  }

  def intervalAction(): Unit = {
    if(intervalIgnorer.shouldIgnore()) {
      intervalIgnorer.unignoreOne()
      return
    }

    nextCharacter() match {
      case Some(char) => consumeCharacter(char)
      case _          =>
    }
  }

  def start()(implicit config: TerminalConfig): Handle = {
    Handle(id = dom.window.setInterval(() => intervalAction(), config.interval))
  }
}