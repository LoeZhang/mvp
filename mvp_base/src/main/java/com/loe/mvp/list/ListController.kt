package com.loe.mvp.list

import android.app.Activity
import android.content.res.Resources
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.widget.TextView
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.loe.mvp.R

/**
 * 下拉刷新&上拉加载 列表控制类
 *
 * @author zls
 * @since 2020/7/3-9:30
 */
abstract class ListController<BEAN> : SwipeRefreshLayout.OnRefreshListener,
    BaseQuickAdapter.RequestLoadMoreListener
{
    private lateinit var activity: Activity

    private var size: Int = 10
    lateinit var adapter: QuickAdapter<BEAN>

    private var isEnableLoad = true

    private var recyclerView: RecyclerView? = null
    private var refreshLayout: SwipeRefreshLayout? = null

    /**
     * 在activity onCreate() 执行
     */
    fun init(
        activity: Activity,
        recyclerView: RecyclerView?,
        refreshLayout: SwipeRefreshLayout? = null,
        isEnableLoad: Boolean = true
    )
    {
        this.activity = activity
        this.recyclerView = recyclerView
        this.refreshLayout = refreshLayout
        this.isEnableLoad = isEnableLoad

        size = 10

        recyclerView?.layoutManager = LinearLayoutManager(activity)
        recyclerView?.itemAnimator = DefaultItemAnimator()
        refreshLayout?.setColorSchemeColors(ContextCompat.getColor(activity, R.color.colorPrimary))
        refreshLayout?.setOnRefreshListener(this)

        adapter = getListAdapter()
        recyclerView?.adapter = adapter

        if (isEnableLoad)
        {
            adapter.setEnableLoadMore(true)
            adapter.setOnLoadMoreListener(this, recyclerView)
        }

        adapter.setHeaderFooterEmpty(true, true)

        // 空白页
        adapter.emptyView = View.inflate(activity, R.layout.layout_list_empty, null)
    }

    fun refresh()
    {
        refreshLayout?.measure(0, 0)
        refreshLayout?.isRefreshing = true
    }

    fun refreshAuto()
    {
        if(adapter.data.isEmpty())
        {
            refresh()
        }
    }

    abstract fun loadData(isRefresh: Boolean)

    /**
     * 加载完成调用
     */
    fun loadOk(isRefresh: Boolean, aList: List<BEAN>)
    {
        if (isEnableLoad)
        {
            refreshLayout?.isRefreshing = false
            if (isRefresh)
            {
                size = 10
                adapter.data.clear()
            }
            adapter.addData(aList)
            if (isRefresh)
            {
                refreshLayout?.isRefreshing = false
                adapter.setEnableLoadMore(true)
            } else
            {
                adapter.loadMoreComplete()
                refreshLayout?.isEnabled = true
            }
            if (aList.size < size)
            {
                adapter.setEnableLoadMore(false)
            } else
            {
                size = aList.size
            }
        } else
        {
            refreshLayout?.isRefreshing = false
            adapter.data.clear()
            adapter.addData(aList)
        }
    }

    /**
     * 加载失败调用
     */
    fun loadError(isRefresh: Boolean)
    {
        if (isEnableLoad)
        {
            if (isRefresh)
            {
                refreshLayout?.isRefreshing = false
            } else
            {
                adapter.loadMoreComplete()
                refreshLayout?.isEnabled = true
            }
        } else
        {
            refreshLayout?.isRefreshing = false
        }
    }

    protected abstract fun getListAdapter(): QuickAdapter<BEAN>

    fun setRefreshing(refreshing: Boolean)
    {
        refreshLayout?.isRefreshing = refreshing
    }

    /**
     * 刷新
     */
    override fun onRefresh()
    {
        if (isEnableLoad) adapter.setEnableLoadMore(false)

        recyclerView?.postDelayed({
            loadData(true)
        }, 500)
    }

    /**
     * 加载
     */
    override fun onLoadMoreRequested()
    {
        refreshLayout?.isEnabled = false

        recyclerView?.postDelayed({
            loadData(false)
        }, 400)
    }

    operator fun get(i: Int): BEAN = adapter.getItem(i)!!

    operator fun set(i: Int, bean: BEAN) = adapter.setData(i, bean)

    inline fun forEach(action: (BEAN) -> Unit) = adapter.data.forEach(action)

    inline fun forEachIndexed(action: (Int, BEAN) -> Unit) = adapter.data.forEachIndexed(action)

    fun add(bean: BEAN) = adapter.addData(bean)

    fun add(bList: List<BEAN>) = adapter.addData(bList)

    fun add(i: Int, bean: BEAN) = adapter.addData(i, bean)

    fun add(i: Int, bList: List<BEAN>) = adapter.addData(i, bList)

    fun remove(i: Int) = adapter.remove(i)

    fun notifyItemChanged(i: Int) = adapter.notifyItemChanged(i)

    fun notifyDataSetChanged() = adapter.notifyDataSetChanged()

    var data
        get() = adapter.data
        set(value) = adapter.setNewData(value)

    companion object
    {
        @JvmStatic
        fun dp_Px(dp: Int): Int
        {
            return (dp * Resources.getSystem().displayMetrics.density).toInt()
        }
    }
}