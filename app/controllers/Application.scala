package controllers

import play.api._
import play.api.mvc._

import scala.io.Source
import scala.util.Random

object Application extends Controller {

  lazy val words = {
    val wordsStream = Play.current.resourceAsStream("words.txt").get
    Source.fromInputStream(wordsStream).getLines().toVector
  }

  def index = Action {
    val word = words(Random.nextInt(words.size))
    Ok(word)
  }

}