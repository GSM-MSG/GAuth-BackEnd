package com.msg.gauth.domain.image.presentation

import com.amazonaws.Response
import com.msg.gauth.domain.image.presentation.dto.response.UploadImageResDto
import com.msg.gauth.domain.image.service.DeleteImageService
import com.msg.gauth.domain.image.service.UploadImageService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/image")
class ImageController(
    private val uploadImageService: UploadImageService,
    private val deleteImageService: DeleteImageService
) {
    @PostMapping
    fun uploadImage(@RequestPart image: MultipartFile): ResponseEntity<UploadImageResDto> {
        val result = uploadImageService.execute(image)
        return ResponseEntity.ok(result)
    }

    @DeleteMapping
    fun deleteImage(@RequestParam url: String): ResponseEntity<Void> {
        deleteImageService.execute(url)
        return ResponseEntity.ok().build()
    }
}