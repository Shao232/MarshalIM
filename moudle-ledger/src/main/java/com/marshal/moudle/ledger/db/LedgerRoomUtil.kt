package com.marshal.moudle.ledger.db

import androidx.room.Room
import com.marshal.base_common.MApplication

/**
 * 记账库，模块内私有，不跟宿主 App 的主库混在一起
 */
object LedgerRoomUtil {

    private val db = Room.databaseBuilder(
        MApplication.getInstance().applicationContext,
        LedgerDatabase::class.java,
        "ledger.db"
    ).fallbackToDestructiveMigration().build()

    fun getLedgerDao(): LedgerDao {
        return db.ledgerDao()
    }
}
