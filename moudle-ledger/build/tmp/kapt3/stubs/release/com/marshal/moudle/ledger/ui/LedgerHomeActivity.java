package com.marshal.moudle.ledger.ui;

import java.lang.System;

/**
 * 记账首页：本月汇总卡 + 预算进度 + 按天分组流水
 */
@com.alibaba.android.arouter.facade.annotation.Route(path = "/ledger/home")
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u0000 !2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001!B\u0005\u00a2\u0006\u0002\u0010\u0003J&\u0010\u0006\u001a\u0012\u0012\u0004\u0012\u00020\b0\u0007j\b\u0012\u0004\u0012\u00020\b`\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u0002J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000eH\u0002J\u0018\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0013H\u0002J\n\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\b\u0010\u0019\u001a\u00020\u0011H\u0016J\b\u0010\u001a\u001a\u00020\u0011H\u0002J\"\u0010\u001b\u001a\u00020\u00112\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001d2\b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0014R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\""}, d2 = {"Lcom/marshal/moudle/ledger/ui/LedgerHomeActivity;", "Lcom/marshal/base_common/baseview/BaseViewActivity;", "Lcom/marshal/moudle/ledger/databinding/ActivityLedgerHomeBinding;", "()V", "adapter", "Lcom/marshal/moudle/ledger/adapter/LedgerListAdapter;", "buildItems", "Ljava/util/ArrayList;", "Lcom/marshal/moudle/ledger/pojo/LedgerListItem;", "Lkotlin/collections/ArrayList;", "list", "", "Lcom/marshal/moudle/ledger/db/LedgerBean;", "dayStart", "", "time", "fillSummary", "", "expense", "", "income", "getResLayoutBinding", "Landroid/view/View;", "hasToolbar", "", "initView", "loadData", "onActivityResult", "requestCode", "", "resultCode", "data", "Landroid/content/Intent;", "Companion", "moudle-ledger_release"})
public final class LedgerHomeActivity extends com.marshal.base_common.baseview.BaseViewActivity<com.marshal.moudle.ledger.databinding.ActivityLedgerHomeBinding> {
    private com.marshal.moudle.ledger.adapter.LedgerListAdapter adapter;
    @org.jetbrains.annotations.NotNull
    public static final com.marshal.moudle.ledger.ui.LedgerHomeActivity.Companion Companion = null;
    public static final int REQUEST_ADD = 1001;
    
    public LedgerHomeActivity() {
        super();
    }
    
    @java.lang.Override
    public boolean hasToolbar() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable
    @java.lang.Override
    public android.view.View getResLayoutBinding() {
        return null;
    }
    
    @java.lang.Override
    public void initView() {
    }
    
    @java.lang.Override
    protected void onActivityResult(int requestCode, int resultCode, @org.jetbrains.annotations.Nullable
    android.content.Intent data) {
    }
    
    private final void loadData() {
    }
    
    private final void fillSummary(double expense, double income) {
    }
    
    private final java.util.ArrayList<com.marshal.moudle.ledger.pojo.LedgerListItem> buildItems(java.util.List<com.marshal.moudle.ledger.db.LedgerBean> list) {
        return null;
    }
    
    private final long dayStart(long time) {
        return 0L;
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/marshal/moudle/ledger/ui/LedgerHomeActivity$Companion;", "", "()V", "REQUEST_ADD", "", "moudle-ledger_release"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}