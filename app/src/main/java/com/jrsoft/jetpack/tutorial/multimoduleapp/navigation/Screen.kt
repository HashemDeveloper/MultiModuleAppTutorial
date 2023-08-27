package com.jrsoft.jetpack.tutorial.multimoduleapp.navigation

import com.jrsoft.jetpack.tutorial.multimoduleapp.utils.Constants.WRITE_SCREEN_DIARY_KEY

sealed class Screen (val route: String) {
    object Authentication: Screen(route = "authentication_screen")
    object Home: Screen(route = "home_screen")
    object Write: Screen(route = "write_screen?${WRITE_SCREEN_DIARY_KEY}={${WRITE_SCREEN_DIARY_KEY}}") {
        fun passDiaryId(diaryId: String) = "write_screen?${WRITE_SCREEN_DIARY_KEY}=${diaryId}"
    }
}
