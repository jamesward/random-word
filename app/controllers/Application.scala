package controllers

import javax.inject.Inject
import play.api._
import play.api.mvc._

import scala.io.Source
import scala.util.Random

class Application @Inject()(environment: Environment) extends InjectedController {

  lazy val words = {
    val wordsStream = environment.resourceAsStream("words.txt").get
    Source.fromInputStream(wordsStream).getLines().toVector
  }

  def index = Action {
    val word = words(Random.nextInt(words.size))
    Ok(word)
  }

}
