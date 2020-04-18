import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.2.6.RELEASE"
	id("io.spring.dependency-management") version "1.0.9.RELEASE"
	kotlin("jvm") version "1.3.71"
	kotlin("plugin.spring") version "1.3.71"
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
	implementation("org.springframework.boot:spring-boot-starter")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}

	// joda time
	implementation("joda-time:joda-time:${property("jodatime_version")}")

	// inject support
	implementation("javax.inject:javax.inject:${property("javax_version")}")

	// database
	implementation("org.jetbrains.exposed:exposed-core:${property("exposed_version")}")
	implementation("org.jetbrains.exposed:exposed-dao:${property("exposed_version")}")
	implementation("org.jetbrains.exposed:exposed-jdbc:${property("exposed_version")}")
	implementation("org.jetbrains.exposed:exposed-jodatime:${property("exposed_version")}")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}
