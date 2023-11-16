plugins {
    java
    id("com.github.weave-mc.weave-gradle") version "649dba7468"
}

group = "maxstats.weave"
version = "v2.1"

minecraft.version("1.8.9")

repositories {
    maven("https://jitpack.io")
    maven("https://repo.spongepowered.org/maven/")
}

dependencies {
    compileOnly("com.github.weave-mc:weave-loader:70bd82faa6")
    compileOnly("org.spongepowered:mixin:0.8.5")
}