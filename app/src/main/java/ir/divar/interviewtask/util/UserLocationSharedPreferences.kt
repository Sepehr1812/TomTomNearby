package ir.divar.interviewtask.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.android.gms.maps.model.LatLng
import kotlin.properties.Delegates


/**
 * This class will be used for saving the last location of user which the last API request called within it.
 *
 * @author Sepi 6/8/22
 */
object UserLocationSharedPreferences {

    // region of properties
    private const val userLocationPref = "user_location_pref"
    private const val userLocationPrefKey = "user_location_pref_key"
    private var userLocationPreference: SharedPreferences by Delegates.notNull()

    // END of region of properties

    /**
     * @param ctx should be application context to prevent memory leak.
     *
     * @return [UserLocationSharedPreferences]
     */
    fun initialWith(ctx: Context): UserLocationSharedPreferences {
        userLocationPreference = ctx.getSharedPreferences(userLocationPref, Context.MODE_PRIVATE)
        return UserLocationSharedPreferences
    }

    fun saveLocation(latLng: LatLng) {
        userLocationPreference.edit {
            putString(userLocationPrefKey, latLng.toLocalString())
        }
    }

    fun getLastLocation() = userLocationPreference.getString(userLocationPrefKey, null)?.toLatLang()
}