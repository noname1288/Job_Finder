package com.example.jobfinder.feature

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Videocam
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.jobfinder.feature.login.LoginViewModel
import com.example.jobfinder.feature.message.ChatViewModel
import com.example.jobfinder.navigation.AppNavHost
import com.example.jobfinder.navigation.AppRoutes
import com.example.jobfinder.utils.MyNavBarItem

/**
 * Màn hình chính – chưa hẳn 1 screen.
 * Tại đây, ta có 1 NavController chung -> AppNavHost + BottomNav
 * Ẩn bottomBar khi route = "chat_detail/{chatId}"
 */
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    //khai bao viewmodel o composable root
    val chatViewModel: ChatViewModel = viewModel()
    val loginViewModel: LoginViewModel = viewModel()

    // Theo dõi route hiện tại
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    LaunchedEffect(currentRoute) {
        Log.d("Navigation", "Current route: $currentRoute")
    }

    // Ẩn bottom bar nếu route thuộc list dưới
    val notShowBottomBarRoutes = listOf(
        "chat_detail/{chatId}",

        // login, forgot
        AppRoutes.LOGIN,
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
    )
    val shouldShowBottomBar = !notShowBottomBarRoutes.contains(currentRoute) // hien thi bottom app bar
    val showFloatingButton = currentRoute == AppRoutes.WORK_SPACE

    // Định nghĩa các item bottom nav
    val bottomBarItems: List<MyNavBarItem> = listOf(
        MyNavBarItem("home", Icons.Outlined.Home, 0, AppRoutes.HOME),
        MyNavBarItem("work_space", Icons.Outlined.CalendarToday, 0, AppRoutes.WORK_SPACE),
        MyNavBarItem("candidate", Icons.Outlined.Group, 0,AppRoutes.CANDIDATE_MANAGEMENT),
        MyNavBarItem("notification", Icons.Outlined.Notifications, 5, AppRoutes.NOTIFICATION),
        MyNavBarItem("profile", Icons.Outlined.Person, 0, AppRoutes.PROFILE),
    )

    // Tạo scaffold
    Scaffold(
        bottomBar = {
            if (shouldShowBottomBar) {
                NavigationBar {
                    bottomBarItems.forEach { navItem ->
                        val selected = currentRoute == navItem.route
                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                // Điều hướng sang route
                                navController.navigate(navItem.route) {
                                    // Xoá stack cũ, tuỳ logic
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
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
            if (showFloatingButton){
                FloatingActionButton(
                    onClick = {
                        navController.navigate(AppRoutes.CREATE_JOB)
                    }
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Floating action button to add new Post")
                }
            }
        }

    ) { innerPadding ->
        // NavHost
        AppNavHost(
            navController = navController,
            chatViewModel = chatViewModel,
            loginViewModel = loginViewModel,
//            innerPadding = innerPadding
            modifier = Modifier.padding(innerPadding)
        )
    }
}

