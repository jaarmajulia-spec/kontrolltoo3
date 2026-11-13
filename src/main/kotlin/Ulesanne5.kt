// Kontrolltöö 3 · Ülesanne 5 — Saavutuste mudel ja seis

import java.util.Locale

// 1. Saavutus = latt
data class Achievement(val code: String, val name: String, val target: Double)

// 2. Seis on kolm ERI asja, mitte kaks lippu
sealed interface AchievementState {
    data object Locked : AchievementState
    data class InProgress(val percent: Double) : AchievementState
    data class Earned(val collected: Double) : AchievementState
}

// 3. Seis TULETATAKSE kogutud väärtusest, teda ei salvestata
fun stateOf(a: Achievement, collected: Double?): AchievementState = when {
    collected == null -> AchievementState.Locked
    collected >= a.target -> AchievementState.Earned(collected)
    else -> AchievementState.InProgress(100.0 * collected / a.target)
}

// 4. Ammendav when ILMA else-ta: uus seis teeb selle mittetäielikuks
fun describe(state: AchievementState): String = when (state) {
    AchievementState.Locked -> "lukus"
    is AchievementState.InProgress -> "pooleli ${state.percent.toInt()}%"
    is AchievementState.Earned -> "TEENITUD (${state.collected.toInt()})"
}

// Edenemine protsentides, kuni 100; lukus = 0
fun progressOf(a: Achievement, collected: Double?): Double =
    if (collected == null) 0.0 else (100.0 * collected / a.target).coerceAtMost(100.0)

// 9. Vigane tekst annab null, mitte erindi
fun targetFromText(text: String): Double? = runCatching { text.toDouble() }.getOrNull()

// 11. Laiendus: teenitud saavutuste koodid; tühi sisend -> tühi loend
fun List<Achievement>.earnedCodes(collected: Map<String, Double>): List<String> =
    filter { stateOf(it, collected[it.code]) is AchievementState.Earned }
        .map { it.code }

fun main() {
    // 5. Viis saavutust; RING jaoks kogutud väärtust ei ole
    val achievements = listOf(
        Achievement("KM1", "Esimene kilomeeter", 1000.0),
        Achievement("KM5", "Viis kilomeetrit", 5000.0),
        Achievement("S10K", "Kümme tuhat sammu", 10000.0),
        Achievement("MIN30", "Pool tundi", 30.0),
        Achievement("RING", "Terve linnaosa", 100.0),
    )
    val collected: Map<String, Double> = mapOf(
        "KM1" to 1450.0,
        "KM5" to 3400.0,
        "S10K" to 12800.0,
        "MIN30" to 21.0,
    )

    // 6. Iga saavutuse rida
    println("Saavutused:")
    for (a in achievements) {
        val state = stateOf(a, collected[a.code])
        println("  ${a.code.padEnd(7)}${a.name.padEnd(21)}latt %8d  %s".format(a.target.toInt(), describe(state)))
    }

    // 7. Teenitute arv ja osakaal
    val states = achievements.map { stateOf(it, collected[it.code]) }
    val earnedCount = states.count { it is AchievementState.Earned }
    val share = 100.0 * earnedCount / achievements.size
    println("Teenitud $earnedCount / ${achievements.size} ehk ${"%.1f".format(Locale.ROOT, share)}%")

    // 8. Kolm edenenumat; sortedByDescending on stabiilne, võrdsed jäävad algjärjekorda
    println("Edenemise järgi kolm esimest:")
    achievements
        .sortedByDescending { progressOf(it, collected[it.code]) }
        .take(3)
        .forEach { a ->
            val p = progressOf(a, collected[a.code])
            println("  ${a.code.padEnd(7)}${"%5.1f".format(Locale.ROOT, p)}%")
        }

    // 10. Korrektne ja vigane sisend
    for (text in listOf("1500", "viisteist")) {
        println("Latt tekstist '$text': ${targetFromText(text)}")
    }

    // 12. Teenitud koodid; tühja tulemuse korral selgesõnaline tekst
    val codes = achievements.earnedCodes(collected)
    println("Teenitud koodid: ${codes.joinToString(", ").ifEmpty { "ei ühtki" }}")

    // 13. Tühi sisend: ükski kontroll pole vaja, sest emptyList() ei ole null
    val fromEmpty = emptyList<Achievement>().earnedCodes(collected)
    println("Tühja sisendi tulemus: ${fromEmpty.joinToString(", ").ifEmpty { "ei ühtki" }}")

    // 14. Kokkuvõte seisude kaupa
    val inProgress = states.count { it is AchievementState.InProgress }
    val locked = states.count { it == AchievementState.Locked }
    println("Kokkuvõte: ${achievements.size} saavutust, $earnedCount teenitud, $inProgress pooleli, $locked lukus")
}
