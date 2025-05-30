import com.diffplug.gradle.spotless.SpotlessPlugin
import com.vanniktech.maven.publish.MavenPublishPlugin

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.spotless)
    alias(libs.plugins.maven.publish)
}

subprojects {
    apply<MavenPublishPlugin>()
    apply<SpotlessPlugin>()

    configure<com.diffplug.gradle.spotless.SpotlessExtension> {
        java {
            target("**/*.java")
            targetExclude("**/build/**/*.java")
            googleJavaFormat().aosp()
            licenseHeaderFile(rootProject.file("$rootDir/spotless/copyright.java"))
        }
        kotlin {
            target("**/*.kt")
            targetExclude("**/build/**/*.kt")
            ktlint().editorConfigOverride(mapOf("indent_size" to 2, "continuation_indent_size" to 2))
            licenseHeaderFile(rootProject.file("$rootDir/spotless/copyright.kt"))
        }
        format("kts") {
            target("**/*.kts")
            targetExclude("**/build/**/*.kts")
            // Look for the first line that doesn't have a block comment (assumed to be the license)
            licenseHeaderFile(rootProject.file("spotless/copyright.kts"), "(^(?![\\/ ]\\*).*$)")
        }
        format("xml") {
            target("**/*.xml")
            targetExclude("**/build/**/*.xml")
            // Look for the first XML tag that isn't a comment (<!--) or the xml declaration (<?xml)
            licenseHeaderFile(rootProject.file("spotless/copyright.xml"), "(<[^!?])")
        }
    }
}