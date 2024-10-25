plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

sourceSets {
    main {
        resources.srcDirs += file("$rootDir/common/src/main/resources")
    }
}

dependencies {
    implementation(project(":common"))

    implementation(fileTree(mapOf("dir" to "common", "includes" to listOf("*.jar"))))

    implementation("org.lwjgl:lwjgl:3.3.2")
    implementation("org.lwjgl:lwjgl-openal:3.3.2")
    implementation("org.lwjgl:lwjgl-glfw:3.3.2")
    implementation("org.lwjgl:lwjgl-opengl:3.3.2")
    implementation("org.lwjgl:lwjgl-opengles:3.3.2")
    implementation("org.lwjgl:lwjgl-stb:3.3.2")

    runtimeOnly("org.lwjgl:lwjgl:3.3.2:natives-windows")
    runtimeOnly("org.lwjgl:lwjgl-openal:3.3.2:natives-windows")
    runtimeOnly("org.lwjgl:lwjgl-glfw:3.3.2:natives-windows")
    runtimeOnly("org.lwjgl:lwjgl-opengl:3.3.2:natives-windows")
    runtimeOnly("org.lwjgl:lwjgl-opengles:3.3.2:natives-windows")
    runtimeOnly("org.lwjgl:lwjgl-stb:3.3.2:natives-windows")
}