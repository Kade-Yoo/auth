val bootJar: org.springframework.boot.gradle.tasks.bundling.BootJar by tasks
val bootRun: org.springframework.boot.gradle.tasks.run.BootRun by tasks

val jar: Jar by tasks

bootJar.enabled = true
bootRun.enabled = true
jar.enabled = false

dependencies {
    implementation(project(":domain"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // JWT
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
    implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")

    // Ldap
    implementation("org.springframework.ldap:spring-ldap-core")
    implementation("org.springframework.security:spring-security-ldap")
    implementation("com.unboundid:unboundid-ldapsdk")
}

tasks.register("prepareKotlinBuildScriptModel") {}