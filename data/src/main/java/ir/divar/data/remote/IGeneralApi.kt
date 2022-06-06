package ir.divar.data.remote

import ir.divar.data.util.Constants.BAD_REQUEST_ERROR_MESSAGE
import ir.divar.data.util.Constants.NO_INTERNET_ERROR_MESSAGE
import ir.divar.domain.remote.BaseResult
import retrofit2.HttpException
import java.net.UnknownHostException

interface GeneralApi

inline fun <T : GeneralApi, R, P : Any> T.safeApiCall(
    method: T.() -> R
): BaseResult<P, String> where R : BaseResult<P, String> = try {
    method()
} catch (e: UnknownHostException) {
    e.printStackTrace()
    BaseResult.Error(NO_INTERNET_ERROR_MESSAGE)
} catch (e: HttpException) {
    BaseResult.Error(BAD_REQUEST_ERROR_MESSAGE)
}