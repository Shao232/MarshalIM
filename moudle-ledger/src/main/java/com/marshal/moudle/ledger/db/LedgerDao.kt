package com.marshal.moudle.ledger.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LedgerDao {

    @Insert
    fun insert(bean: LedgerBean): Long

    @Delete
    fun delete(bean: LedgerBean)

    @Query("SELECT * FROM ledger ORDER BY createTime DESC")
    fun queryAll(): MutableList<LedgerBean>

    @Query("SELECT * FROM ledger WHERE createTime BETWEEN :start AND :end ORDER BY createTime DESC")
    fun queryByRange(start: Long, end: Long): MutableList<LedgerBean>

    @Query("SELECT IFNULL(SUM(amount),0) FROM ledger WHERE type = :type AND createTime BETWEEN :start AND :end")
    fun sumByRange(type: Int, start: Long, end: Long): Double
}
