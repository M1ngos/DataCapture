/*
 * Copyright 2023 Joel Kanyi.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.acsunmz.datacapture.feature.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.acsunmz.datacapture.core.presentation.navigation.Destinations
import datacapture.composeapp.generated.resources.Res
import datacapture.composeapp.generated.resources.il_statistics
import datacapture.composeapp.generated.resources.il_tasks
import datacapture.composeapp.generated.resources.il_work_time
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun OnboardingScreen(
    navController: NavController,
) {
    val coroutineScope = rememberCoroutineScope()
    val pageCount = 3
    val pagerState = rememberPagerState(pageCount = { pageCount })

    OnboardingScreenContent(
        pagerState = pagerState,
        pageCount = pageCount,
        onClickNext = {
            coroutineScope.launch {
                pagerState.animateScrollToPage(pagerState.currentPage + 1)
            }
        },
        onClickGetStarted = {
            navController.popBackStack()
            navController.navigate(Destinations.AppointmentIdScreen)
        },
    )
}

@Composable
fun OnboardingScreenContent(
    pageCount: Int,
    pagerState: PagerState,
    onClickNext: () -> Unit,
    onClickGetStarted: () -> Unit,
) {
    Scaffold(
        bottomBar = {
            if (pagerState.currentPage == pageCount - 1) {
                OnBoardingNavigationButton(
                    modifier = Modifier.padding(16.dp),
                    text = "Get Started",
                    onClick = onClickGetStarted,
                )
            } else {
                OnBoardingNavigationButton(
                    modifier = Modifier.padding(16.dp),
                    text = "Next",
                    onClick = onClickNext,
                )
            }
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues),
        ) {
            HorizontalPager(
                modifier = Modifier
                    .weight(.9f)
                    .padding(16.dp),
                state = pagerState,
            ) { currentPage ->
                when (currentPage) {
                    0 -> OnboardingFirstPage()
                    1 -> OnboardingSecondPage()
                    2 -> OnboardingThirdPage()
                }
            }

            PageIndicators(
                pageCount = pageCount,
                currentPage = pagerState.currentPage,
            )
        }
    }
}

@Composable
private fun ColumnScope.PageIndicators(pageCount: Int, currentPage: Int) {
    Row(
        Modifier
            .weight(.1f)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        repeat(pageCount) { iteration ->
            val color =
                if (currentPage == iteration) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurface.copy(
                        alpha = 0.2f,
                    )
                }
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(color)
                    .width(24.dp)
                    .height(8.dp),

            )
        }
    }
}

@Composable
private fun OnboardingFirstPage() {
    PageContent(
        title = "This is a prototype",
        description = "That aims to automate the process of capturing biometric data...",
        illustration = Res.drawable.il_tasks,
    )
}

@Composable
private fun OnboardingSecondPage() {
    PageContent(
        title = "Lorem ipsum dolor sit amet",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
        illustration = Res.drawable.il_work_time,
    )
}

@Composable
private fun OnboardingThirdPage() {
    PageContent(
        title = "Lorem ipsum dolor sit amet",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
        illustration = Res.drawable.il_statistics,
    )
}

@Composable
private fun PageContent(title: String, description: String, illustration: DrawableResource) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(illustration),
            contentDescription = null,
            modifier = Modifier.size(370.dp),
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 22.sp,
                textAlign = TextAlign.Center,
            ),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = description,
            style = MaterialTheme.typography.labelMedium.copy(
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
            ),
        )
    }
}

@Composable
fun OnBoardingNavigationButton(modifier: Modifier = Modifier, text: String, onClick: () -> Unit) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            ),
        )
    }
}
