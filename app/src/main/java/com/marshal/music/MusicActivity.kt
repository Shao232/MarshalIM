package com.marshal.music

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.marshal.AppRouterPath
import com.marshal.base_common.baseview.BaseViewActivity
import com.marshal.databinding.ActivityMusicBinding

/**
 * 音乐页：界面先留白，用来练 Service。
 *
 * 建议按这个顺序点，同时看 Logcat（过滤 MusicService / MusicActivity）：
 * 1. 启动服务 → Service.onCreate + onStartCommand
 * 2. 再点一次启动 → 只有 onStartCommand（不会再 onCreate）
 * 3. 绑定服务 → onBind，然后可以调 Service 的方法
 * 4. 解绑服务 → onUnbind；如果之前 start 过，服务还活着
 * 5. 停止服务 → 没有绑定的话就会 onDestroy
 */
@Route(path = AppRouterPath.APP_MUSIC_PAGE)
class MusicActivity : BaseViewActivity<ActivityMusicBinding>() {

    companion object {
        private const val TAG = "MusicActivity"
    }

    /** 绑定成功后才能拿它调 Service 的方法，没绑就是 null */
    private var musicService: MusicService? = null

    /** 已经 bind 了就不能再 bind，已经 unbind 了就不能再 unbind，否则会崩 */
    private var isBound = false

    /**
     * 绑定回调：系统连上 / 断开 Service 时通知你。
     * onServiceConnected 里的 binder，就是 Service.onBind() 返回的那个。
     */
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            val musicBinder = binder as? MusicService.MusicBinder
            musicService = musicBinder?.getService()
            isBound = true
            val status = musicService?.currentStatus().orEmpty()
            Log.d(TAG, "onServiceConnected：$status")
            updateStatus("已绑定：$status")
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            // 只有 Service 异常被系统杀掉才会走这里，自己 unbind 不会走
            musicService = null
            isBound = false
            Log.d(TAG, "onServiceDisconnected：服务异常断开")
            updateStatus("服务异常断开")
        }
    }

    override fun hasToolbar(): Boolean = true

    override fun getResLayoutBinding(): View? {
        binding = ActivityMusicBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initView() {
        if (hasIncludeToolbar) {
            setTitle("音乐")
        }
        updateStatus("空白页，用下面按钮练 Service")

        binding?.btnStartService?.setOnClickListener { startMusicService() }
        binding?.btnStopService?.setOnClickListener { stopMusicService() }
        binding?.btnBindService?.setOnClickListener { bindMusicService() }
        binding?.btnUnbindService?.setOnClickListener { unbindMusicService() }
    }

    /**
     * 启动服务：startService
     * 页面关了，服务还可以继续活（直到 stopService / stopSelf）。
     */
    private fun startMusicService() {
        val intent = Intent(this, MusicService::class.java)
        startService(intent)
        Log.d(TAG, "调用 startService")
        updateStatus("已调用 startService，看 Logcat 的 onStartCommand")
    }

    /**
     * 停止服务：stopService
     * 如果此时还有人 bind 着，服务不会马上销毁，要等全部解绑。
     */
    private fun stopMusicService() {
        val intent = Intent(this, MusicService::class.java)
        stopService(intent)
        Log.d(TAG, "调用 stopService")
        updateStatus("已调用 stopService")
    }

    /**
     * 绑定服务：bindService
     * BIND_AUTO_CREATE：还没创建的话，系统会先帮你 onCreate。
     * 绑定后才能通过 Binder 调 Service 的方法。
     */
    private fun bindMusicService() {
        if (isBound) {
            updateStatus("已经绑定过了，不用重复 bind")
            return
        }
        val intent = Intent(this, MusicService::class.java)
        bindService(intent, connection, Context.BIND_AUTO_CREATE)
        Log.d(TAG, "调用 bindService")
        updateStatus("正在 bindService…")
    }

    /**
     * 解绑服务：unbindService
     * 没 bind 就 unbind 会抛 IllegalArgumentException，所以先看 isBound。
     */
    private fun unbindMusicService() {
        if (!isBound) {
            updateStatus("当前没有绑定，不用 unbind")
            return
        }
        unbindService(connection)
        isBound = false
        musicService = null
        Log.d(TAG, "调用 unbindService")
        updateStatus("已解绑")
    }

    /**
     * 页面销毁时如果还绑着，必须解开，否则容易泄漏。
     * 这里不自动 stop：启动型服务要不要停，由「停止服务」自己决定。
     */
    override fun onDestroy() {
        if (isBound) {
            unbindService(connection)
            isBound = false
            musicService = null
        }
        super.onDestroy()
    }

    private fun updateStatus(text: String) {
        binding?.tvServiceStatus?.text = text
    }
}
