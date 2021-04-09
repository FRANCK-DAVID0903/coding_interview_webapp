import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.4.4"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    war
    kotlin("jvm") version "1.4.31"
    kotlin("plugin.spring") version "1.4.31"
    kotlin("plugin.jpa") version "1.4.31"
}

group = "com.b2i"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
    maven(url = "https://repo.spring.io/snapshot")
    maven(url = "https://repo.spring.io/milestone")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity5")

    // Spring Mobile Device
    implementation("org.springframework.mobile:spring-mobile-device:2.0.0.M3")

    // Unirest
    implementation("com.mashape.unirest:unirest-java:1.4.9")

    // Kotlin Reflect
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // Jackson Module Kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // Joda Time
    implementation("joda-time:joda-time:2.4")

    // Neko Html
    implementation("net.sourceforge.nekohtml:nekohtml:1.9.21")

    // Geolite
    implementation("com.maxmind.geoip2:geoip2:2.9.0")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.0-M1")

    // Thymleaf
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity4:3.0.2.RELEASE")


    developmentOnly("org.springframework.boot:spring-boot-devtools")

    // PostgresSQL Driver
    runtimeOnly("org.postgresql:postgresql")

    providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
