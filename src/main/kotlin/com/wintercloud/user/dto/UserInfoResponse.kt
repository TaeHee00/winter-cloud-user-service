package com.wintercloud.user.dto

import com.wintercloud.user.entity.User
import java.time.LocalDateTime

data class UserInfoResponse(
    val email: String,
    val name: String,
    val createdAt: LocalDateTime?
) {
    companion object {
        fun fromEntity(entity: User): UserInfoResponse {
            return UserInfoResponse(
                email = entity.email,
                name = entity.name,
                createdAt = entity.createdAt,
            )
        }
    }
}
