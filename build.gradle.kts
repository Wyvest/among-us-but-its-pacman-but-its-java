plugins {
	id("com.github.johnrengelman.shadow") version "7.1.2"
	kotlin("jvm") version "1.6.21"
	java
}

val lwjglVersion = "3.3.1"

val lwjglNatives = Pair(
	System.getProperty("os.name")!!,
	System.getProperty("os.arch")!!
).let { (name, arch) ->
	when {
		arrayOf("Linux", "FreeBSD", "SunOS", "Unit").any { name.startsWith(it) } ->
			"natives-linux"
		arrayOf("Mac OS X", "Darwin").any { name.startsWith(it) }                ->
			"natives-macos${if (arch.startsWith("aarch64")) "-arm64" else ""}"
		arrayOf("Windows").any { name.startsWith(it) }                           ->
			"natives-windows"
		else -> throw Error("Unrecognized or unsupported platform. Please set \"lwjglNatives\" manually")
	}
}


repositories {
	mavenCentral()
	maven("https://jitpack.io/")
}

val shade by configurations.creating {
	configurations.implementation.get().extendsFrom(this)
}

val runtimeOnlyShade by configurations.creating {
	configurations.runtimeOnly.get().extendsFrom(this)
}

dependencies {
	implementation(platform("org.lwjgl:lwjgl-bom:$lwjglVersion"))

	shade(kotlin("stdlib", "1.6.21"))
	shade(kotlin("stdlib-jdk8", "1.6.21"))
	shade(kotlin("stdlib-jdk7", "1.6.21"))

	shade("commons-io:commons-io:2.11.0")
	shade("org.lwjgl", "lwjgl", lwjglVersion)
	shade("org.lwjgl", "lwjgl-glfw", lwjglVersion)
	shade("org.lwjgl", "lwjgl-nanovg", lwjglVersion)
	shade("org.lwjgl", "lwjgl-opengl", lwjglVersion)
	shade("org.lwjgl", "lwjgl-openal", lwjglVersion)
	shade("org.lwjgl", "lwjgl-opus", lwjglVersion)
	shade("org.lwjgl", "lwjgl-stb", lwjglVersion)
	shade("com.github.KevinPriv:keventbus:c52e0a2ea0") {
		isTransitive = false
	}
	runtimeOnlyShade("org.lwjgl", "lwjgl", lwjglVersion, classifier = lwjglNatives)
	runtimeOnlyShade("org.lwjgl", "lwjgl-glfw", lwjglVersion, classifier = lwjglNatives)
	runtimeOnlyShade("org.lwjgl", "lwjgl-nanovg", lwjglVersion, classifier = lwjglNatives)
	runtimeOnlyShade("org.lwjgl", "lwjgl-opengl", lwjglVersion, classifier = lwjglNatives)
	runtimeOnlyShade("org.lwjgl", "lwjgl-openal", lwjglVersion, classifier = lwjglNatives)
	runtimeOnlyShade("org.lwjgl", "lwjgl-opus", lwjglVersion, classifier = lwjglNatives)
	runtimeOnlyShade("org.lwjgl", "lwjgl-stb", lwjglVersion, classifier = lwjglNatives)
}

tasks {
	withType(Jar::class.java) {
		manifest {
			"Main-Class" to "MainKt"
		}
	}
	jar.get().enabled = false
	shadowJar {
		manifest {
			"Main-Class" to "MainKt"
		}
		archiveClassifier.set("")
		configurations = listOf(shade, runtimeOnlyShade)
	}
	jar.get().dependsOn(shadowJar)
}