package com.example.jobfinder.navigation

import androidx.navigation.NavController


/*
*Analysis:

✅ Sets launchSingleTop = true to avoid duplicate screens
✅ Configures popUpTo with saveState option
✅ Handles restoration of state
✅ Default popUpToRoute is HOME, which makes sense as a common base
✅ Good default parameters that make the function flexible
* */
fun NavController.safeNavigate(
    route: String,
    popUpToRoute: String = AppRoutes.HOME,
    isInclusive: Boolean = false,
    restore: Boolean = true
) {
    this.navigate(route) {
        popUpTo(popUpToRoute) {
            inclusive = isInclusive
            saveState = restore
        }
        launchSingleTop = true
        restoreState = restore
    }
}

/*
*Analysis:

✅ Safely checks if popBackStack is possible
✅ Simple utility function with clear purpose
✅ Contains comment space for further implementation
* */

fun NavController.popBackIfCan() {
    // Nếu có thể pop ra khỏi stack thì tiến hành pop
    if (popBackStack()) {
        // Có thể xử lý thêm nếu cần, ví dụ log hoặc thực hiện một hành động sau pop
    }
}

/*
*Analysis:

✅ Handles formatting of route with arguments
✅ Uses same navigation options as safeNavigate
✅ Uses varargs for flexible number of arguments
Usage example: navController.navigateWithArgs("detail/%s", itemId)
* */
fun NavController.navigateWithArgs(
    route: String,
    vararg args: Any,
    popUpToRoute: String = AppRoutes.HOME,
    isInclusive: Boolean = false,
    restore: Boolean = true
) {
    // Giả sử route có định dạng như "detail/%s" và args sẽ thay thế %s
    val formattedRoute = String.format(route, *args)
    this.navigate(formattedRoute) {
        popUpTo(popUpToRoute) {
            inclusive = isInclusive
            saveState = restore
        }
        launchSingleTop = true
        restoreState = restore
    }
}

/*
*Analysis:

✅ Checks current route to prevent unnecessary navigation
✅ Reuses safeNavigate for consistent behavior
✅ Prevents duplicate navigation requests
Usage example: navController.navigateOnce(AppRoutes.DETAILS)
* */
fun NavController.navigateOnce(
    route: String,
    popUpToRoute: String = AppRoutes.HOME,
    isInclusive: Boolean = false,
    restore: Boolean = true
) {
    // Kiểm tra nếu route hiện tại khác với route cần điều hướng
    if (currentBackStackEntry?.destination?.route != route) {
        // Sử dụng hàm safeNavigate đã định nghĩa từ trước để đảm bảo cấu hình phù hợp
        safeNavigate(route, popUpToRoute, isInclusive, restore)
    }
}




