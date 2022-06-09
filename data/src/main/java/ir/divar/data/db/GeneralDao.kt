package ir.divar.data.db

import androidx.room.Dao

@Dao
interface GeneralDao

/**
 * this extension function can be reduce boilerplate code
 *
 * @param block will execute related method
 *
 * @param onError will be called whenever an exception is occurred
 */
inline fun <T : GeneralDao, R> T.executeQuery(
    block: T.() -> R,
    onError: (String?) -> Unit = {}
): R? {

    return try {
        block()
    } catch (e: Exception) {
        e.printStackTrace()
        onError(e.message)
        null
    }
}