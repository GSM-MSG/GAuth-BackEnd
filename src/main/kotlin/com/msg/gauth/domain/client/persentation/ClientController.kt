package com.msg.gauth.domain.client.persentation

import com.msg.gauth.domain.client.persentation.dto.request.ClientRegisterReqDto
import com.msg.gauth.domain.client.persentation.dto.request.ClientUpdateReqDto
import com.msg.gauth.domain.client.persentation.dto.response.ClientAllResDto
import com.msg.gauth.domain.client.persentation.dto.response.ClientOneResDto
import com.msg.gauth.domain.client.persentation.dto.response.ClientRegisterResDto
import com.msg.gauth.domain.client.services.GetAllClientsService
import com.msg.gauth.domain.client.services.GetOneClientService
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
    private val getAllClientsService: GetAllClientsService,
    private val updateClientService: UpdateClientService,
    private val getOneClientService: GetOneClientService,
    private val registerClientService: RegisterClientService,
){

    @PostMapping
    fun registerClient(@RequestBody clientRegisterReqDto: ClientRegisterReqDto):ResponseEntity<ClientRegisterResDto>{
        val clientRegisterResDto = registerClientService.execute(clientRegisterReqDto)
        return ResponseEntity.ok(clientRegisterResDto)
    }

    @GetMapping
    fun getMyAllClients(): ResponseEntity<List<ClientAllResDto>>{
        val result = getAllClientsService.execute()
        return ResponseEntity.ok(result)
    }

    @GetMapping("/{id}")
    fun getMyOneClient(@PathVariable id:String): ResponseEntity<ClientOneResDto>{
        val clientOneResDto = getOneClientService.execute(id)
        return ResponseEntity.ok(clientOneResDto)
    }

    @PatchMapping("/{id}")
    fun updateClient(@PathVariable id:String, @RequestBody clientUpdateDto: ClientUpdateReqDto): ResponseEntity<Void>{
        updateClientService.updateClient(id, clientUpdateDto)
        return ResponseEntity.ok().build()
    }
}