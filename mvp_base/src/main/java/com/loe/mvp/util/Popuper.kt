package com.loe.mvp.util

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView

class Popuper
@JvmOverloads
constructor(
        context: Context,
        layoutResId: Int,
        width: Int = WRAP_CONTENT,
        height: Int = MATCH_PARENT
)
{
    companion object
    {
        const val WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT
        const val MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT
    }

    private val popupWindow: PopupWindow
    private val root: ViewGroup =
        LayoutInflater.from(context).inflate(layoutResId, null) as ViewGroup

    private var canShow = true

    val isShowing: Boolean get() = popupWindow.isShowing

    init
    {
        popupWindow = PopupWindow(root, width, height)
        popupWindow.isFocusable = true
        // 设置背景
        popupWindow.setBackgroundDrawable(ColorDrawable())
        // 外部点击事件
        popupWindow.isOutsideTouchable = true

        popupWindow.setOnDismissListener {
            canShow = false
            object : Thread()
            {
                override fun run()
                {
                    try
                    {
                        sleep(100)
                    } catch (e: InterruptedException)
                    {
                    }

                    canShow = true
                }
            }.start()
        }

        root.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && popupWindow.isShowing)
            {
                popupWindow.dismiss()
                return@OnKeyListener true
            }
            false
        })
    }

    fun show(anchor: View)
    {
        if (canShow)
        {
            popupWindow.showAsDropDown(anchor)
        }
    }

    fun show(anchor: View, xOff: Int, yOff: Int)
    {
        if (canShow)
        {
            popupWindow.showAsDropDown(anchor, xOff, yOff)
        }
    }

    fun show(anchor: View, xOff: Int, yOff: Int, gravity: Int)
    {
        if (canShow)
        {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            {
                popupWindow.showAsDropDown(anchor, xOff, yOff, gravity)
            }else
            {
                popupWindow.showAsDropDown(anchor, xOff, yOff)
            }
        }
    }

    fun showAtLocation(parent: View, gravity: Int, x: Int, y: Int)
    {
        if (canShow)
        {
            popupWindow.showAtLocation(parent, gravity, x, y)
        }
    }

    fun setOutsideTouchable(touchable: Boolean)
    {
        popupWindow.isOutsideTouchable = touchable
    }

    fun getView(resId: Int): View
    {
        return root.findViewById(resId)
    }

    fun getTextView(resId: Int): TextView
    {
        return root.findViewById(resId)
    }

    fun isVisible(resId: Int): Boolean
    {
        return root.findViewById<View>(resId).visibility == View.VISIBLE
    }

    fun setVisible(resId: Int, visible: Boolean)
    {
        root.findViewById<View>(resId).visibility = if (visible) View.VISIBLE else View.GONE
    }

    fun dismiss()
    {
        popupWindow.dismiss()
    }
}