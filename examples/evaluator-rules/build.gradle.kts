/*
 * Copyright (C) 2022 The ORT Project Authors (see <https://github.com/oss-review-toolkit/ort/blob/main/NOTICE>)
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

val ortGroupNameOnJitPack = "com.github.oss-review-toolkit.ort"

repositories {
    mavenCentral()

    exclusiveContent {
        forRepository {
            maven("https://jitpack.io")
        }

        filter {
            includeGroup(ortGroupNameOnJitPack)
        }
    }
}

dependencies {
    implementation("$ortGroupNameOnJitPack:evaluator:$version")
}

configurations.all {
    resolutionStrategy.dependencySubstitution.all {
        requested.let { selector ->
            if (selector is ModuleComponentSelector && selector.group == ortGroupNameOnJitPack) {
                logger.info("Looking for a project named '${selector.module}' to substitute the artifact.")
                val targetProject = rootProject.subprojects.find { it.name == selector.module }
                if (targetProject != null) {
                    logger.info("Found project $targetProject.")
                    useTarget(targetProject)
                }
            }
        }
    }
}
