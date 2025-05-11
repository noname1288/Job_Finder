package com.example.jobfinder.navigation

object AppRoutes {
    const val HOME = "home"

    const val LOGIN = "login"
    const val REGISTER = "register"

    const val FORGOT_PASSWORD1 = "forgot_password1"
    const val FORGOT_PASSWORD2 = "forgot_password2"
    const val FORGOT_PASSWORD3 = "forgot_password3"

    const val WORK_SPACE = "workspace"
    const val JOB_DETAIL = "job_detail"
    const val CREATE_JOB = "create_job"


    const val MESSAGE = "message"
    const val NOTIFICATION = "notification"
    const val PROFILE = "profile"

    const val CANDIDATE_MANAGEMENT = "candidate_management"
    const val CANDIDATE_LIST = "candidate_list"
    const val CANDIDATE_DETAIL = "candidate_detail"

    // ...existing routes...
    const val CHAT_DETAIL = "chat_detail/{chatId}"
    // Route builder function
    fun chatDetail(chatId: String) = "chat_detail/$chatId"

}