package com.msg.gauth.domain.client.persentation

import com.msg.gauth.domain.client.persentation.dto.request.ClientRegisterReqDto
import com.msg.gauth.domain.client.persentation.dto.request.ClientUpdateReqDto
import com.msg.gauth.domain.client.persentation.dto.response.SingleClientResDto
import com.msg.gauth.domain.client.persentation.dto.response.ClientDetailResDto
import com.msg.gauth.domain.client.persentation.dto.response.ClientRegisterResDto
import com.msg.gauth.domain.client.services.GetMyAllClientsService
import com.msg.gauth.domain.client.services.GetMyDetailClientService
import com.msg.gauth.domain.client.services.RegisterClientService
import com.msg.gauth.domain.client.services.UpdateClientService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/client")
class ClientController(
    private val getMyAllClientsService: GetMyAllClientsService,
    private val updateClientService: UpdateClientService,
    private val getMyDetailClientService: GetMyDetailClientService,
    private val registerClientService: RegisterClientService,
) {

    @PostMapping
    fun registerClient(@RequestBody clientRegisterReqDto: ClientRegisterReqDto): ResponseEntity<ClientRegisterResDto>{
        val clientRegisterResDto = registerClientService.execute(clientRegisterReqDto)
        return ResponseEntity.ok(clientRegisterResDto)
    }

    @GetMapping
    fun getMyAllClients(): ResponseEntity<List<SingleClientResDto>>{
        val result = getMyAllClientsService.execute()
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
}