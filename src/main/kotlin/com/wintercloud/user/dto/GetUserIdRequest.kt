package com.wintercloud.user.dto

data class GetUserIdRequest(
    val email: String,
    val password: String,
)
