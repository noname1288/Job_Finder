package com.example.jobfinder.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.jobfinder.presentation.candidate.CandidateListPage
import com.example.jobfinder.presentation.candidate.CandidateManagementPage
import com.example.jobfinder.presentation.candidate.CandidateProfilePage
import com.example.jobfinder.presentation.forgotpassword.ForgetPage1
import com.example.jobfinder.presentation.forgotpassword.ForgetPage2
import com.example.jobfinder.presentation.forgotpassword.ForgetPage3
import com.example.jobfinder.presentation.home.HomePage
import com.example.jobfinder.presentation.login.LoginPage
import com.example.jobfinder.presentation.login.LoginViewModel
import com.example.jobfinder.presentation.message.ChatDetailPage
import com.example.jobfinder.presentation.message.MessagePage
import com.example.jobfinder.presentation.notification.NotificationPage
import com.example.jobfinder.presentation.profile.ProfilePage
import com.example.jobfinder.presentation.workspace.WorkspacePage
import com.example.jobfinder.presentation.message.ChatViewModel
import com.example.jobfinder.presentation.workspace.create.CreateJobPage
import com.example.jobfinder.pages.jobdetail.JobDetailPage

@Composable
fun AppNavHost(
//    innerPadding: PaddingValues,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    chatViewModel: ChatViewModel,
    loginViewModel: LoginViewModel
) {
    val innerPadding = null
    NavHost(
        navController = navController,
        startDestination = AppRoutes.HOME,
        modifier = modifier
    ) {
        composable( AppRoutes.HOME) {
            HomePage(navController)
        }

        composable(AppRoutes.WORK_SPACE) {
            WorkspacePage(navController)
        }
        composable(AppRoutes.JOB_DETAIL) {
            JobDetailPage(navController)
        }
        composable(AppRoutes.CREATE_JOB) {
            // Màn hình tạo job
            CreateJobPage(navController)
        }

        composable (AppRoutes.CANDIDATE_MANAGEMENT){
            CandidateManagementPage(navController)
        }
        composable(AppRoutes.CANDIDATE_LIST) {
            CandidateListPage(navController)
        }
        composable (AppRoutes.CANDIDATE_DETAIL){
            CandidateProfilePage(navController)
        }

        composable( AppRoutes.MESSAGE) {
            // Màn hình danh sách chat
            MessagePage(
                chatViewModel = chatViewModel,
                onChatClicked = { chatItem ->
                    navController.navigate("chat_detail/${chatItem.id}")
                },
                navController = navController
            )
        }
        composable( AppRoutes.NOTIFICATION) {
            NotificationPage()
        }
        composable( AppRoutes.PROFILE) {
            ProfilePage(navController)
        }

        composable( AppRoutes.LOGIN) {
            LoginPage(navController, loginViewModel)
        }
        composable( AppRoutes.FORGOT_PASSWORD1) {
            ForgetPage1(navController)
        }
        composable( AppRoutes.FORGOT_PASSWORD2) {
            ForgetPage2(navController)
        }
        composable( AppRoutes.FORGOT_PASSWORD3) {
            ForgetPage3(navController)
        }

        // Route chat_detail
        composable(
            "chat_detail/{chatId}",
            arguments = listOf(navArgument("chatId") { type = NavType.StringType })
        ) { backStackEntry ->
            val chatId = backStackEntry.arguments?.getString("chatId") ?: ""
            ChatDetailPage(
                chatId = chatId,
                navController = navController,
                chatViewModel = chatViewModel
            )
        }
    }
}
