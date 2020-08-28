package com.loe.mvp.list

import android.support.annotation.LayoutRes
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

abstract class QuickAdapter<BEAN>(@LayoutRes layoutResId: Int, list: List<BEAN> = ArrayList())
    : BaseQuickAdapter<BEAN, BaseViewHolder>(layoutResId, list)
{
    override fun convert(holder: BaseViewHolder, bean: BEAN) = holder.itemView.convert(holder, bean)

    abstract fun View.convert(holder: BaseViewHolder, bean: BEAN)
}