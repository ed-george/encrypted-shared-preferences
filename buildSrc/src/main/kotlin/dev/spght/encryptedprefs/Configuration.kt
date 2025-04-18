package dev.spght.encryptedprefs

object Configuration {
    const val compileSdk = 35
    const val targetSdk = 35
    const val minSdk = 21

    const val majorVersion = 1
    const val minorVersion = 0
    const val patchVersion = 0
    const val versionName = "$majorVersion.$minorVersion.$patchVersion"

    const val versionCode = 100
    const val snapshotVersionName = "$majorVersion.$minorVersion.${patchVersion + 1}-SNAPSHOT"
    const val artifactGroup = "dev.spght"
}