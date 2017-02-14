package jsbindings

import scala.scalajs.js
import scala.scalajs.js.annotation.JSName

@js.native
@JSName("Audio")
class Audio() extends js.Object {
  var autoplay: Boolean = js.native
  var controls: Boolean = js.native
  var loop: Boolean = js.native
  var muted: Boolean = js.native
  var src: String = js.native
  var currentTime: Int = js.native
  var volume: Int = js.native

  def play(): Unit = js.native
  def pause(): Unit = js.native
}