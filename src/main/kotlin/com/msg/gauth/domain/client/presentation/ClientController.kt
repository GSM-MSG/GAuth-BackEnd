package com.msg.gauth.domain.client.presentation

import com.msg.gauth.domain.client.presentation.dto.response.ClientResDto
import com.msg.gauth.domain.client.presentation.dto.request.ClientRegisterReqDto
import com.msg.gauth.domain.client.presentation.dto.request.ClientUpdateReqDto
import com.msg.gauth.domain.client.presentation.dto.response.SingleClientResDto
import com.msg.gauth.domain.client.presentation.dto.response.ClientDetailResDto
import com.msg.gauth.domain.client.presentation.dto.response.ClientRegisterResDto
import com.msg.gauth.domain.client.services.*
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/client")
class ClientController(
    private val getAllClientsService: GetAllClientsService,
    private val updateClientService: UpdateClientService,
    private val getMyDetailClientService: GetMyDetailClientService,
    private val registerClientService: RegisterClientService,
    private val deleteClientService: DeleteClientService,
    private val getClientsByServiceNameService: GetClientsByServiceNameService
) {

    @PostMapping
    fun registerClient(@RequestBody clientRegisterReqDto: ClientRegisterReqDto): ResponseEntity<ClientRegisterResDto>{
        val clientRegisterResDto = registerClientService.execute(clientRegisterReqDto)
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
    fun updateClient(@PathVariable id: Long, @RequestBody clientUpdateDto: ClientUpdateReqDto): ResponseEntity<Void>{
        updateClientService.updateClient(id, clientUpdateDto)
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
}