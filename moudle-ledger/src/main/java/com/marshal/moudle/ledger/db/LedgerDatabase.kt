package com.marshal.moudle.ledger.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LedgerBean::class], version = 1, exportSchema = false)
abstract class LedgerDatabase : RoomDatabase() {

    abstract fun ledgerDao(): LedgerDao
}
