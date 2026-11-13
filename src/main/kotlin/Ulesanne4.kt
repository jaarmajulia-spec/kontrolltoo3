// Kontrolltöö 3 · Ülesanne 4 — Kollektsiooniahelad ja Sequence

// 1. Ühe päeva kirje
data class DayEntry(val day: Int, val meters: Double, val steps: Int)

fun main() {
    // 2. Kümme päeva, päevad 1 ja 6 on null-meetrised
    val history = listOf(
        DayEntry(0, 1200.0, 1620),
        DayEntry(1, 0.0, 0),
        DayEntry(2, 2100.0, 2840),
        DayEntry(3, 650.0, 880),
        DayEntry(4, 5200.0, 7020),
        DayEntry(5, 700.0, 950),
        DayEntry(6, 0.0, 0),
        DayEntry(7, 3400.0, 4590),
        DayEntry(8, 1000.0, 1350),
        DayEntry(9, 600.0, 810),
    )

    // 3–4. Ahel: iga samm teeb ÜHTE asja ja on ühe sõnaga nimetatav —
    // "jäta välja", "võta meetrid", "järjesta", "võta kolm". Nii saab iga rea eraldi
    // kontrollida ja järjekord on õige: kitsenda enne, järjesta siis, lõika lõpus.
    val topThree = history
        .filter { it.meters > 0.0 }   // jäta null-päevad välja
        .map { it.meters }            // võta ainult meetrid
        .sortedDescending()           // suurim enne
        .take(3)                      // kolm esimest
    println("Kolm pikimat päeva: $topThree")

    // 5. Nädalad: päev / 7 (täisarvujagamine) annab nädala numbri
    val byWeek: Map<Int, List<DayEntry>> = history.groupBy { it.day / 7 }
    for ((week, entries) in byWeek) {
        println("Nädal $week: ${entries.size} päeva, kokku ${entries.sumOf { it.meters }.toInt()} m")
    }

    // 6. associateBy: päev -> üks kirje; puuduv võti annab null, mitte erindi
    val byDay: Map<Int, DayEntry> = history.associateBy { it.day }
    println("Päev 4: ${byDay[4]}")
    println("Päev 99: ${byDay[99]}")

    // 7. fold algväärtusega 0.0
    val total = history.fold(0.0) { acc, entry -> acc + entry.meters }
    println("Kogumeetrid: ${total.toInt()}")

    // 8. reduce tühjal loendil viskab erindi; runCatching püüab selle kinni
    val empty = emptyList<Double>()
    val failure = runCatching { empty.reduce { a, b -> a + b } }.exceptionOrNull()
    println("reduce tühjal loendil: ${failure?.javaClass?.simpleName}")
    println("fold tühjal loendil: ${empty.fold(0.0) { acc, x -> acc + x }}")

    // 9. Jada: element haaval läbi kogu ahela; viie leidmisel arvutus LÕPEB
    var seqVisited = 0
    val seqResult = (1..100_000).asSequence()
        .map { seqVisited++; it * 3 }
        .filter { it % 7 == 0 }
        .take(5)
        .toList()

    // 10. Loend: iga samm arvutatakse KOGU loendi peal lõpuni
    var listVisited = 0
    val listResult = (1..100_000)
        .map { listVisited++; it * 3 }
        .filter { it % 7 == 0 }
        .take(5)

    println("Jada tulemus: $seqResult, läbitud elemente: $seqVisited")
    println("Loendi tulemus: $listResult, läbitud elemente: $listVisited")
    if (seqResult == listResult) {
        println("Sama vastus, aga jada tegi ${listVisited / seqVisited} korda vähem tööd")
    }
}
