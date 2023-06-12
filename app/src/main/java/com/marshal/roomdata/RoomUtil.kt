package com.marshal.roomdata

import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.marshal.MApplication

object RoomUtil {

    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("CREATE TABLE `Car` (`car_name` TEXT, 'value' REAL, 'origin' TEXT, `id` INTEGER PRIMARY KEY AUTOINCREMENT)")
        }
    }

    private val db = Room.databaseBuilder(
        MApplication.getInstance().applicationContext, AppDatabase::class.java, "database-name.db"
    ).addMigrations(MIGRATION_1_2).build()

    // 在app/build/generated/source/kapt/debug/包名/ 目录下 会生成room代码，控制sqlite框架
    private val userDao = db.userDao()

    fun getUserDao(): UserDao {
        return userDao
    }

}