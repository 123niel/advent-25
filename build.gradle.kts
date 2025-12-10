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
}

tasks {
    wrapper {
        gradleVersion = "9.2.1"
    }
}
