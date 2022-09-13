import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.3"
	id("io.spring.dependency-management") version "1.0.13.RELEASE"
	id("com.gorylenko.gradle-git-properties") version "2.4.1"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.jpa") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
	id("com.palantir.docker") version "0.31.0"
	id("com.palantir.docker-run") version "0.31.0"
}

group = "com"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

apply(from = "$rootDir/gradle/docker.gradle")

repositories {
	mavenCentral()
}

dependencies {
	val springDocVersion: String by project

	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-quartz")

	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

	implementation("org.flywaydb:flyway-core")

	runtimeOnly("com.h2database:h2")

	// Open API
	implementation("org.springdoc:springdoc-openapi-webflux-ui:$springDocVersion")
	implementation("org.springdoc:springdoc-openapi-kotlin:$springDocVersion")

	// Tests
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(module = "junit")
		exclude(module = "mockito-core")
	}
	testImplementation("org.junit.jupiter:junit-jupiter-api")
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
	testImplementation("io.mockk:mockk:1.12.7")
	testImplementation("com.ninja-squad:springmockk:3.1.1")
	testImplementation("io.projectreactor:reactor-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}