package com.example.jobfinder.presentation

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.jobfinder.navigation.AppNavHost
import com.example.jobfinder.navigation.AppRoutes
import com.example.jobfinder.navigation.NavigationDestination
import com.example.jobfinder.navigation.safeNavigate
import com.example.jobfinder.presentation.message.ChatViewModel
import com.example.jobfinder.service_locator.AppContainer
import com.example.jobfinder.utils.MyNavBarItem

/**
 * Màn hình chính – chưa hẳn 1 screen.
 * Tại đây, ta có 1 NavController chung -> AppNavHost + BottomNav
 * Ẩn bottomBar khi route = "chat_detail/{chatId}"
 */
@SuppressLint("RememberReturnType")
@Composable
fun BaseScreen() {
    val context = LocalContext.current
    val application = context.applicationContext as Application
    val navController = rememberNavController()

    //khai bao viewmodel o composable root
    val chatViewModel: ChatViewModel = viewModel()
    val authViewModel: AuthViewModel = viewModel(
        factory = BaseViewModelFactory {
            AuthViewModel(
                application = application,
                loginUseCase = AppContainer.loginUseCase,
                registerUseCase = AppContainer.registerUseCase
            )
        },
        viewModelStoreOwner = LocalContext.current as ViewModelStoreOwner
    )

    // Theo dõi route hiện tại
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
//    LaunchedEffect(currentRoute) {
//        Log.d("Navigation", "Current route: $currentRoute")
//    }

    //create a snackbarhoststate to be shared across all screens
    val snackbarHostState = remember{ SnackbarHostState() }
    //create a coroutine scope for launching snackbars
    val scope = rememberCoroutineScope()

    //observe login state
    val isUserAlreadyLoggedIn by authViewModel.isUserAlreadyLoggedIn.collectAsState()

    //determine start destination
    val startDestination = remember(isUserAlreadyLoggedIn) {
        if (isUserAlreadyLoggedIn) NavigationDestination.Home.route else NavigationDestination.Login.route
    }


    // Ẩn bottom bar nếu route thuộc list dưới
    val notShowBottomBarRoutes = listOf(
        "chat_detail/{chatId}",

        // login
        AppRoutes.LOGIN,
        //register
        AppRoutes.REGISTER,
        //forgot
        AppRoutes.FORGOT_PASSWORD1,
        AppRoutes.FORGOT_PASSWORD2,
        AppRoutes.FORGOT_PASSWORD3,

        //
        AppRoutes.CREATE_JOB,
        AppRoutes.JOB_DETAIL,
        //
        AppRoutes.CANDIDATE_LIST,
        AppRoutes.CANDIDATE_DETAIL,

        //
        AppRoutes.MESSAGE,

        //update profile pagfe
        AppRoutes.UPDATE_PROFILE
    )

    val shouldShowBottomBar =
        !notShowBottomBarRoutes.contains(currentRoute) // hien thi bottom app bar
    val showFloatingButton = currentRoute == AppRoutes.WORK_SPACE

    // Định nghĩa các item bottom nav
    val bottomBarItems: List<MyNavBarItem> = listOf(
        MyNavBarItem("home", Icons.Outlined.Home, 0, AppRoutes.HOME),
        MyNavBarItem("work_space", Icons.Outlined.CalendarToday, 0, AppRoutes.WORK_SPACE),
        MyNavBarItem("candidate", Icons.Outlined.Group, 0, AppRoutes.CANDIDATE_MANAGEMENT),
        MyNavBarItem("notification", Icons.Outlined.Notifications, 5, AppRoutes.NOTIFICATION),
        MyNavBarItem("profile", Icons.Outlined.Person, 0, AppRoutes.PROFILE),
    )

    // Tạo scaffold
    Scaffold(
        bottomBar = {
            if (shouldShowBottomBar) {
                NavigationBar {
                    bottomBarItems.forEach { navItem ->
//                        val selected = currentRoute == navItem.route

                        // Better - Handle nested routes
                        val selected = currentRoute == navItem.route ||
                                (currentRoute?.startsWith(navItem.route + "/") == true)
//                        This handles cases where a child route should keep the parent tab selected.

                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                // Điều hướng sang route
//                                navController.navigate(navItem.route) {
//                                    // Xoá stack cũ, tuỳ logic
//                                    popUpTo(navController.graph.startDestinationId) {
//                                        saveState = true
//                                    }
//                                    launchSingleTop = true
//                                    restoreState = true
//                                }

                                navController.navigate(navItem.route) {
                                    // Pop up to the start destination of the graph to
                                    // avoid building up a large stack of destinations
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    // Avoid multiple copies of the same destination
                                    launchSingleTop = true
                                    // Restore state when reselecting a previously selected item
                                    restoreState = true
                                }
                            },
                            icon = {
                                BadgedBox(
                                    badge = {
                                        if (navItem.badge > 0) {
                                            Badge {
                                                Text(text = navItem.badge.toString())
                                            }
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = navItem.icon,
                                        contentDescription = navItem.label
                                    )
                                }
                            }
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            if (showFloatingButton) {
                FloatingActionButton(
                    onClick = {
                        navController.safeNavigate(AppRoutes.CREATE_JOB, popUpToRoute = AppRoutes.WORK_SPACE)
                    }
                ) {
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = "Floating action button to add new Post"
                    )
                }
            }
        },
        //add the snackbar host
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        // NavHost
        AppNavHost(
            navController = navController,
            chatViewModel = chatViewModel,
            modifier = Modifier.padding(innerPadding),
            //pass the snackbar state and scope to the NavHost
            snackbarHostState = snackbarHostState,
            snackbarScope = scope,
            authViewModel = authViewModel,
            startDestination = startDestination // Pass the dynamic start destination

        )
    }
}

