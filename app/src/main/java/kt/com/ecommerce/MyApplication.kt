package kt.com.ecommerce

import android.app.Application
import android.content.Context
import com.hazz.kotlinmvp.utils.DisplayManager
import kotlin.properties.Delegates

/**
 * 修改番号 INLS-NEW-201811-002 修改简介 wuy 2018/11/30 ADD
 */
class MyApplication : Application() {

    companion object {
        private val TAG = "MyApplication"

        var context: Context by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        DisplayManager.init(this)
        context = applicationContext
    }
}