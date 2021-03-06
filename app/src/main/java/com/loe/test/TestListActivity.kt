package com.loe.test

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.loe.mvp.ext_app.addOnItemChildClickListener
import com.loe.mvp.ext_app.addOnItemClickListener
import com.loe.mvp.ext_app.createAdapter
import com.loe.mvp.ext_java.AsyncTimer
import com.loe.mvp.ext_java.delay
import com.loe.mvp.ext_java.timer
import com.loe.mvp.ext_view.addOnClickView
import com.loe.mvp.list.BaseListActivity
import com.loe.mvp.list.QuickAdapter
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.activity_list_item.*

class TestListActivity : BaseListActivity<String>()
{
    private var page = 1

    lateinit var time: AsyncTimer

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        // 初始化列表
        init(recyclerView, refreshLayout)

        initView()

        loadData(true)
        // 主动显示refreshLayout的刷新动画
        refresh()

        time = timer(1000, 1000)
        {
            Logger.d(System.currentTimeMillis())
        }
        delay(5000)
        {
            Logger.d("" + this@TestListActivity + " : " + System.currentTimeMillis())
        }
    }

    override fun initView()
    {
        // 添加列表的头布局
        val header = TextView(this)
        header.text = "我是头部"
        adapter.addHeaderView(header)

        // 添加空白页
//        val empty = TextView(activity)
//        empty.text = "暂无数据"
//        adapter.emptyView = empty

        buttonAdd.setOnClickListener()
        {
            // 通过adapter操作列表数据
            list.remove(1)
        }

        // item点击事件
        recyclerView.addOnItemClickListener()
        {
            toast(list[it])
        }

        // item内按钮的事件
        recyclerView.addOnItemChildClickListener(R.id.button)
        {
            toast(list[it] + "按钮")
        }
    }

    /**
     * 加载列表数据
     * @param isRefresh 是否为刷新
     */
    override fun loadData(isRefresh: Boolean)
    {
        if (isRefresh) page = 1
        // 模拟网络请求延时
        recyclerView.postDelayed({
            ////////////////////////// 模拟页面数据 //////////////////////////
            val list = ArrayList<String>()
            list.forEach {  }
            when (page)
            {
                1 -> for (i in 1..15) list.add(i.toString() + Math.random().toString())
                2 -> for (i in 15..30) list.add(i.toString() + Math.random().toString())
                3 -> for (i in 30..42) list.add(i.toString() + Math.random().toString())
            }
            ////////////////////////////////////////////////////////////////
            // 加载完成后调用loadOk()结束加载，如果加载出错，调用loadError()结束加载
            loadOk(isRefresh, list)
            page++
        }, 5000)
    }

    /**
     * 实现Adapter
     * 可以通过createAdapter扩展方法简单创建
     * 如果多个页面需要用到此adapter，可单独创建一个adapter文件类
     */
    override fun getListAdapter() = createAdapter<String>(R.layout.activity_list_item)
    { holder, s ->

            // item绑定逻辑
//            holder.setText(R.id.textName, s)
//            textName.text = s

            // 注册item内按钮的事件，再用recyclerView.addOnItemChildClickListener(id)监听
//            holder.addOnClickView(button, textName)
    }
}
