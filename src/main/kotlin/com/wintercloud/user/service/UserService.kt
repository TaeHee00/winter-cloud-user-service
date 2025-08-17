package com.wintercloud.user.service

import com.wintercloud.user.entity.User
import com.wintercloud.user.exception.BusinessException
import com.wintercloud.user.exception.ErrorCode
import com.wintercloud.user.repository.UserRepository
import com.wintercloud.user.utils.UUIDv7
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    @Transactional
    suspend fun create(
        email: String,
        password: String,
        name: String,
    ): User {
        checkAlreadyEmail(email)

        return userRepository.save(
            entity = User(
                id = UUIDv7.randomUUID(),
                email = email,
                password = password,
                name = name,
            )
        )
    }

    @Transactional(readOnly = true)
    suspend fun getUserInfo(userId: UUID): User {
        return userRepository.findById(userId) ?: throw BusinessException(ErrorCode.USER_NOT_FOUND)
    }

    @Transactional(readOnly = true)
    suspend fun getUserId(email: String, password: String): UUID {
        val user = userRepository.findByEmail(email) ?: throw BusinessException(ErrorCode.USER_NOT_FOUND)
        checkPassword(password, user.password)

        return user.id
    }

    private suspend fun checkAlreadyEmail(email: String) {
        if (userRepository.existsByEmail(email)) throw BusinessException(ErrorCode.ALREADY_EMAIL)
    }

    private fun checkPassword(password: String, encodedPassword: String) {
        // TODO: passwordEncoder 로 변경
        if (password != encodedPassword) throw BusinessException(ErrorCode.INVALID_PASSWORD)
    }

}