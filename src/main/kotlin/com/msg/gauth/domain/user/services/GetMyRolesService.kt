import com.msg.gauth.domain.user.utils.UserUtil
import com.msg.gauth.global.annotation.service.TransactionalService
import org.springframework.http.ResponseEntity

@TransactionalService
class GetMyRolesService(
    private val userUtil: UserUtil
) {
    fun execute(): ResponseEntity<GetMyRolesResponseDto> =
        userUtil.fetchCurrentUser()
            .roles
}
