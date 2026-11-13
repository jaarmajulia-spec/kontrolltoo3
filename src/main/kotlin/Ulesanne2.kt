// Kontrolltöö 3 · Ülesanne 2 — Märgireegel on väärtus

// 1. Päeva statistika
data class DayStats(val meters: Double, val steps: Int, val minutes: Int)

// 2. Funktsioonitüübile antakse nimi: reegel võtab päeva ja ütleb jah/ei
typealias BadgeRule = (DayStats) -> Boolean

// 4. Tavaline funktsioon — antakse edasi viitena ::halfHour
fun halfHour(stats: DayStats): Boolean = stats.minutes >= 30

// 3 ja 8. Reeglid lambdana.
// it: ÜHE parameetri korral on nimi automaatselt "it" — lühike ja sobib lühikesse lambdasse.
val kilometer: BadgeRule = { it.meters >= 1000.0 }
// Nimega parameeter: "day ->" — pikemas lambdas loeb selgemini, mis objektiga tegu on,
// ja pesastatud lambdades hoiab ära it-ide segiajamise.
val thousandSteps: BadgeRule = { day -> day.steps >= 1000 }
val longDay: BadgeRule = { day -> day.meters >= 5000.0 && day.minutes >= 45 }

// 6. Tagastab nende märkide nimed, mille reegel kehtib
fun earnedBy(stats: DayStats, rules: Map<String, BadgeRule>): List<String> =
    rules.filter { (_, rule) -> rule(stats) }.keys.toList()

// 9. Teade arvutatakse AINULT siis, kui reegel kehtib: message on funktsioon, mitte valmis string
fun ifEarned(stats: DayStats, rule: BadgeRule, message: () -> String) {
    if (rule(stats)) println(message())
}

fun main() {
    // 5. Kõik neli reeglit Map-is; LinkedHashMap hoiab lisamise järjekorra
    val rules: Map<String, BadgeRule> = mapOf(
        "Kilomeeter päevas" to kilometer,
        "Tuhat sammu" to thousandSteps,
        "Pool tundi liikumist" to ::halfHour,
        "Pikk päev" to longDay,
    )

    // 7. Kolm päeva
    val days = listOf(
        DayStats(320.0, 430, 8),
        DayStats(1450.0, 1960, 31),
        DayStats(5200.0, 7020, 62),
    )
    for (d in days) {
        val names = earnedBy(d, rules)
        val text = if (names.isEmpty()) "ei ühtki" else names.joinToString(", ")
        println("${d.meters.toInt()} m, ${d.steps} sammu, ${d.minutes} min -> $text")
    }

    // 10. Viimase parameetri süntaks: lambda sulgudest väljas
    ifEarned(days[2], kilometer) { "Kolmas päev ületas kilomeetri" }
    ifEarned(days[0], kilometer) { "Seda ei trükita: esimene päev ei ületanud kilomeetrit" }

    // Reegel on väärtus: selle saab muutujasse panna ja hiljem välja kutsuda
    val held: BadgeRule = rules.getValue("Kilomeeter päevas")
    println("Hoitud reegel esimese päeva peal: ${held(days[0])}")
}
