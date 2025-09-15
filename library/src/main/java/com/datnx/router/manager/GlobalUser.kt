import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class GlobalUser private constructor() {

    private lateinit var prefs: SharedPreferences

    companion object {
        @Volatile
        private var INSTANCE: GlobalUser? = null

        fun init(context: Context) {
            if (INSTANCE == null) {
                synchronized(this) {
                    if (INSTANCE == null) {
                        INSTANCE = GlobalUser().apply {
                            prefs = context.applicationContext.getSharedPreferences(
                                "my_auth_prefs",
                                Context.MODE_PRIVATE
                            )
                        }
                    }
                }
            }
        }

        fun getInstance(): GlobalUser {
            return INSTANCE
                ?: throw IllegalStateException("GlobalUser chưa được khởi tạo. Hãy gọi init(context) trước.")
        }
    }

    fun remove(key: String) {
        prefs.edit { remove(key) }
    }

    fun clear() {
        prefs.edit { clear() }
    }

    fun saveData(value: String) {
        prefs.edit { putString("email", value) }
    }

    fun getData(): String {
        return prefs.getString("email", "") ?: ""
    }
}