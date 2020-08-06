package com.loe.mvp.list

import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.loe.mvp.BaseFragment

abstract class BaseListFragment<BEAN> : BaseFragment()
{
    var listController = object: ListController<BEAN>()
    {
        override fun loadData(isRefresh: Boolean) = this@BaseListFragment.loadData(isRefresh)

        override fun getListAdapter() = this@BaseListFragment.getListAdapter()
    }

    fun init(recyclerView: RecyclerView?, refreshLayout: SwipeRefreshLayout? = null, isEnableLoad: Boolean = true)
    {
        listController.init(activity, recyclerView, refreshLayout, isEnableLoad)
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
    fun loadError(isRefresh: Boolean) = listController.loadError(isRefresh)

    /**
     * 设置refreshLayout刷新状态
     */
    fun setRefreshing(refreshing: Boolean) = listController.setRefreshing(refreshing)

    /**
     * 加载列表数据
     * @param isRefresh 是否为刷新
     */
    abstract fun loadData(isRefresh: Boolean)

    /**
     * 获取列表Adapter
     */
    protected abstract fun getListAdapter(): BaseQuickAdapter<BEAN, *>
}