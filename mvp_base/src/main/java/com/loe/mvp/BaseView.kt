package com.loe.mvp

import android.app.Activity
import com.loe.mvp.initer.BaseLoading
import com.loe.mvp.initer.BaseToast

interface BaseView
{
    val root: Activity
    val toast: BaseToast
    val loading: BaseLoading

    fun runOnUi(run: () -> Unit) = root.runOnUiThread(run)

    fun toast(msg: CharSequence?)= toast.show(msg ?: "")

    fun toast(o: Any?) = toast.show("" + o)

    fun showLoading(msg: CharSequence = "") = loading.show(msg)

    fun cancelLoading() = loading.cancel()

    fun invoke(name: String, vararg params: Any): Any?
    {
        this::class.java.declaredMethods.forEach()
        {
            if (it.name == name)
            {
                try
                {
                    it.isAccessible = true
                    return it.invoke(this, *params)
                } catch (e: Exception)
                {
                }
            }
        }
        return null
    }
}