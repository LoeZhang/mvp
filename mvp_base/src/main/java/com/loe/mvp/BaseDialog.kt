package com.loe.mvp

import android.app.AlertDialog
import android.content.Context

import android.view.ViewGroup
import android.view.WindowManager

/**
 * Dialog基类
 */
abstract class BaseDialog(context: Context, private val resId: Int)
{
    private val dialog: AlertDialog = AlertDialog.Builder(context).create()

    var cancelable: Boolean = true
        set(value) = dialog.setCancelable(value)

    var canceledOnTouchOutside: Boolean = true
        set(value) = dialog.setCanceledOnTouchOutside(value)

    abstract fun onView(root: ViewGroup)

    fun show()
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

                val root = window.decorView as ViewGroup
                onView(root)
            }
        }
    }

    fun cancel()
    {
        dialog.cancel()
    }

    companion object
    {
        @JvmStatic
        fun create(context: Context, resId: Int, onView: (root: ViewGroup) -> Unit): BaseDialog
        {
            return object : BaseDialog(context, resId)
            {
                override fun onView(root: ViewGroup)
                {
                    onView(root)
                }
            }
        }
    }
}
