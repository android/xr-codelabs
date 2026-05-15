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

package com.example.android.xrfundamentals.ui.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.xr.compose.platform.LocalSpatialConfiguration
import com.example.android.xrfundamentals.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun XRFundamentalsTopAppBar(
    onHomeSpaceRequested: () -> Unit,
    onFullSpaceRequested: () -> Unit,
) {
    TopAppBar(
        title = { Text(stringResource(R.string.app_name)) },
        actions = {
            // Only show the space toggle if the device supports spatial UI
            if (LocalSpatialConfiguration.current.hasXrSpatialFeature) {
                ToggleSpaceButton(onHomeSpaceRequested, onFullSpaceRequested)
            }
        }
    )
}