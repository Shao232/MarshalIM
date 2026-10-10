package com.marshal.moudle.ledger.pojo;

import java.lang.System;

/**
 * 列表项：日期分组标题 + 流水记录两种
 */
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 !2\u00020\u0001:\u0001!B9\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u00a2\u0006\u0002\u0010\u000bJ\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\u0016\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\nH\u00c6\u0003J=\u0010\u001a\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\nH\u00c6\u0001J\u0013\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001e\u001a\u00020\u0003H\u00d6\u0001J\t\u0010\u001f\u001a\u00020 H\u00d6\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0011\u00a8\u0006\""}, d2 = {"Lcom/marshal/moudle/ledger/pojo/LedgerListItem;", "", "type", "", "bean", "Lcom/marshal/moudle/ledger/db/LedgerBean;", "date", "", "dayCount", "dayTotal", "", "(ILcom/marshal/moudle/ledger/db/LedgerBean;JID)V", "getBean", "()Lcom/marshal/moudle/ledger/db/LedgerBean;", "getDate", "()J", "getDayCount", "()I", "getDayTotal", "()D", "getType", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "toString", "", "Companion", "moudle-ledger_debug"})
public final class LedgerListItem {
    private final int type = 0;
    @org.jetbrains.annotations.Nullable
    private final com.marshal.moudle.ledger.db.LedgerBean bean = null;
    private final long date = 0L;
    private final int dayCount = 0;
    private final double dayTotal = 0.0;
    @org.jetbrains.annotations.NotNull
    public static final com.marshal.moudle.ledger.pojo.LedgerListItem.Companion Companion = null;
    public static final int TYPE_DATE = 1;
    public static final int TYPE_RECORD = 2;
    
    /**
     * 列表项：日期分组标题 + 流水记录两种
     */
    @org.jetbrains.annotations.NotNull
    public final com.marshal.moudle.ledger.pojo.LedgerListItem copy(int type, @org.jetbrains.annotations.Nullable
    com.marshal.moudle.ledger.db.LedgerBean bean, long date, int dayCount, double dayTotal) {
        return null;
    }
    
    /**
     * 列表项：日期分组标题 + 流水记录两种
     */
    @java.lang.Override
    public boolean equals(@org.jetbrains.annotations.Nullable
    java.lang.Object other) {
        return false;
    }
    
    /**
     * 列表项：日期分组标题 + 流水记录两种
     */
    @java.lang.Override
    public int hashCode() {
        return 0;
    }
    
    /**
     * 列表项：日期分组标题 + 流水记录两种
     */
    @org.jetbrains.annotations.NotNull
    @java.lang.Override
    public java.lang.String toString() {
        return null;
    }
    
    public LedgerListItem() {
        super();
    }
    
    public LedgerListItem(int type, @org.jetbrains.annotations.Nullable
    com.marshal.moudle.ledger.db.LedgerBean bean, long date, int dayCount, double dayTotal) {
        super();
    }
    
    public final int component1() {
        return 0;
    }
    
    public final int getType() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.marshal.moudle.ledger.db.LedgerBean component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.marshal.moudle.ledger.db.LedgerBean getBean() {
        return null;
    }
    
    public final long component3() {
        return 0L;
    }
    
    public final long getDate() {
        return 0L;
    }
    
    public final int component4() {
        return 0;
    }
    
    public final int getDayCount() {
        return 0;
    }
    
    public final double component5() {
        return 0.0;
    }
    
    public final double getDayTotal() {
        return 0.0;
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lcom/marshal/moudle/ledger/pojo/LedgerListItem$Companion;", "", "()V", "TYPE_DATE", "", "TYPE_RECORD", "moudle-ledger_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}