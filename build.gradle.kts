import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.2.6.RELEASE"
	id("io.spring.dependency-management") version "1.0.9.RELEASE"
	kotlin("jvm") version "1.3.71"
	kotlin("plugin.allopen") version "1.3.71"
	kotlin("plugin.jpa") version "1.3.71"
	kotlin("plugin.spring") version "1.3.71"
	kotlin("kapt") version "1.3.61"
}

group = "unq.edu"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
	mavenCentral()
	jcenter()
	google()
	maven(url = "https://dl.bintray.com/kotlin/kotlin-dev")
}

dependencies {
	// kotlin libraries
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	// spring libraries
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-mustache")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter")
	kapt("org.springframework.boot:spring-boot-configuration-processor")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
		exclude(module = "mockito-core")
	}
	runtimeOnly("org.springframework.boot:spring-boot-devtools")

	// joda time
	implementation("joda-time:joda-time:2.10.5")

	// persistence
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	runtimeOnly("com.h2database:h2")

	// test
	testImplementation("org.junit.jupiter:junit-jupiter-api")
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}


tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}

// persistence annotations
allOpen {
	annotation("javax.persistence.Entity")
	annotation("javax.persistence.Embeddable")
	annotation("javax.persistence.MappedSuperclass")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
