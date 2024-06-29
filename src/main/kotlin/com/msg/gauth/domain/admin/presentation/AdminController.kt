package com.msg.gauth.domain.admin.presentation

import com.msg.gauth.domain.admin.service.ExcelParsingService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/admin")
class AdminController(
    private val excelParsingService: ExcelParsingService
) {

    @PostMapping("/parsing-member")
    fun parseMember(@RequestParam("studentInfo") file: MultipartFile): ResponseEntity<Void> {
        excelParsingService.execute(file)
        return ResponseEntity.noContent().build()
    }
}