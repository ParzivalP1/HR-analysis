plugins {
	java
	id("org.springframework.boot") version "3.1.2"
	id("io.spring.dependency-management") version "1.1.2"
}

group = "ru.nikitakuts"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	// new one
	implementation("org.springframework.data:spring-data-keyvalue")
	implementation("org.springframework.boot:spring-boot-starter-validation")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
