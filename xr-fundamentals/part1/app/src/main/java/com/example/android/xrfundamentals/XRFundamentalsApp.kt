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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import androidx.xr.compose.spatial.Subspace
import androidx.xr.compose.subspace.DragPolicy
import androidx.xr.compose.subspace.MovePolicy
import androidx.xr.compose.subspace.SpatialCurvedRow
import androidx.xr.compose.subspace.SpatialPanel
import androidx.xr.compose.subspace.layout.SubspaceModifier
import androidx.xr.compose.subspace.layout.height
import androidx.xr.compose.subspace.layout.movable
import androidx.xr.compose.subspace.layout.width
import com.example.android.xrfundamentals.ui.component.PrimaryCard
import com.example.android.xrfundamentals.ui.component.SecondaryCardList
import com.example.android.xrfundamentals.ui.component.XRFundamentalsTopAppBar
import com.example.android.xrfundamentals.ui.layout.CompactLayout
import com.example.android.xrfundamentals.ui.layout.ExpandedLayout

@Composable
fun XRFundamentalsApp(
    windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
) {
    Scaffold(
        topBar = { XRFundamentalsTopAppBar() }
    ) { innerPadding ->

        val modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()

        if (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT) {
            CompactLayout(
                modifier = modifier,
                primaryContent = {
                    PrimaryCard()
                },
                secondaryContent = {
                    SecondaryCardList()
                }
            )
        } else {
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
        }
    }
    Subspace {
        SpatialCurvedRow(
            curveRadius = 825.dp
        ) {
            SpatialPanel(
                modifier = SubspaceModifier
                    .width(1024.dp)
                    .height(800.dp)
            ) {
                Scaffold(
                    topBar = { XRFundamentalsTopAppBar() }
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
                    .height(800.dp),
                dragPolicy = MovePolicy()
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