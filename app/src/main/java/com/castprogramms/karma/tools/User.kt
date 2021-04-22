package com.castprogramms.karma.tools

data class User(
    var id: String = "",
    var name: String = "",
    var scores: List<Score> = listOf()
)
