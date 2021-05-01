package com.castprogramms.karma.tools

import com.castprogramms.karma.tools.time.DataTime

data class Service(
    var name: String = "",
    var cost: Int = 0,
    var desc : String = "",
    var photo: String = "",
    var idAuthor : String ="",
    var dataTime: DataTime = DataTime(),
    var unit : String = ""
)