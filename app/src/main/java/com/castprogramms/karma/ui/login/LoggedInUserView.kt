package com.castprogramms.karma.ui.login

/**
 * User details post authentication that is exposed to the UI
 */
data class LoggedInUserView(
    val displayName: String,
    val id: String,
    val isFirstEnter: Boolean
    //... other data fields that may be accessible to the UI
)