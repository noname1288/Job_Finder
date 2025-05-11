package com.example.jobfinder.navigation

/*
* WHY NEED Type-Safe Navigation ??
*
*
* */
sealed interface NavigationDestination {
    val route: String

    // Main destinations
    object Home : NavigationDestination {
        override val route = "home"
    }

    object Workspace : NavigationDestination {
        override val route = "workspace"
    }

    object CandidateManagement : NavigationDestination {
        override val route = "candidate_management"
    }

    object Notification : NavigationDestination {
        override val route = "notification"
    }

    object Profile : NavigationDestination {
        override val route = "profile"
    }

    // Authentication destinations
    object Login : NavigationDestination {
        override val route = "login"
    }

    object Register : NavigationDestination {
        override val route = "register"
    }

    // Parameterized destinations
    data class ChatDetail(val chatId: String) : NavigationDestination {
        override val route = "chat_detail/$chatId"

        companion object {
            const val baseRoute = "chat_detail/{chatId}"
        }
    }

    // Helper to get all bottom nav destinations
    companion object {
        val bottomNavDestinations = listOf(Home, Workspace, CandidateManagement, Notification, Profile)
    }
}