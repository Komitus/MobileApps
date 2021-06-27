package com.example.ex2.userPanel

import com.google.firebase.database.PropertyName
data class PairIDChat(
    @PropertyName("userToMessage") val userToMessage: String?,
    @PropertyName("chatId") val chatId: String?,
){
    constructor() : this(null, null)
}
data class User(
    @PropertyName("age") val age: Int,
    @PropertyName("email") val email: String,
    @PropertyName("fullName") val fullName: String,
    @PropertyName("nickName") val nickName: String,
) {
    constructor() : this(0, "", "", "", )
}
