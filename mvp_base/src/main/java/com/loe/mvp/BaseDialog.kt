package com.loe.mvp

import android.app.Activity
import android.app.AlertDialog
import android.view.Gravity

import android.view.ViewGroup
import android.view.WindowManager
import com.loe.mvp.ext_app.start
import com.loe.mvp.initer.BaseLoading
import com.loe.mvp.initer.BaseToast
import kotlin.reflect.KClass

/**
 * Dialog基类
 */
abstract class BaseDialog(protected val activity: Activity, private val resId: Int): BaseView
{
    protected val dialog: AlertDialog = AlertDialog.Builder(activity).create()
    private var mToast: BaseToast = BaseToast(activity)
    private var mLoading: BaseLoading = BaseLoading(activity)

    var cancelable: Boolean = true
        set(value) = dialog.setCancelable(value)

    var canceledOnTouchOutside: Boolean = true
        set(value) = dialog.setCanceledOnTouchOutside(value)

    lateinit var rootView:ViewGroup

    abstract fun ViewGroup.onView()

    @JvmOverloads
    fun show(gravity: Int = Gravity.BOTTOM)
    {
        if (!dialog.isShowing)
        {
            dialog.show()
            val window = dialog.window
            if (window != null)
            {
                window!!.setContentView(resId)
                window.setBackgroundDrawableResource(android.R.color.transparent)
                window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                window.setGravity(gravity)
                rootView = window.decorView as ViewGroup
                rootView.onView()
            }
        }
    }

    fun cancel()
    {
        dialog.cancel()
    }

    fun dismiss()
    {
        dialog.dismiss()
    }

    override val root: Activity get() = activity
    override val toast: BaseToast get() = mToast
    override val loading: BaseLoading get() = mLoading

    companion object
    {
        @JvmStatic
        fun create(activity: Activity, resId: Int, onView: ViewGroup.() -> Unit): BaseDialog
        {
            return object : BaseDialog(activity, resId)
            {
                override fun ViewGroup.onView()
                {
                    onView()
                }
            }
        }
    }
}