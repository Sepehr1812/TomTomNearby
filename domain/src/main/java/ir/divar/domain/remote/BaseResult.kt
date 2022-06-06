package ir.divar.domain.remote


/**
 * Represents Success or Error states of API responses.
 *
 * @author Sepi 6/6/22
 */
sealed class BaseResult<out T : Any, out U : Any> {
    data class Success<T : Any>(val data: T?) : BaseResult<T, Nothing>()
    data class Error<U : Any>(val message: U?) : BaseResult<Nothing, U>()
}