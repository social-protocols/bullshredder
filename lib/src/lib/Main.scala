package lib

import json._
import json.schema.description
import upickle.default.{macroRW, ReadWriter => RW}

case class InitialGuesses(
  ad_hominem: Double,
  ad_populum: Double,
  appeal_to_anger: Double,
  appeal_to_false_authority: Double,
  appeal_to_fear: Double,
  appeal_to_nature: Double,
  appeal_to_pity: Double,
  appeal_to_positive_emotion: Double,
  appeal_to_ridicule: Double,
  appeal_to_tradition: Double,
  appeal_to_worse_problems: Double,
  causal_oversimplification: Double,
  circular_reasoning: Double,
  equivocation: Double,
  fallacy_of_division: Double,
  false_analogy: Double,
  false_causality: Double,
  false_dilemma: Double,
  guilt_by_association: Double,
  hasty_generalization: Double,
  slippery_slope: Double,
  straw_man: Double,
  tu_quoque: Double,
)

object InitialGuesses {
  implicit val rw: RW[InitialGuesses] = macroRW
}

case class Foo(
  detected_language: String,
  @description("Rough guesses which fallacies could be present in the content. Value between 0.1 - 1.00")
  initial_guesses: InitialGuesses,
)
object Foo {
  implicit val rw: RW[Foo]     = macroRW
  val schema: json.Schema[Foo] = Json.schema[Foo]
}

object Main {
  def main(args: Array[String]): Unit = {
    println(detect("I'm a circle, therefore you are a square."))
  }

  def detect(text: String): Foo = {
    val prompt =
      """detect logical fallacies in the text
        |Don't judge if the stated claims are true or false, only explain why the way of reasoning is not valid.
        |""".stripMargin
    llm.jsonCall(
      llm.Request(
        model = "gpt-4o-mini",
        messages = Vector(llm.Message(role = "system", content = prompt), llm.Message(role = "user", text)),
        response_format = llm.ResponseFormat.jsonSchema(Foo.schema),
      )
    )
  }
}
