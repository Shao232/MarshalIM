
import android.os.Parcelable
import com.tencent.mmkv.MMKV

/**
 * for example:
 * int openApp = StoreManager.getData("openApp",0);
 * StoreManager.putData("openApp",openApp);
 * 清理缓存:
 * StoreManager.clearAll();
 */
class StoreManager private constructor() {

    companion object {
        private val mmkv: MMKV = MMKV.defaultMMKV()

        fun putData(key: String?, obj: Any?): Boolean {
            var encodeSuccess = false
            when (obj) {
                is Boolean ->{
                    encodeSuccess = mmkv.encode(key, (obj as Boolean?)?:false)
                }
                is Int -> {
                    encodeSuccess = mmkv.encode(key, (obj as Int?)?:0)
                }
                is Long -> {
                    encodeSuccess = mmkv.encode(key, (obj as Long?)?:0)
                }
                is Float -> {
                    encodeSuccess = mmkv.encode(key, (obj as Float?)?:0.0f)
                }
                is Double -> {
                    mmkv.encode(key, (obj as Double?)?:0.0)
                }
                is String -> {
                    encodeSuccess = mmkv.encode(key, obj as String?)
                }
                is Parcelable -> {
                    encodeSuccess = mmkv.encode(key, obj as Parcelable?)
                }
                else -> {
                    encodeSuccess = mmkv.encode(key, obj.toString())
                }
            }
            return encodeSuccess
        }

        fun getData(key: String?, defaultValue: Boolean): Boolean {
            return mmkv.decodeBool(key, defaultValue)
        }

        fun getData(key: String?, defaultValue: Int): Int {
            return mmkv.decodeInt(key, defaultValue)
        }

        fun getData(key: String?, defaultValue: Long): Long {
            return mmkv.decodeLong(key, defaultValue)
        }

        fun getData(key: String?, defaultValue: Float): Float {
            return mmkv.decodeFloat(key, defaultValue)
        }

        fun getData(key: String?, defaultValue: Double): Double {
            return mmkv.decodeDouble(key, defaultValue)
        }

        fun getData(key: String?, defaultValue: String?): String? {
            return mmkv.decodeString(key, defaultValue)
        }

        fun <T : Parcelable?> getData(key: String?, defaultValue: Class<T>?): T? {
            return mmkv.decodeParcelable(key, defaultValue)
        }

        fun cleanAll() {
            mmkv.clearAll()
        }

        fun removeValue(keyString: String) {
            mmkv.removeValueForKey(keyString)
        }
    }
}