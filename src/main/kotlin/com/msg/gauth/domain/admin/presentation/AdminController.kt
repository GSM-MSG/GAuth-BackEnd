package com.msg.gauth.domain.admin.presentation

import com.msg.gauth.domain.admin.services.ExcelParsingService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/admin")
class AdminController(
    val excelParsingService: ExcelParsingService
) {
    @PostMapping("/parsing-member")
    fun parseMember(@RequestParam("studentInfo") file: MultipartFile): ResponseEntity<Void>{
        excelParsingService.execute(file)
        return ResponseEntity.noContent().build()
    }
}