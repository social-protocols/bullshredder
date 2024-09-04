package lib

import com.github.andyglow.jsonschema.AsU
import json.schema.Version.Draft12
import ujson.Obj
import upickle.default.{macroR, macroRW, macroW, ReadWriter => RW, Reader => R, Writer => W}

package object llm {
  case class ResponseFormat[T](
    `type`: String,
    json_schema: ResponseSchema[T],
  )
  object ResponseFormat {
    def jsonSchema[T](schema: json.Schema[T], name: String) = ResponseFormat[T](
      `type` = "json_schema",
      json_schema = ResponseSchema(name = name, schema = schema, strict = true),
    )
    implicit def w[T]: W[ResponseFormat[T]] = macroW
  }

  case class ResponseSchema[T](name: String, schema: json.Schema[T], strict: Boolean)
  object ResponseSchema {
    implicit def w[T]: W[ResponseSchema[T]] =
      upickle.default.writer[Obj].comap(x => Obj("name" -> x.name, "schema" -> AsU.UJsonSchemaOps(x.schema).asU(Draft12("TODO:URL"))))
  }

  case class Request[T](
    model: String,
    messages: Vector[Message],
    response_format: ResponseFormat[T],
  )
  object Request {
    implicit def w[T]: W[Request[T]] = macroW
  }

  case class Response(
    id: String,
    // `object`: String,
    created: Int,
    model: String,
    choices: Vector[Choice],
  )
  object Response {
    implicit val rw: R[Response] = macroR
  }

  case class Choice(
    message: Message
  )
  object Choice {
    implicit val rw: RW[Choice] = macroRW
  }

  case class Message(
    role: String,
    content: String,
  )
  object Message {
    implicit val rw: RW[Message] = macroRW
  }

  def jsonCall[T: RW](request: Request[T]): T = {
    // read API Key from Environment variable
    val apiKey = sys.env("OPENAI_API_KEY")
    val url    = "https://api.openai.com/v1/chat/completions"
    val headers = Map(
      "Content-Type"  -> "application/json",
      "Authorization" -> s"Bearer $apiKey",
    )

    println(upickle.default.write(request, indent = 1))
    val httpResponse =
      requests.post(url, headers = headers, data = upickle.default.write(request), readTimeout = 60000)
    val response = upickle.default.read[Response](httpResponse.text())
    println(response.choices.head.message.content)
    upickle.default.read[T](response.choices.head.message.content)
  }
  //
  // case class ChatHistory(history: Vector[String]) {
  //   def sendSnippet(command: String): ChatHistory = {
  //     var newHistory = history
  //     newHistory :+= command
  //     // call GPT Api
  //     val answer = callGPTAPI(history.mkString("\n"))
  //     newHistory :+= answer
  //     copy(history = newHistory)
  //   }
  //
  // }
  //
  // object ChatHistory {
  //   def apply(): ChatHistory = ChatHistory()
  // }
}
