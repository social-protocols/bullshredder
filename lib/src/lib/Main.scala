package lib

import json._
import json.schema.description
import upickle.default.{macroRW, ReadWriter => RW}

// sealed trait FallacyName
// object FallacyName {
//   case object AdHominem                extends FallacyName
//   case object AdPopulum                extends FallacyName
//   case object AppealToAnger            extends FallacyName
//   case object AppealToFalseAuthority   extends FallacyName
//   case object AppealToFear             extends FallacyName
//   case object AppealToNature           extends FallacyName
//   case object AppealToPity             extends FallacyName
//   case object AppealToPositiveEmotion  extends FallacyName
//   case object AppealToRidicule         extends FallacyName
//   case object AppealToTradition        extends FallacyName
//   case object AppealToWorseProblems    extends FallacyName
//   case object CausalOversimplification extends FallacyName
//   case object CircularReasoning        extends FallacyName
//   case object Equivocation             extends FallacyName
//   case object FallacyOfDivision        extends FallacyName
//   case object FalseAnalogy             extends FallacyName
//   case object FalseCausality           extends FallacyName
//   case object FalseDilemma             extends FallacyName
//   case object GuiltByAssociation       extends FallacyName
//   case object HastyGeneralization      extends FallacyName
//   case object SlipperySlope            extends FallacyName
//   case object StrawMan                 extends FallacyName
//   case object TuQuoque                 extends FallacyName
//
// }

// case class InitialGuesses(
//   ad_hominem: Double,
//   ad_populum: Double,
//   appeal_to_anger: Double,
//   appeal_to_false_authority: Double,
//   appeal_to_fear: Double,
//   appeal_to_nature: Double,
//   appeal_to_pity: Double,
//   appeal_to_positive_emotion: Double,
//   appeal_to_ridicule: Double,
//   appeal_to_tradition: Double,
//   appeal_to_worse_problems: Double,
//   causal_oversimplification: Double,
//   circular_reasoning: Double,
//   equivocation: Double,
//   fallacy_of_division: Double,
//   false_analogy: Double,
//   false_causality: Double,
//   false_dilemma: Double,
//   guilt_by_association: Double,
//   hasty_generalization: Double,
//   slippery_slope: Double,
//   straw_man: Double,
//   tu_quoque: Double,
// )
// object InitialGuesses {
//   implicit val rw: RW[InitialGuesses] = macroRW
// }

case class FallacyDefinition(
  name: String,
  @description("Be very concise and precise.")
  definition: String,
)
object FallacyDefinition {
  implicit val rw: RW[FallacyDefinition] = macroRW
}

case class Analysis(
  // fallacy_name: String,
  // @description("The fallacy exists. If not, invent a definition.")
  // fallacy_definition: String,
  detected_language: String,
  // @description(
  //   "one of: Slippery Slope, Hasty Generalization, False Analogy, Guilt by Association, Causal Oversimplification, Ad Populum, Circular Reasoning, Appeal to Fear, Ad Hominem, Appeal to (False) Authority, False Causality, Fallacy of Division, Appeal to Ridicule, Appeal to Worse Problems, Appeal to Nature, False Dilemma, Straw Man, Appeal to Anger, Appeal to Positive Emotion, Equivocation, Appeal to Tradition, Appeal to Pity, Tu Quoque"
  //   // "one of: Slippery Slope, Guilt by Association, Ad Populum, Appeal to Fear, Ad Hominem, Appeal to (False) Authority, False Causality, Fallacy of Division, Appeal to Ridicule, Appeal to Worse Problems, Appeal to Nature, Straw Man, Appeal to Anger, Appeal to Positive Emotion, Equivocation, Appeal to Tradition, Appeal to Pity, Tu Quoque"
  // )
  // name: String,
  @description(
    "relevant substring of original text"
  )
  quote: String,
  // Your last sentence must be a conclusion naming the fallacy and if it is present or not.
  @description(
    "Make the strongest case for the fallacy being present in the text. Be very concise."
    // Don't judge if the stated claims are true or false, only explain why the way of reasoning is not valid.
  )
  case_for_fallacy_being_present: String,
  @description(
    "Make the strongest case against the fallacy being present in the text. Be very concise."
    // Don't judge if the stated claims are true or false, only explain why the way of reasoning is not valid.
  )
  case_against_fallacy_being_present: String,
  // blatant_oversight_in_case_for_fallacy_being_present: String,
  // blatant_oversight_in_case_against_fallacy_being_present: String,
  @description("Which case is stronger?")
  conclusion: String,
  present: Boolean,
  @description(
    "Explain why the given fallacy is present and how this leads to a wrong conclusion. Use simple language. Be concise. Write in the language of the content you analyze."
  )
  explanation: String,
  @description(
    "Improve and simplify the previous explanation. Use simple language. Be concise. Write in the language of the content you analyze."
  )
  improved_explanation: String,
  // Write in the language of the content you analyze. Use simple language. Be very concise.
  // @description("Is the given analysis valid, helpful and objective?")
  // criticism: String,
  // // @description("criticise the analysis. Explain why it is not useful.")
  // // analysis_criticism: String,
  // @description(
  //   "The previous analysis was written by a 4th grader who wants to improve at detecting fallacies. Give him a grade [0.0 to 1.0] so that he can improve."
  // )
  // quality_of_analysis: Double,
  // @description("Read your analysis and tell me your confidence of the fallacy being present (between 0.0 and 1.0).")
  // strength_of_presence: Double,
  // @description("concise summary of analysis and explanation.")
  // summary: String,
  // present: Boolean,
)
object Analysis {
  implicit val rw: RW[Analysis]     = macroRW
  val schema: json.Schema[Analysis] = Json.schema[Analysis]
}

