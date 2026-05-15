/*
 * Copyright 2024 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.xrfundamentals.environment

import androidx.xr.runtime.Session
import androidx.xr.scenecore.ExrImage
import androidx.xr.scenecore.GltfModel
import androidx.xr.scenecore.SpatialEnvironment.SpatialEnvironmentPreference
import kotlin.io.path.Path

data class EnvironmentOption(val name: String, val skyboxPath: String?, val geometryPath: String?) {
    suspend fun toSpatialEnvironmentPreference(session: Session): SpatialEnvironmentPreference? {
        if (skyboxPath == null && geometryPath == null) {
            return null
        } else {
            val skybox = skyboxPath?.let {
                ExrImage.createFromZip(session, Path(it))
            }

            val geometry = geometryPath?.let {
                GltfModel.create(session, Path(it))
            }

            return SpatialEnvironmentPreference(skybox, geometry)
        }
    }
}

val ENVIRONMENT_OPTIONS = listOf(
    EnvironmentOption("Default", null, null),
    EnvironmentOption("Green Hills", "green_hills_ibl.zip", "green_hills_ktx2_mipmap.glb")
)