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
    private const val USER_LOCATION_PREF = "user_location_pref"
    private const val USER_LOCATION_PREF_KEY = "user_location_pref_key"
    private const val PERMISSION_DENIED_KEY = "permission_denied_key"
    private var userLocationPreference: SharedPreferences by Delegates.notNull()

    // END of region of properties

    /**
     * @param ctx should be application context to prevent memory leak.
     *
     * @return [UserLocationSharedPreferences]
     */
    fun initialWith(ctx: Context): UserLocationSharedPreferences {
        userLocationPreference = ctx.getSharedPreferences(USER_LOCATION_PREF, Context.MODE_PRIVATE)
        return UserLocationSharedPreferences
    }

    /** Saves the last location that API is called by it. */
    fun saveLocation(latLng: LatLng) {
        userLocationPreference.edit {
            putString(USER_LOCATION_PREF_KEY, latLng.toLocalString())
        }
    }

    /** @return the last location that API is called by it. */
    fun getLastLocation() =
        userLocationPreference.getString(USER_LOCATION_PREF_KEY, null)?.toLatLang()

    /**
     * @return `true` if Location permission request has been denied at least once, `false` otherwise.
     */
    fun isLocationPermissionDeniedBefore() =
        userLocationPreference.getBoolean(PERMISSION_DENIED_KEY, false)

    /**
     * sets PERMISSION_DENIED_KEY `true` when user denies Location permission request for the first time.
     */
    fun setLocationPermissionDenied() {
        userLocationPreference.edit {
            putBoolean(PERMISSION_DENIED_KEY, true)
        }
    }
}