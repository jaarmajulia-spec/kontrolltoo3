// Kontrolltöö 3 · Ülesanne 3 — Ulatusfunktsioonid ja laiendused

// 1. Muutuvate väljadega seadistus
class BadgeSettings {
    var meterTarget: Int = 0
    var stepTarget: Int = 0
    var name: String? = null

    // 8. Laisk omadus: arvutatakse esimesel lugemisel, edasi tuleb meelest
    val description: String by lazy {
        println("  (kirjeldus arvutatakse nüüd, esimest ja ainsat korda)")
        "$name: $meterTarget m või $stepTarget sammu"
    }
}

// 6. Laiendusfunktsioon: tühjal loendil 0.0, mitte erind
fun List<Double>.mean(): Double = if (isEmpty()) 0.0 else sum() / size

// 7. Laiendusomadus: tühjal loendil null
val List<Double>.largest: Double?
    get() = maxOrNull()

fun main() {
    // 2–3. apply seadistab (this, tagastab objekti), also logib (it, tagastab objekti)
    val settings = BadgeSettings().apply {
        meterTarget = 1000
        stepTarget = 1000
        name = "Esimene kilomeeter"
    }.also { println("Seadistus valmis: ${it.name}") }

    // 4. let jääb null-väärtuse korral vahele
    settings.name?.let { println("Nimi on olemas: $it") }
    val noName: String? = null
    noName?.let { println("Seda ei trükita: nimi puudub") }

    // 5. with: mitu lugemist ühelt objektilt, tulemuseks lambda väärtus
    val targetSum = with(settings) { meterTarget + stepTarget }
    println("Lattide summa: $targetSum")

    // 6–7. Laiendused
    val distances = listOf(320.0, 1450.0, 5200.0, 180.0)
    println("Keskmine: ${distances.mean()} m")
    println("Suurim: ${distances.largest}")
    val empty = emptyList<Double>()
    println("Tühja loendi keskmine: ${empty.mean()}, suurim: ${empty.largest}")

    // 8. Kaks lugemist: arvutusteade ilmub ainult esimesel korral
    println("Laisk omadus, kaks lugemist:")
    println("  1. ${settings.description}")
    println("  2. ${settings.description}")

    // 9. Tabel
    val table = listOf(
        listOf("let", "it", "lambda tulemus", "null-kontroll, teisendus"),
        listOf("run", "this", "lambda tulemus", "arvutus objekti kontekstis"),
        listOf("with", "this", "lambda tulemus", "mitu lugemist ühelt objektilt"),
        listOf("apply", "this", "objekt ise", "objekti seadistamine"),
        listOf("also", "it", "objekt ise", "logimine ja kontroll ahela sees"),
    )
    println("Viis ulatusfunktsiooni:")
    for ((fn, ref, returns, use) in table) {
        println("  ${fn.padEnd(7)}${ref.padEnd(7)}${returns.padEnd(17)}$use")
    }

    // 10. ?.run nullitava objekti peal + elvis
    val missing: BadgeSettings? = null
    val target = missing?.run { meterTarget } ?: -1
    println("Puuduva seadistuse latt: $target")
}
