package com.loe.mvp

import android.app.Activity
import kotlin.reflect.KClass

open class BaseModel
{
    lateinit var view: BaseView
        protected set

    constructor()

    constructor(view: BaseView)
    {
        init(view)
    }

    fun init(view: BaseView)
    {
        this.view = view
    }

    protected val activity: Activity get() = view.root

    /********************** 实现View的方法 *********************/

    fun runOnUi(run: () -> Unit) = view.runOnUi(run)

    fun toast(msg: CharSequence?) = view.toast(msg)

    fun toast(o: Any?) = view.toast(o)

    fun showLoading(msg: CharSequence = "") = view.showLoading(msg)

    fun cancelLoading() = view.cancelLoading()

    fun start(cls: Class<out Activity>, delay: Long = 0) = view.start(cls, delay)

    fun start(kCls: KClass<out Activity>, delay: Long = 0) = view.start(kCls, delay)

    fun finish() = view.root.finish()

    fun overridePendingTransition(enterAnim: Int, exitAnim: Int) = view.root.overridePendingTransition(enterAnim, exitAnim)

    /**
     * 实现常用方法
     */
    fun initView() = view.initView()

    fun initEvent() = view.initEvent()

    fun initData() = view.initData()

    fun loadData() = view.loadData()

    /**
     * 实现List方法
     */
    fun loadError(isRefresh: Boolean) = view.loadError(isRefresh)

    fun setRefreshing(refreshing: Boolean) = view.setRefreshing(refreshing)

    fun loadData(isRefresh: Boolean) = view.loadData(isRefresh)

    /**
     * 执行View的方法
     * "方法名".invoke([参数1,参数2...])
     */
    fun String.invoke(vararg params: Any): Any? = view.invokeName(this, *params)

    fun String.invokeSuper(vararg params: Any): Any? = view.invokeNameSupper(this, *params)

    fun <T> String.invokeTo(vararg params: Any): T = view.invokeName(this, *params) as T

    fun String.invokeToString(vararg params: Any): String = view.invokeName(this, *params) as String

    fun String.invokeToInt(vararg params: Any): Int = view.invokeName(this, *params) as Int

    fun String.invokeToDouble(vararg params: Any): Double = view.invokeName(this, *params) as Double

    fun String.invokeToFloat(vararg params: Any): Float = view.invokeName(this, *params) as Float

    fun String.invokeToBoolean(vararg params: Any): Boolean = view.invokeName(this, *params) as Boolean
}