case class FallacyDetection(
  // @description("Rough guesses which fallacies could be present in the content. Value between 0.1 - 1.00")
  // initial_guesses: InitialGuesses,
  @description("list all fallacies which might be present in the text and therefore must be analyzed.")
  relevant_fallacies: Vector[FallacyDefinition]
  // @description("Analyze if the fallacies are present.")
  // analyses: Vector[Analysis],
)
object FallacyDetection {
  implicit val rw: RW[FallacyDetection]     = macroRW
  val schema: json.Schema[FallacyDetection] = Json.schema[FallacyDetection]
}

object Main {
  def main(args: Array[String]): Unit = {
    val texts = Vector(
      // "The energy transition can only work with nuclear power plants",
      // "Fragt mal die Franzosen im Hochsommer oder zu Dürreperioden... :D Ohne Kühlwasser in den Flüssen müssen die abgestellt werden, düdüm"
      "Die Risikobewertung und -statistik spielt eine besonders wichtige Rolle, wenn es darum geht, die Gefahren der Kernenergie neutral zu bewerten. Sie erfolgt auf der Grundlage bisheriger Reaktorunfälle und/oder Modellrechnungen. Bisher gab es 3 schwere Reaktorunfälle: Tschernobyl, Fukushima und Three Miles Island. Insbesondere im Fall von Tschernobyl wäre ohne den Einsatz hunderter Feuerwehrleute der gesamte europäische Kontinent für Jahrtausende für die Landwirtschaft unbrauchbar gewesen. Würden wir die gesamte Energieversorgung der Welt ausschließlich auf Kernenergie stützen, hätten wir statistisch gesehen alle paar Jahre einen solchen Unfall. Insbesondere in korrupten Ländern ist die Nutzung der Kernenergie daher nicht zu verantworten, da der größte Kostenfaktor der Kernenergie die Reaktorsicherheit ist und diese ohne unabhängige Kontrollinstanzen nicht gewährleistet werden kann. Die ungeklärte Entsorgung des Atommülls spielt eine weitere entscheidende Rolle, auf die ich hier nicht weiter eingehen möchte. Weitere gute Informationen und Quellenangaben finden sich in diesem Artikel: https://blog.rootsofprogress.org/devanney-on-the-nuclear-flop"
      // "Christoph_Ploss:_Sie_sagn,_nur_mit_Elektroautos_kann_man_die_Klimaziele_erreichen._Eigentlich_soll_es_in_Zukunft_nur_noch_Elektroautos_auf_Deutschlands_Straßen_geben._Und_da_sage_ich_Ihnen_eines,_Sie_begeben_sich_mit_dieser_Politik_auf_einen_gefährlichen_Irrweg._Ein_E_-Auto,_das_mit_Braunkohlestrom_betrieben_wird,_das_rettet_nicht_das_Weltklima,_sondern_ein_solches_E_-Auto,_das_schadet_dem_Klima._Und_das_ist_ein_idologischer_Irrsinn,_den_Sie_da_begehen."
      // "Jane, who is a known liar, says we should invest in renewable energy. If we listen to her, we'll end up bankrupt. Therefore, we shouldn't invest in renewable energy.",
      // "Tom supports the new education reform, just like those radical activists. We've always followed the traditional education system. Therefore, we shouldn't support the new education reform.",
      // "Mary argues for better healthcare, but she doesn't even exercise. Besides, we have bigger issues like unemployment. Therefore, we shouldn't focus on improving healthcare.",
      // "Most people believe that you either support the government or you're a traitor. Therefore, if you don't support the government, you're a traitor.",
      // "Environmentalists say we should stop all industrial activities immediately, which would lead to massive job losses. Think of all the families that would suffer. Therefore, we shouldn't listen to environmentalists.",
      // "Er sagt, wir sollen weniger Zucker essen. Er will also, dass wir nur noch Gemüse essen.",
      // "Alice says we should recycle more, but she's just a high school dropout. Therefore, we shouldn't recycle more.",
      // "John says climate change is real, but so does that corrupt politician. Therefore, climate change isn't real.",
      // "Bob says smoking is unhealthy, but I've seen him smoke. Therefore, smoking isn't unhealthy.",
      // "I'm furious that we have to pay so much in taxes! Therefore, high taxes are wrong.",
      // "Dr. Smith, a renowned physicist, says astrology is true. Therefore, astrology is true.",
      // "Millions of people believe in UFOs. Therefore, UFOs must exist.",
      // "If we don't increase security, our country will be invaded. Therefore, we must increase security.",
      // "Organic foods are natural. Therefore, organic foods are better than non-organic foods.",
      // "We should let John pass the exam because his dog just died.",
      // "We've always celebrated this festival in this way. Therefore, we should continue to celebrate it in this way.",
      // "The economy is bad because of the new tax law. (ignoring other factors like global markets, inflation, etc.)",
      // "The Bible is true because it says so in the Bible.",
      // "A feather is light. What is light cannot be dark. Therefore, a feather cannot be dark. (using two different meanings of 'light')",
      // "You're either with us or against us. (ignoring other possible stances)",
      // "I met two rude people from New York. Therefore, everyone from New York is rude.",
      // "Every time I wash my car, it rains. Therefore, washing my car causes it to rain.",
      // "Sure, the environment is important, but we have bigger issues like poverty. Therefore, we shouldn't focus on the environment.",
      // "If we allow same-sex marriage, next people will want to marry their pets. Therefore, we shouldn't allow same-sex marriage.",
      // "Vegetarians say no one should eat meat because animals are more important than people. But that's ridiculous. Therefore, vegetarianism is wrong.",
      // "This new policy will make everyone happy. Therefore, we should implement it.",
      // "Employees are like nails. Just as nails must be hit on the head to make them work, so must employees.",
      // "You think we should build a colony on Mars? What a silly idea! Therefore, we shouldn't build a colony on Mars.",
      // "The team is good at defense. Therefore, each player on the team must be good at defense.",
    )
    val text = texts(util.Random.nextInt(texts.size))
    println(text)
    val result: FallacyDetection = detect(text)
    pprint.pprintln(result)
    result.relevant_fallacies.foreach { f =>
      // Vector(
      //   FallacyDefinition(
      //     "Circular Reasoning",
      //     "Circular reasoning is a logical fallacy where the conclusion is used as a premise to support itself, essentially asserting that \"X is true because X is true.\"",
      //   )
      // ).foreach { f =>
      println(f)
      pprint.pprintln(analyze(text, f))
    }
    // result.analyses.foreach(a => assert(text contains a.quote, a.quote))
  }

  def detect(text: String): FallacyDetection = {
    // val prompt = """analyze if fallacies are present or not in the text. Test for all fallacies"""
    llm.jsonCall(
      llm.Request(
        model = "gpt-4o-mini",
        seed = Some(42),
        top_p = Some(0.000000001),
        temperature = Some(0),
        messages = Vector(
          // llm.Message(role = "system", content = prompt),
          llm.Message(role = "user", text)
        ),
        response_format = llm.ResponseFormat.jsonSchema(FallacyDetection.schema),
      )
    )
  }

  def analyze(text: String, fallacy: FallacyDefinition): Analysis = {
    val prompt = s"analyze if the given fallacy  is present or not: ${upickle.default.write(fallacy)} "
    llm.jsonCall(
      llm.Request(
        model = "gpt-4o-mini",
        // model = "gpt-4o-2024-08-06",
        seed = Some(42),
        top_p = Some(0.000000001),
        temperature = Some(0),
        messages = Vector(
          llm.Message(role = "system", content = prompt),
          llm.Message(role = "user", text),
        ),
        response_format = llm.ResponseFormat.jsonSchema(Analysis.schema),
      )
    )
  }
}
