// ★ TÖÖRIISTAAHELA LAHENDAJA.
//
// `build.gradle.kts` ütleb `jvmToolchain(21)` — see EI paigalda JDK 21, ta
// ainult NÕUAB teda. Kui masinas on ainult uuem JDK (Android Studio toob
// kaasa JDK 25), siis Gradle ei leia sobivat tööriistaahelat ja Kotlini
// kompilaator kukub segase teatega `IllegalArgumentException: 25`.
//
// See plugin lubab Gradle'il puuduva JDK ISE alla laadida. Esimene ehitus
// vajab võrku; edasi tuleb ta vahemälust.
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "kontrolltoo3"
