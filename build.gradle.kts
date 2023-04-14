plugins {
    java
    id("org.springframework.boot") version "3.0.5"
    id("io.spring.dependency-management") version "1.1.0"
}

group = "com.brunobalic.demo"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web") {
        exclude("org.springframework.boot", "spring-boot-starter-tomcat")
    }
    implementation("org.springframework.boot:spring-boot-starter-jetty")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

// This is Jetty specific thing with Spring Boot 3
// https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-3.0-Migration-Guide#jetty
ext {
    set("jakarta-servlet.version", "5.0.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
