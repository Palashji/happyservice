package com.bluewebspark.happyservice.adapter.base;

import android.view.View;

public class BaseViewHolder<V> extends BaseClickListenerViewHolder<V> {

    @SuppressWarnings("unchecked")
    public BaseViewHolder(BaseRecyclerViewAdapter adapter, View view) {
        super(adapter, adapter.onRecycleItemClickListener, view);
    }
}