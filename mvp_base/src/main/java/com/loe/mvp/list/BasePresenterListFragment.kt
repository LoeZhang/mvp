package com.loe.mvp.list

import android.support.annotation.Keep
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.loe.mvp.BasePresenter
import com.loe.mvp.mvp.BasePresenterFragment

abstract class BasePresenterListFragment<PRESENTER : BasePresenter, BEAN> : BasePresenterFragment<PRESENTER>()
{
    var listController = object: ListController<BEAN>()
    {
        override fun loadData(isRefresh: Boolean) = this@BasePresenterListFragment.loadData(isRefresh)

        override fun getListAdapter() = this@BasePresenterListFragment.getListAdapter()
    }

    fun init(recyclerView: RecyclerView?, refreshLayout: SwipeRefreshLayout? = null, isEnableLoad: Boolean = true)
    {
        listController.init(activity!!, recyclerView, refreshLayout, isEnableLoad)
    }

    protected val adapter get() = listController.adapter

    /**
     * 主动显示refreshLayout的刷新动画
     */
    protected fun refresh() = listController.refresh()

    /**
     * 加载完成调用
     */
    fun loadOk(isRefresh: Boolean, aList: List<BEAN>) = listController.loadOk(isRefresh, aList)

    /**
     * 加载失败调用
     */
    @Keep
    fun loadError(isRefresh: Boolean) = listController.loadError(isRefresh)

    /**
     * 设置refreshLayout刷新状态
     */
    @Keep
    fun setRefreshing(refreshing: Boolean) = listController.setRefreshing(refreshing)

    /**
     * 加载列表数据
     * @param isRefresh 是否为刷新
     */
    @Keep
    abstract fun loadData(isRefresh: Boolean)

    /**
     * 获取列表Adapter
     */
    protected abstract fun getListAdapter(): BaseQuickAdapter<BEAN, *>
}