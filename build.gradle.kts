// Puhas Kotlin/JVM projekt: Androidi siin ei ole, sest loeng 2 õpetab KEELT.
// Nii saab iga näite käivitada ja tema väljundit kohe näha.
plugins {
    // ★ KOTLIN 2.2.20, MITTE 2.0.21, ja põhjus on tehniline: 2.0.21
    // kompilaator kukub, kui Gradle jookseb JDK 25 peal —
    // `JavaVersion.parse("25.0.3")` viskab `IllegalArgumentException`.
    // Android Studio toob kaasa JDK 25, seega see tabaks igaüht, kes ei sea
    // Gradle'i JDK-d käsitsi 21 peale. Androidi projekte see ei puuduta,
    // sest nende ehitust juhib AGP. 2.0.21 ehitub ainult JDK 21-l, 2.2.20
    // mõlemal.
    kotlin("jvm") version "2.2.20"
    application
}

repositories { mavenCentral() }

kotlin { jvmToolchain(21) }

// Millist näidet käivitada:  gradle run -Pk=Ulesanne3Kt
application {
    // UTF-8 vaikimisi: muidu sõltub väljund konsooli koodileheküljest
    applicationDefaultJvmArgs = listOf("-Dfile.encoding=UTF-8", "-Dstdout.encoding=UTF-8")
    mainClass.set(providers.gradleProperty("k").getOrElse("Ulesanne1Kt"))
}
