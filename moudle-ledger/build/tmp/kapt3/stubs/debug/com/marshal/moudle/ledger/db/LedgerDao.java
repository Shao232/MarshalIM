package com.marshal.moudle.ledger.db;

import java.lang.System;

@androidx.room.Dao
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010!\n\u0002\b\u0004\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\b\n\u0000\bg\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\'J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020\u0005H\'J\u000e\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\tH\'J\u001e\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\t2\u0006\u0010\u000b\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u0007H\'J \u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u000b\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u0007H\'\u00a8\u0006\u0011"}, d2 = {"Lcom/marshal/moudle/ledger/db/LedgerDao;", "", "delete", "", "bean", "Lcom/marshal/moudle/ledger/db/LedgerBean;", "insert", "", "queryAll", "", "queryByRange", "start", "end", "sumByRange", "", "type", "", "moudle-ledger_debug"})
public abstract interface LedgerDao {
    
    @androidx.room.Insert
    public abstract long insert(@org.jetbrains.annotations.NotNull
    com.marshal.moudle.ledger.db.LedgerBean bean);
    
    @androidx.room.Delete
    public abstract void delete(@org.jetbrains.annotations.NotNull
    com.marshal.moudle.ledger.db.LedgerBean bean);
    
    @org.jetbrains.annotations.NotNull
    @androidx.room.Query(value = "SELECT * FROM ledger ORDER BY createTime DESC")
    public abstract java.util.List<com.marshal.moudle.ledger.db.LedgerBean> queryAll();
    
    @org.jetbrains.annotations.NotNull
    @androidx.room.Query(value = "SELECT * FROM ledger WHERE createTime BETWEEN :start AND :end ORDER BY createTime DESC")
    public abstract java.util.List<com.marshal.moudle.ledger.db.LedgerBean> queryByRange(long start, long end);
    
    @androidx.room.Query(value = "SELECT IFNULL(SUM(amount),0) FROM ledger WHERE type = :type AND createTime BETWEEN :start AND :end")
    public abstract double sumByRange(int type, long start, long end);
}