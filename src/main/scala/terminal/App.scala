package terminal

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport


object App extends JSApp {
  implicit val root = RenderTarget(id = "scala-render-target")
  implicit val terminalConfig = TerminalConfig(Terminal.createTarget(parent=root, tag="h1"), interval = 80)

  val terminal = new Terminal()

  @JSExport
  def println(text: String): Unit = terminal.println(text)

  def main(): Unit = {
    run()
    render()
  }

  def run(): Unit = {
    jsbindings.Audio.ambient("assets/audios/10 Minutes of Ambient Computer Sounds - Retro_SciFi Medley.mp3")

    terminal.start()
    terminal.println("Go on,")
    terminal.println("""Use terminal.App().println("test")""");
  }

  def render(): Unit = {
    root.element().className += " glow"
    //target.element.innerHTML = content.render
  }

  //def content(): TypedTag[String] = {
  //h1("-- terminal --"),
  //p("test")
  //}
}
