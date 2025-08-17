package com.wintercloud.user.controller

import com.wintercloud.user.dto.CreateUserRequest
import com.wintercloud.user.dto.UserIdResponse
import com.wintercloud.user.dto.UserInfoResponse
import com.wintercloud.user.entity.User
import com.wintercloud.user.service.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class UserController(
    private val userService: UserService,
) {

    @GetMapping("/info")
    suspend fun getUserInfo(
        @RequestHeader("X-User-Id") userId: UUID,
    ): ResponseEntity<UserInfoResponse> {
        return ResponseEntity.ok(
            UserInfoResponse.fromEntity(userService.getUserInfo(userId))
        )
    }

    @GetMapping("/user-id")
    suspend fun getUserId(
        @RequestParam(value = "email", required = true) email: String,
        @RequestParam(value = "password", required = true) password: String
    ): ResponseEntity<UserIdResponse> {
        return ResponseEntity.ok(
            UserIdResponse(userService.getUserId(email, password))
        )
    }

    @PostMapping("/signup")
    suspend fun signup(
        @Valid @RequestBody
        request: CreateUserRequest,
    ): ResponseEntity<UserInfoResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                UserInfoResponse.fromEntity(
                    userService.create(
                        email = request.email,
                        password = request.password,
                        name = request.name
                    )
                )
            )
    }
}