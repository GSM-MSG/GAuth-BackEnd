package com.msg.gauth.domain.client.presentation

import com.msg.gauth.domain.client.presentation.dto.response.ClientResDto
import com.msg.gauth.domain.client.presentation.dto.request.ClientRegisterReqDto
import com.msg.gauth.domain.client.presentation.dto.request.ClientUpdateReqDto
import com.msg.gauth.domain.client.presentation.dto.response.SingleClientResDto
import com.msg.gauth.domain.client.presentation.dto.response.ClientDetailResDto
import com.msg.gauth.domain.client.presentation.dto.response.ClientRegisterResDto
import com.msg.gauth.domain.client.service.*
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/client")
class ClientController(
    private val getAllClientsService: GetAllClientsService,
    private val updateClientService: UpdateClientService,
    private val getMyDetailClientService: GetMyDetailClientService,
    private val registerClientService: RegisterClientService,
    private val deleteClientService: DeleteClientService,
    private val getClientsByServiceNameService: GetClientsByServiceNameService,
    private val updateAnyClientService: UpdateAnyClientService,
    private val deleteClientsService: DeleteClientsService
) {

    @PostMapping
    fun registerClient(
        @RequestPart(name = "image") image: MultipartFile,
        @RequestBody clientRegisterReqDto: ClientRegisterReqDto
    ): ResponseEntity<ClientRegisterResDto>{
        val clientRegisterResDto = registerClientService.execute(image, clientRegisterReqDto)
        return ResponseEntity.ok(clientRegisterResDto)
    }

    @GetMapping
    fun getAllClients(): ResponseEntity<List<SingleClientResDto>>{
        val result = getAllClientsService.execute()
        return ResponseEntity.ok(result)
    }

    @GetMapping("/{id}")
    fun getMyDetailClient(@PathVariable id: Long): ResponseEntity<ClientDetailResDto>{
        val clientOneResDto = getMyDetailClientService.execute(id)
        return ResponseEntity.ok(clientOneResDto)
    }

    @PatchMapping("/{id}")
    fun updateClient(
        @PathVariable id: Long,
        @RequestBody clientUpdateDto: ClientUpdateReqDto,
        @RequestPart(name = "image", required = false) image: MultipartFile?
    ): ResponseEntity<Void>{
        updateClientService.updateClient(id, clientUpdateDto, image)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/{id}")
    fun deleteClient(@PathVariable id: Long): ResponseEntity<Void>{
        deleteClientService.execute(id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/search")
    fun getClientsByServiceName(@RequestParam(defaultValue = "0") page: Int, @RequestParam(defaultValue = "10") size: Int, @RequestParam serviceName: String): ResponseEntity<Page<ClientResDto>> {
        val result = getClientsByServiceNameService.execute(page, size, serviceName)
        return ResponseEntity.ok(result)
    }

    @PatchMapping("/{id}/patch")
    fun updateAnyClient(
        @PathVariable id: Long,
        @RequestBody clientUpdateDto: ClientUpdateReqDto,
        @RequestPart(name = "image", required = false) image: MultipartFile
    ): ResponseEntity<Void>{
        updateAnyClientService.execute(id, clientUpdateDto, image)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping
    fun deleteSeveralClient(@RequestParam(value = "ids") ids: List<Long>): ResponseEntity<Void> {
        deleteClientsService.execute(ids)
        return ResponseEntity.noContent().build()
    }
}