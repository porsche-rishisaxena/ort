/*
 * Copyright (C) 2023 The ORT Project Authors (see <https://github.com/oss-review-toolkit/ort/blob/main/NOTICE>)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 * License-Filename: LICENSE
 */

// See https://github.com/gradle/gradle/issues/15383.
//val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
val Project.libs get() = the<org.gradle.accessors.dm.LibrariesForLibs>()

plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    // See https://docs.gradle.org/current/samples/sample_convention_plugins.html#applying_an_external_plugin_in_precompiled_script_plugin.
    implementation(libs.detektPlugin)
    implementation(libs.kotlinPlugin)
}

