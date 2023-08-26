package com.msg.gauth.domain.client.service

import com.msg.gauth.domain.client.presentation.dto.request.ClientRegisterReqDto
import com.msg.gauth.domain.client.presentation.dto.response.ClientRegisterResDto
import com.msg.gauth.domain.client.repository.ClientRepository
import com.msg.gauth.domain.user.util.UserUtil
import com.msg.gauth.global.annotation.service.TransactionalService
import com.msg.gauth.global.thirdparty.aws.s3.S3Util
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@TransactionalService
class RegisterClientService(
    private val clientRepository: ClientRepository,
    private val userUtil: UserUtil,
    private val s3Util: S3Util
) {
    fun execute(image: MultipartFile, clientRegisterDto: ClientRegisterReqDto): ClientRegisterResDto {
        val (clientSecret, clientId) = createUUID() to createUUID()

        val user = userUtil.fetchCurrentUser()

        val url: String = s3Util.upload(image)

        val client = clientRegisterDto.toEntity(user, clientSecret, clientId, url)

        return ClientRegisterResDto(clientRepository.save(client))
    }

    private fun createUUID() = (UUID.randomUUID().toString() + UUID.randomUUID().toString())
        .replace("-", "")
}