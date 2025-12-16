plugins {
    kotlin("jvm") version "2.2.21"
}

sourceSets {
    main {
        kotlin.srcDir("src")
    }
}

dependencies {
    implementation("com.toldoven.aoc:aoc-kotlin-notebook:1.1.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
}

tasks {
    wrapper {
        gradleVersion = "9.2.1"
    }
}
