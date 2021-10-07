import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
   kotlin("jvm")
   id("org.jlleitschuh.gradle.ktlint")
}

repositories {
   mavenCentral()
}

dependencies {
   implementation(Libs.mockServer.client)
   implementation(Libs.mockServer.core)
   testImplementation(Testing.kotest.property)
   testImplementation(Testing.kotest.runner.junit5)
   testImplementation(Testing.kotestExtensions.mockServer)
   testImplementation(Testing.mockK)
}

tasks.named<Wrapper>("wrapper") {
   gradleVersion = "7.1"
   distributionType = Wrapper.DistributionType.ALL
}

val targetJdk = JavaVersion.VERSION_11

tasks.withType<KotlinCompile> {
   kotlinOptions {
      jvmTarget = targetJdk.toString()
   }
}

plugins.withType<JavaPlugin> {
   extensions.configure<JavaPluginExtension> {
      sourceCompatibility = targetJdk
      targetCompatibility = targetJdk
   }
}

tasks.withType<Test> {
   useJUnitPlatform()
}

// Dependency notations, inspired by refreshVersions
object Libs {
   val mockServer = MockServer

   object MockServer {
      private const val groupId = "org.mock-server"

      const val client = "$groupId:mockserver-client-java:_"
      const val core = "$groupId:mockserver-core:_"
   }
}
