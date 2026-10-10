package com.marshal.moudle.ledger.adapter;

import java.lang.System;

/**
 * 九宫格分类选择
 */
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0019\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\u0002\u0010\u0007J\u0018\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\tH\u0016J\u0018\u0010\r\u001a\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\tH\u0016J\b\u0010\u0011\u001a\u0004\u0018\u00010\u0003R\u001a\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2 = {"Lcom/marshal/moudle/ledger/adapter/LedgerCategoryAdapter;", "Lcom/marshal/base_common/baseadapter/BaseRecyclerAdapter;", "Lcom/marshal/moudle/ledger/adapter/LedgerCategoryViewHolder;", "", "onSelect", "Lkotlin/Function1;", "", "(Lkotlin/jvm/functions/Function1;)V", "selectedPosition", "", "bindViewHolderData", "holder", "position", "onViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "selectedItem", "moudle-ledger_release"})
public final class LedgerCategoryAdapter extends com.marshal.base_common.baseadapter.BaseRecyclerAdapter<com.marshal.moudle.ledger.adapter.LedgerCategoryViewHolder, java.lang.String> {
    private final kotlin.jvm.functions.Function1<java.lang.String, kotlin.Unit> onSelect = null;
    private int selectedPosition = 0;
    
    public LedgerCategoryAdapter(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onSelect) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    @java.lang.Override
    public com.marshal.moudle.ledger.adapter.LedgerCategoryViewHolder onViewHolder(@org.jetbrains.annotations.NotNull
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override
    public void bindViewHolderData(@org.jetbrains.annotations.NotNull
    com.marshal.moudle.ledger.adapter.LedgerCategoryViewHolder holder, int position) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String selectedItem() {
        return null;
    }
}