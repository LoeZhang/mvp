package com.loe.mvp

import android.app.Activity
import android.support.design.widget.BottomSheetDialog
import android.view.View
import android.view.ViewGroup
import com.loe.mvp.initer.BaseLoading
import com.loe.mvp.initer.BaseToast

/**
 * 底部滑动Dialog基类
 */
abstract class BaseBottomDialog(protected val activity: Activity, private val resId: Int): BaseView
{
    protected val dialog: BottomSheetDialog = BottomSheetDialog(activity)
    private var mToast: BaseToast = BaseToast(activity)
    private var mLoading: BaseLoading = BaseLoading(activity)

    var cancelable: Boolean = true
        set(value) = dialog.setCancelable(value)

    var canceledOnTouchOutside: Boolean = true
        set(value) = dialog.setCanceledOnTouchOutside(value)

    lateinit var rootView:ViewGroup
    private var isInit = false

    abstract fun ViewGroup.onView()
    fun ViewGroup.onShow(){}

    fun show()
    {
        if (!dialog.isShowing)
        {
            if(!isInit)
            {
                dialog.setContentView(resId)
                dialog.window.setBackgroundDrawableResource(android.R.color.transparent)
                dialog.delegate.findViewById<View>(android.support.design.R.id.design_bottom_sheet)
                    ?.setBackgroundResource(android.R.color.transparent)
                rootView = dialog.window.decorView as ViewGroup
                rootView.onView()
            }

            dialog.show()
            rootView.onShow()
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
        fun create(activity: Activity, resId: Int, onView: ViewGroup.() -> Unit): BaseBottomDialog
        {
            return object : BaseBottomDialog(activity, resId)
            {
                override fun ViewGroup.onView()
                {
                    onView()
                }
            }
        }
    }
}