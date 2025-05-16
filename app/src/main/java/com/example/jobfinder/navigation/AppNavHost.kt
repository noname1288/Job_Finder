package com.example.jobfinder.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.jobfinder.pages.jobdetail.JobDetailPage
import com.example.jobfinder.presentation.AuthViewModel
import com.example.jobfinder.presentation.BaseViewModelFactory
import com.example.jobfinder.presentation.candidate.CandidateListPage
import com.example.jobfinder.presentation.candidate.CandidateManagementPage
import com.example.jobfinder.presentation.candidate.CandidateProfilePage
import com.example.jobfinder.presentation.forgotpassword.ForgetPage1
import com.example.jobfinder.presentation.forgotpassword.ForgetPage2
import com.example.jobfinder.presentation.forgotpassword.ForgetPage3
import com.example.jobfinder.presentation.home.HomePage
import com.example.jobfinder.presentation.login.LoginPage
import com.example.jobfinder.presentation.message.ChatDetailPage
import com.example.jobfinder.presentation.message.ChatViewModel
import com.example.jobfinder.presentation.message.MessagePage
import com.example.jobfinder.presentation.notification.NotificationPage
import com.example.jobfinder.presentation.profile.ProfilePage
import com.example.jobfinder.presentation.profile.update.UpdateProfilePage
import com.example.jobfinder.presentation.register.RegisterPage
import com.example.jobfinder.presentation.workspace.WorkspacePage
import com.example.jobfinder.presentation.workspace.create.CreateJobPage
import com.example.jobfinder.service_locator.AppContainer
import kotlinx.coroutines.CoroutineScope

@Composable
fun AppNavHost(
//    innerPadding: PaddingValues,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    snackbarScope: CoroutineScope,
    authViewModel: AuthViewModel,
    chatViewModel: ChatViewModel,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = AppRoutes.HOME,
        modifier = modifier
    ) {
        /*
        * CÁC MÀN ĐĂNG NHẬP, ĐĂNG KÍ, LẤY MẬT KHẨU
         * */
        //Màn hình đăng nhập
        composable(AppRoutes.LOGIN) {
            LoginPage(modifier, navController, authViewModel)
        }

        //Màn hình đăng ký
        composable(AppRoutes.REGISTER) {
            RegisterPage(navController, authViewModel)
        }

        //Màn hình quên mật khẩu
        composable(AppRoutes.FORGOT_PASSWORD1) {
            ForgetPage1(navController)
        }
        composable(AppRoutes.FORGOT_PASSWORD2) {
            ForgetPage2(navController)
        }
        composable(AppRoutes.FORGOT_PASSWORD3) {
            ForgetPage3(navController)
        }


        /*
        * TRANG HOME
         * */
        //Màn hình trang chính
        composable(NavigationDestination.Home.route) {
            HomePage(navController)
        }


        /*
        * CÁC MÀN QUẢN LÝ CÔNG VIỆC: DANH SÁCH, CHI TIẾT, THÊM/SỬA/XÓA CÔNG VIỆC
         * */
        //Màn hình trang quản lý công việc
        composable(AppRoutes.WORK_SPACE) {
            WorkspacePage(navController)
        }
        //Màn hình xem chi tiết 1 công việc
        composable(AppRoutes.JOB_DETAIL) {
            JobDetailPage(navController)
        }
        //Màn hình tạo 1 công việc
        composable(AppRoutes.CREATE_JOB) {
            // Màn hình tạo job
            CreateJobPage(navController)
        }


        /*
        * CÁC MÀN QUẢN LÝ ỨNG VIÊN: DANH SÁCH, CHI TIẾT, PHÊ DUYỆT/TỪ CHỐI ỨNG VIÊN
         * */
        composable(AppRoutes.CANDIDATE_MANAGEMENT) {
            CandidateManagementPage(navController)
        }
        composable(AppRoutes.CANDIDATE_LIST) {
            CandidateListPage(navController)
        }
        composable(AppRoutes.CANDIDATE_DETAIL) {
            CandidateProfilePage(navController)
        }


        /*
        * CÁC MÀN CHAT VỚI ỨNG VIÊN
         * */
        composable(AppRoutes.MESSAGE) {
            // Màn hình danh sách chat
            MessagePage(
                chatViewModel = chatViewModel,
                onChatClicked = { chatItem ->
                    navController.navigate("chat_detail/${chatItem.id}")
                },
                navController = navController
            )
        }
        //Chi tiết 1 màn hình chat
        // For parameterized routes:
        composable(
            NavigationDestination.ChatDetail.baseRoute,
            arguments = listOf(navArgument("chatId") { type = NavType.StringType })
        ) { backStackEntry ->
            val chatId = backStackEntry.arguments?.getString("chatId") ?: ""
            ChatDetailPage(
                chatId = chatId,
                navController = navController,
                chatViewModel = chatViewModel
            )
        }


        /*
        * CÁC MÀN LẤY THÔNG BÁO CỦA HỆ THỐNG
         * */
        composable(AppRoutes.NOTIFICATION) {
            NotificationPage()
        }

        /*
        * XEM CHI TIẾT PROFILE/ (SETTING)
         * */
        composable(AppRoutes.PROFILE) {
            ProfilePage(navController, authViewModel)
        }
        composable(AppRoutes.UPDATE_PROFILE) {
            UpdateProfilePage(navController)
        }


    }
}
