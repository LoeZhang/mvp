package com.loe.mvp

open class BasePresenter
{
    lateinit var view: BaseView

    constructor()

    constructor(view: BaseView)
    {
        init(view)
    }

    internal fun init(view: BaseView)
    {
        this.view = view
        onInit()
    }

    open fun onInit()
    {
    }

    /********************** 实现View的方法 *********************/

    fun runOnUi(run: () -> Unit) = view.runOnUi(run)

    fun toast(msg: CharSequence?)= view.toast(msg)

    fun toast(o: Any?)= view.toast(o)

    fun showLoading(msg: CharSequence = "") = view.showLoading(msg)

    fun cancelLoading() = view.cancelLoading()

    /**
     * 执行View的方法
     * "方法名".invoke([参数1,参数2...])
     */
    fun String.invoke(vararg params: Any): Any? = view.invoke(this, *params)

    fun <T> String.invokeTo(vararg params: Any): T = view.invoke(this, *params) as T

    fun String.invokeToString(vararg params: Any): String = view.invoke(this, *params) as String

    fun String.invokeToInt(vararg params: Any): Int = view.invoke(this, *params) as Int

    fun String.invokeToDouble(vararg params: Any): Double = view.invoke(this, *params) as Double

    fun String.invokeToFloat(vararg params: Any): Float = view.invoke(this, *params) as Float

    fun String.invokeToBoolean(vararg params: Any): Boolean = view.invoke(this, *params) as Boolean
}