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

import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.report.ReportMergeTask

plugins {
    //alias(libs.plugins.kotlin)
    kotlin("jvm")
    id("io.gitlab.arturbosch.detekt")
}

// Note: Kotlin DSL cannot directly access configurations that are created by applying a plugin in the very same
// project, thus put configuration names in quotes to leverage lazy lookup.
dependencies {
    "detektPlugins"(project(":detekt-rules"))

    //"detektPlugins"("io.gitlab.arturbosch.detekt:detekt-formatting:${rootProject.libs.versions.detektPlugin.get()}")
    "detektPlugins"("io.gitlab.arturbosch.detekt:detekt-formatting")
}

detekt {
    // Only configure differences to the default.
    buildUponDefaultConfig = true
    config = files("$rootDir/.detekt.yml")

    source.from(fileTree(".") { include("*.gradle.kts") }, "src/funTest/kotlin")

    basePath = rootProject.projectDir.path
}

val mergeDetektReports by tasks.registering(ReportMergeTask::class) {
    output.set(rootProject.buildDir.resolve("reports/detekt/merged.sarif"))
}

tasks.withType<Detekt> detekt@{
    dependsOn(":detekt-rules:assemble")

    reports {
        html.required.set(false)

        // TODO: Enable this once https://github.com/detekt/detekt/issues/5034 is resolved and use the merged
        //       Markdown file as a GitHub Action job summary, see
        //       https://github.blog/2022-05-09-supercharging-github-actions-with-job-summaries/.
        md.required.set(false)

        sarif.required.set(true)
        txt.required.set(false)
        xml.required.set(false)
    }

    finalizedBy(mergeDetektReports)

    mergeDetektReports.configure {
        input.from(this@detekt.sarifReportFile)
    }
}
