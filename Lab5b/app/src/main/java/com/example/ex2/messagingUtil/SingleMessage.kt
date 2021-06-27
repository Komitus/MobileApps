package com.example.ex2.messagingUtil

import com.google.firebase.database.ServerValue

data class SingleMessage(
    var message: String?,
    var userId: String?,
    var timeStamp: Long?
    ) {
    constructor() : this(null, null, null)

}
