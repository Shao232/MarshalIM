package com.marshal.music

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

/**
 * 学习用 Service：音乐页用来看「创建 / 启动 / 绑定」三条路。
 *
 * 先记住三件事：
 * 1. 类写好还不算创建成功，必须在 AndroidManifest.xml 里登记 <service>，系统才认。
 * 2. startService：适合「做完自己的事」，不需要 Activity 一直握着它。
 * 3. bindService：适合「页面要调用 Service 里的方法」，通过 Binder 互相说话。
 *
 * 生命周期（建议对着 Logcat 滤 MusicService 自己点一遍）：
 * - 第一次 start 或 bind：onCreate → onStartCommand 或 onBind
 * - 已经活着再 start：只走 onStartCommand，不再走 onCreate
 * - 已经活着再 bind：只走 onBind
 * - 既 start 又 bind 时：要 stopService 并且全部 unbind，才会 onDestroy
 */
class MusicService : Service() {

    companion object {
        private const val TAG = "MusicService"
    }

    /**
     * Binder：绑定成功后，Activity 拿到的就是它。
     * Activity 可以转成 MusicBinder，再调 getService() 拿到 Service 本身。
     */
    inner class MusicBinder : Binder() {
        fun getService(): MusicService = this@MusicService
    }

    private val musicBinder = MusicBinder()

    /**
     * 创建：Service 实例第一次被拉起来时走一次。
     * startService / bindService 都可能触发这里，但整段生命周期只来一次。
     * 适合做：初始化播放器、开线程、读配置。
     */
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate：服务被创建（只会走一次）")
    }

    /**
     * 启动：每次 startService / startForegroundService 都会进来。
     * 就算服务已经活着，再点「启动」也会再走一遍。
     *
     * 返回值告诉系统「被杀掉后要不要重启」：
     * - START_STICKY：重启，但 Intent 可能是 null
     * - START_NOT_STICKY：不自动重启
     * - START_REDELIVER_INTENT：重启并把最后一次 Intent 再送进来
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand：服务被启动 startId=$startId")
        // 启动型服务做完工作后可以自己 stopSelf()；这里留给页面上的「停止服务」按钮。
        return START_NOT_STICKY
    }

    /**
     * 绑定：bindService 时系统来要 IBinder。
     * 把 Binder 交出去后，Activity 就能调用下面的 currentStatus()。
     * 返回 null 表示不支持绑定。
     */
    override fun onBind(intent: Intent?): IBinder {
        Log.d(TAG, "onBind：有页面绑定上来了")
        return musicBinder
    }

    /**
     * 解绑：最后一个客户端 unbindService 时走这里。
     * 返回 true 的话，下次再 bind 会走 onRebind，而不是再走一次 onBind。
     */
    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG, "onUnbind：绑定断开")
        return super.onUnbind(intent)
    }

    /**
     * 销毁：没有人 start、也没有人 bind 时才会走到。
     * 这里释放播放器、停线程、关通知。
     */
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy：服务被销毁")
    }

    /** 给绑定方调用，证明 Activity 已经拿到了 Service 实例 */
    fun currentStatus(): String = "已绑定，可以直接调 Service 里的方法"
}
