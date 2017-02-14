package terminal

import scala.scalajs.js.JSApp


object App extends JSApp {
  implicit val target = RenderTarget(id = "scala-render-target")
  implicit val terminalConfig = TerminalConfig(interval = 80)

  def main(): Unit = {
    run()
    render()
  }

  def run(): Unit = {
    val terminal = new Terminal()
    terminal.start()

    jsbindings.Audio.ambient("assets/audios/10 Minutes of Ambient Computer Sounds - Retro_SciFi Medley.mp3")


    terminal.println("Liberty Prime is online.")
    terminal.println("All systems nominal.")
    terminal.println("Weapons hot.")
    terminal.println(" Mission: the destruction of any and ")
    terminal.println("all Chinese communists.")
  }

  def render(): Unit = {
    target.element().className += " glow"
    //target.element.innerHTML = content.render
  }

  //def content(): TypedTag[String] = {
  //h1("-- terminal --"),
  //p("test")
  //}
}
