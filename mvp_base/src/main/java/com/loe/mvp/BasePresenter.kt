package com.loe.mvp

open class BasePresenter
{
    lateinit var view: BaseView

    fun init(view: BaseView)
    {
        this.view = view
    }

    /********************** 实现View的方法 *********************/

    fun runOnUi(run: () -> Unit) = view.runOnUi(run)

    fun toast(msg: CharSequence?)= view.toast(msg)

    fun showLoading(msg: CharSequence = "") = view.showLoading(msg)

    fun cancelLoading() = view.cancelLoading()

    fun invoke(name: String, vararg params: Any): Any? = view.invoke(name, *params)
}