package com.loe.mvp

import android.app.Activity
import com.loe.mvp.ext_app.start
import com.loe.mvp.initer.BaseLoading
import com.loe.mvp.initer.BaseToast
import kotlin.reflect.KClass

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

    fun start(cls: Class<out Activity>, delay: Long = 0) = root.start(cls, delay)

    fun start(kCls: KClass<out Activity>, delay: Long = 0) = root.start(kCls, delay)

    fun start(clsName: String, delay: Long = 0) = root.start(clsName, delay)

    fun BaseView.finish() = root.finish()

    fun BaseView.overridePendingTransition(enterAnim: Int, exitAnim: Int) = root.overridePendingTransition(enterAnim, exitAnim)

    /**
     * 定义常用方法
     */
    fun initView(){}

    fun initEvent(){}

    fun initData(){}

    fun loadData(){}

    /**
     * 定义List方法
     */
    fun loadError(isRefresh: Boolean){}

    fun setRefreshing(refreshing: Boolean){}

    fun loadData(isRefresh: Boolean){}

    /********************** invoke *********************/
    fun invokeName(name : String, vararg params: Any): Any?
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

    fun invokeNameSupper(name : String, vararg params: Any): Any?
    {
        this::class.java.methods.forEach()
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

    /**
     * "方法名".invoke([参数1,参数2...])
     */
    fun String.invoke(vararg params: Any): Any? = invokeName(this, *params)

    fun String.invokeSupper(vararg params: Any): Any? = invokeNameSupper(this, *params)

    fun <T> String.invokeTo(vararg params: Any): T = invokeName(this, *params) as T

    fun String.invokeToString(vararg params: Any): String = invokeName(this, *params) as String

    fun String.invokeToInt(vararg params: Any): Int = invokeName(this, *params) as Int

    fun String.invokeToDouble(vararg params: Any): Double = invokeName(this, *params) as Double

    fun String.invokeToFloat(vararg params: Any): Float = invokeName(this, *params) as Float

    fun String.invokeToBoolean(vararg params: Any): Boolean = invokeName(this, *params) as Boolean
}