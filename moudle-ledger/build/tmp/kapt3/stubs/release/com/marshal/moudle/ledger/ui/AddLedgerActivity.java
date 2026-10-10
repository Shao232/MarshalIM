package com.marshal.moudle.ledger.ui;

import java.lang.System;

/**
 * 记一笔：大金额 + 收支分段 + 九宫格分类
 */
@com.alibaba.android.arouter.facade.annotation.Route(path = "/ledger/add")
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0003J\n\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0016J\b\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0002J\b\u0010\u0010\u001a\u00020\u000fH\u0002J\b\u0010\u0011\u001a\u00020\u000fH\u0002J\b\u0010\u0012\u001a\u00020\u000fH\u0016J\u0010\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\tH\u0002R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/marshal/moudle/ledger/ui/AddLedgerActivity;", "Lcom/marshal/base_common/baseview/BaseViewActivity;", "Lcom/marshal/moudle/ledger/databinding/ActivityAddLedgerBinding;", "()V", "categoryAdapter", "Lcom/marshal/moudle/ledger/adapter/LedgerCategoryAdapter;", "currentCategory", "", "currentType", "", "getResLayoutBinding", "Landroid/view/View;", "hasToolbar", "", "initCategoryGrid", "", "initSave", "initTypeSegment", "initView", "switchType", "type", "moudle-ledger_release"})
public final class AddLedgerActivity extends com.marshal.base_common.baseview.BaseViewActivity<com.marshal.moudle.ledger.databinding.ActivityAddLedgerBinding> {
    private com.marshal.moudle.ledger.adapter.LedgerCategoryAdapter categoryAdapter;
    private java.lang.String currentCategory;
    private int currentType = 1;
    
    public AddLedgerActivity() {
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
    
    private final void initTypeSegment() {
    }
    
    private final void switchType(int type) {
    }
    
    private final void initCategoryGrid() {
    }
    
    private final void initSave() {
    }
}