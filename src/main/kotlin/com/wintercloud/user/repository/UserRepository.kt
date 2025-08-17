package com.wintercloud.user.repository

import com.wintercloud.user.entity.User
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserRepository : CoroutineCrudRepository<User, UUID> {

    suspend fun findByEmail(email: String): User?
    suspend fun existsByEmail(email: String): Boolean
}