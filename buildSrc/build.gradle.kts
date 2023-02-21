plugins {
    // Use Kotlin DSL to write precompiled script plugins.
    `kotlin-dsl`
}

repositories {
    // Allow to resolve external plugins from precompiled script plugins.
    gradlePluginPortal()
}
