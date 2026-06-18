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

package com.example.android.xrfundamentals

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import androidx.xr.compose.platform.LocalSession
import androidx.xr.compose.platform.LocalSpatialCapabilities
import androidx.xr.compose.spatial.Subspace
import androidx.xr.compose.subspace.SpatialCurvedRow
import androidx.xr.compose.subspace.SpatialPanel
import androidx.xr.compose.subspace.layout.SubspaceModifier
import androidx.xr.compose.subspace.layout.height
import androidx.xr.compose.subspace.layout.movable
import androidx.xr.compose.subspace.layout.resizable
import androidx.xr.compose.subspace.layout.transformingMovable
import androidx.xr.compose.subspace.layout.transformingResizable
import androidx.xr.compose.subspace.layout.width
import androidx.xr.scenecore.scene
import com.example.android.xrfundamentals.environment.ENVIRONMENT_OPTIONS
import com.example.android.xrfundamentals.ui.component.EnvironmentSelectionOrbiter
import com.example.android.xrfundamentals.ui.component.PrimaryCard
import com.example.android.xrfundamentals.ui.component.SecondaryCardList
import com.example.android.xrfundamentals.ui.component.XRFundamentalsTopAppBar
import com.example.android.xrfundamentals.ui.layout.CompactLayout
import com.example.android.xrfundamentals.ui.layout.ExpandedLayout
import kotlinx.coroutines.launch

@Composable
fun XRFundamentalsApp(
    windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass,
    onHomeSpaceRequested: () -> Unit,
    onFullSpaceRequested: () -> Unit,
) {

    Scaffold(
        topBar = { XRFundamentalsTopAppBar(onHomeSpaceRequested, onFullSpaceRequested) }
    ) { innerPadding ->

        val modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()

        val isExpanded = windowSizeClass.isWidthAtLeastBreakpoint(
            WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND
        )

        if (isExpanded) {
            ExpandedLayout(
                modifier = modifier,
                primaryContent = {
                    PrimaryCard(
                        modifier = Modifier.verticalScroll(rememberScrollState())
                    )
                },
                secondaryContent = {
                    SecondaryCardList(
                        modifier = Modifier.verticalScroll(rememberScrollState())
                    )
                }
            )
        } else {
            CompactLayout(
                modifier = modifier,
                primaryContent = {
                    PrimaryCard()
                },
                secondaryContent = {
                    SecondaryCardList()
                }
            )
        }
    }

    var currentEnvironmentOptionIndex by remember { mutableIntStateOf(0) }
    Subspace {
        val session = checkNotNull(LocalSession.current)
        val scope = rememberCoroutineScope()

        SpatialCurvedRow(
            curveRadius = 825.dp
        ) {
            SpatialPanel(
                modifier = SubspaceModifier
                    .width(1024.dp)
                    .height(800.dp)
            ) {

                // Only show the environment selection orbiter if the app is actually able to
                // set the environment
                if (LocalSpatialCapabilities.current.isAppEnvironmentEnabled) {
                    EnvironmentSelectionOrbiter(
                        onClick = {
                            scope.launch {
                                currentEnvironmentOptionIndex =
                                    (currentEnvironmentOptionIndex + 1) % ENVIRONMENT_OPTIONS.size

                                session.scene.spatialEnvironment.preferredSpatialEnvironment =
                                    ENVIRONMENT_OPTIONS[currentEnvironmentOptionIndex].toSpatialEnvironmentPreference(
                                        session
                                    )
                            }
                        }
                    )
                }

                Scaffold(
                    topBar = { XRFundamentalsTopAppBar(onHomeSpaceRequested, onFullSpaceRequested) }
                ) { innerPadding ->
                    Box(Modifier.padding(innerPadding)) {
                        PrimaryCard(
                            modifier = Modifier
                                .padding(16.dp)
                                .verticalScroll(rememberScrollState())
                        )
                    }
                }
            }
            SpatialPanel(
                modifier = SubspaceModifier
                    .width(340.dp)
                    .height(800.dp)
                    .transformingMovable()
                    .transformingResizable(),
            ) {
                Surface {
                    SecondaryCardList(
                        modifier = Modifier
                            .padding(16.dp)
                            .verticalScroll(rememberScrollState())
                    )
                }
            }
        }
    }
}