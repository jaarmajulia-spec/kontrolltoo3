// Kontrolltöö 3 · Ülesanne 1 — Märgid kolmes kollektsioonis
// List = järjekord, Set = kuuluvus (kordusi ei ole), Map = seos võtmest väärtusele.

// 1. Märgi andmeklass
data class Badge(val code: String, val name: String, val tier: Int)

// 8. Kogu: sees muudetav loend, väljas ainult lugemisliides List
class BadgeCollection {
    private val items = mutableListOf<Badge>()

    // Väljapoole antakse List — sellel ei ole add/remove meetodeid
    val badges: List<Badge>
        get() = items

    fun add(badge: Badge) {
        items.add(badge)
    }
}

fun main() {
    // 2. Kõik märgid järjekorras (List), tasemed 1..3
    val all: List<Badge> = listOf(
        Badge("KM1", "Esimene kilomeeter", 1),
        Badge("S1000", "Tuhat sammu", 1),
        Badge("MIN30", "Pool tundi", 1),
        Badge("KM5", "Viis kilomeetrit", 2),
        Badge("S10K", "Kümme tuhat sammu", 2),
        Badge("RING", "Terve linnaosa", 3),
    )

    // 3–4. Teenitud koodid hulgana: KM1 on MEELEGA kaks korda, Set jätab ühe alles
    val given = arrayOf("KM1", "S1000", "KM5", "KM1")
    val earned: Set<String> = setOf(*given)
    println("Teenitud koode anti ${given.size}, hulgas on ${earned.size}")

    // 5. Kirjeldused koodi järgi; MIN30 kirjeldust ei ole
    val descriptions: Map<String, String> = mapOf(
        "KM1" to "Esimene täis kilomeeter ühe päevaga",
        "S1000" to "Tuhat sammu ühe päevaga",
        "KM5" to "Viis kilomeetrit ühe päevaga",
        "S10K" to "Kümme tuhat sammu ühe päevaga",
        "RING" to "Kogu linnaosa läbitud",
    )

    // 6–7. Rida iga märgi kohta; puuduv kirjeldus saab varuteksti elvisega
    println("Märgid:")
    for (b in all) {
        val status = if (b.code in earned) "TEENITUD" else "lukus"
        val description = descriptions[b.code] ?: "(kirjeldus puudub)"
        println("  ${b.code.padEnd(7)}${b.name.padEnd(21)}tase ${b.tier}  ${status.padEnd(10)}$description")
    }

    // 9. Kogusse kaks märki
    val collection = BadgeCollection()
    collection.add(all[0])
    collection.add(all[3])
    val fromOutside: List<Badge> = collection.badges
    // fromOutside.add(all[1])   // EI KOMPILEERU: List-il ei ole add-i
    println("Kogus on ${fromOutside.size} märki: ${fromOutside.joinToString(", ") { it.code }}")

    // 10. Tühi loend on päris loend, mitte null: suurus 0, esimest ei ole
    val none: List<Badge> = emptyList()
    println("Tühja loendi suurus: ${none.size}, esimene: ${none.firstOrNull()}")
    println("Tühja loendi kõrgeim tase: ${none.maxOfOrNull { it.tier } ?: 0}")
}
