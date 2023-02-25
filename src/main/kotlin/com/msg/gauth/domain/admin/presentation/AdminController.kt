package com.msg.gauth.domain.admin.presentation

import com.msg.gauth.domain.admin.presentation.dto.ClientResDto
import com.msg.gauth.domain.admin.services.ExcelParsingService
import com.msg.gauth.domain.admin.services.GetClientsByServiceNameService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/admin")
class AdminController(
    private val excelParsingService: ExcelParsingService,
    private val getClientsByServiceNameService: GetClientsByServiceNameService
) {
    @GetMapping
    fun getClientsByServiceName(pageable: Pageable,@RequestParam serviceName: String): ResponseEntity<Page<ClientResDto>> {
        val result = getClientsByServiceNameService.execute(pageable, serviceName)
        return ResponseEntity.ok(result)
    }

    @PostMapping("/parsing-member")
    fun parseMember(@RequestParam("studentInfo") file: MultipartFile): ResponseEntity<Void>{
        excelParsingService.execute(file)
        return ResponseEntity.noContent().build()
    }
}