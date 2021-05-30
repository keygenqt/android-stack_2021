/*
 * Copyright 2021 Vitaliy Zarubin
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

package com.keygenqt.stack_2021.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.google.accompanist.insets.ProvideWindowInsets
import com.keygenqt.stack_2021.extension.isSucceeded
import com.keygenqt.stack_2021.ui.home.DetailsRepo
import com.keygenqt.stack_2021.ui.home.TabsHome
import com.keygenqt.stack_2021.ui.home.ViewModelHome
import com.keygenqt.stack_2021.ui.other.Splash
import com.keygenqt.stack_2021.ui.other.ViewModelSplash
import kotlin.time.ExperimentalTime

@ExperimentalTime
@Composable
fun MainNavGraph(changeRoute: (String) -> Unit) {
    val navController = rememberNavController().apply {
        addOnDestinationChangedListener { _, destination, _ ->
            destination.route?.let { changeRoute.invoke(it) }
        }
    }
    val actions = remember(navController) {
        MainActions(navController)
    }
    ProvideWindowInsets {
        NavHost(navController = navController, startDestination = NavScreen.Splash.route) {
            composable(NavScreen.Splash.route) { backStackEntry ->
                val viewModel = hiltViewModel<ViewModelSplash>(backStackEntry = backStackEntry)
                val tabId by viewModel.loadingUser.collectAsState(initial = null)
                if (tabId.isSucceeded) {
                    navController.navigate(NavScreen.Home.route)
                } else {
                    Splash()
                }
            }
            composable(NavScreen.Home.route) { backStackEntry ->
                val viewModel = hiltViewModel<ViewModelHome>(backStackEntry = backStackEntry)
                TabsHome(
                    viewModel = viewModel,
                    navigateToDetailsRepo = actions.navigateToDetailsRepo,
                )
            }
            composable(
                route = NavScreen.DetailsRepo.routeWithArgument,
                arguments = listOf(navArgument(NavScreen.DetailsRepo.argument0) { type = NavType.LongType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getLong(NavScreen.DetailsRepo.argument0) ?: return@composable
                val viewModel = hiltViewModel<ViewModelHome>(backStackEntry = backStackEntry)
                DetailsRepo(
                    id,
                    viewModel = viewModel,
                    upPress = actions.upPress
                )
            }
        }
    }
}


