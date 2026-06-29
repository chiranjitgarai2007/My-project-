// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.kotlin.compose) apply false
  alias(libs.plugins.google.devtools.ksp) apply false
  alias(libs.plugins.roborazzi) apply false
  alias(libs.plugins.secrets) apply false
  alias(libs.plugins.google.services) apply false
}

tasks.register<Zip>("zipApk") {
    archiveFileName.set("My_Skills_My_Dreams_v1_Setup.zip")
    destinationDirectory.set(rootDir)
    from(file("app/build/outputs/apk/debug/app-debug.apk"))
}

