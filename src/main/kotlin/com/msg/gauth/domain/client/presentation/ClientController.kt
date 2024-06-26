package com.msg.gauth.domain.client.presentation

import com.msg.gauth.domain.client.presentation.dto.request.ClientRegisterReqDto
import com.msg.gauth.domain.client.presentation.dto.request.ClientUpdateReqDto
import com.msg.gauth.domain.client.presentation.dto.request.AddCoworkerReqDto
import com.msg.gauth.domain.client.presentation.dto.request.DeleteCoworkerReqDto
import com.msg.gauth.domain.client.presentation.dto.response.*
import com.msg.gauth.domain.client.service.*
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

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
    private val deleteClientsService: DeleteClientsService,
    private val delegateOwnerService: DelegateOwnerService,
    private val addCoworkerService: AddCoworkerService,
    private val deleteCoworkerService: DeleteCoworkerService,
    private val getCoworkerService: GetCoworkerService
) {

    @PostMapping
    fun registerClient(@RequestBody clientRegisterReqDto: ClientRegisterReqDto): ResponseEntity<ClientRegisterResDto> {
        val clientRegisterResDto = registerClientService.execute(clientRegisterReqDto)
        return ResponseEntity.ok(clientRegisterResDto)
    }

    @GetMapping
    fun getAllClients(): ResponseEntity<List<SingleClientResDto>> {
        val result = getAllClientsService.execute()
        return ResponseEntity.ok(result)
    }

    @GetMapping("/{id}")
    fun getMyDetailClient(@PathVariable id: Long): ResponseEntity<ClientDetailResDto> {
        val clientOneResDto = getMyDetailClientService.execute(id)
        return ResponseEntity.ok(clientOneResDto)
    }

    @PatchMapping("/{id}")
    fun updateClient(
        @PathVariable id: Long,
        @RequestBody clientUpdateDto: ClientUpdateReqDto
    ): ResponseEntity<Void> {
        updateClientService.updateClient(id, clientUpdateDto)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/{id}")
    fun deleteClient(@PathVariable id: Long): ResponseEntity<Void> {
        deleteClientService.execute(id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/search")
    fun getClientsByServiceName(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam serviceName: String
    ): ResponseEntity<Page<ClientResDto>> {
        val result = getClientsByServiceNameService.execute(page, size, serviceName)
        return ResponseEntity.ok(result)
    }

    @PatchMapping("/{id}/patch")
    fun updateAnyClient(
        @PathVariable id: Long,
        @RequestBody clientUpdateDto: ClientUpdateReqDto
    ): ResponseEntity<Void> {
        updateAnyClientService.execute(id, clientUpdateDto)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping
    fun deleteSeveralClient(@RequestParam(value = "ids") ids: List<Long>): ResponseEntity<Void> {
        deleteClientsService.execute(ids)
        return ResponseEntity.noContent().build()
    }

    @PatchMapping("/{id}/owner")
    fun delegateOwner(
        @PathVariable id: Long,
        @RequestParam userId: Long
    ): ResponseEntity<Void> {
        delegateOwnerService.execute(id, userId)
        return ResponseEntity.noContent().build()
    }

    @PatchMapping("/{id}/co-worker")
    fun addCoworker(
        @PathVariable id: Long,
        @Valid @RequestBody addCoworkerReqDto: AddCoworkerReqDto
    ): ResponseEntity<Void> {
        addCoworkerService.execute(id, addCoworkerReqDto)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/{id}/co-worker")
    fun deleteCoworker(
        @PathVariable id: Long,
        @Valid @RequestBody deleteCoworkerReqDto: DeleteCoworkerReqDto
    ): ResponseEntity<Void> {
        deleteCoworkerService.execute(id, deleteCoworkerReqDto)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/{id}/co-worker")
    fun getCowoker(@PathVariable id: Long): ResponseEntity<List<GetCoworkerResDto>> {
        val response = getCoworkerService.execute(id)
        return ResponseEntity.ok(response)
    }
}