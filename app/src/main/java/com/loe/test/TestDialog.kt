package com.loe.test

import android.app.Activity
import android.view.ViewGroup
import com.loe.mvp.BaseBottomDialog
import com.loe.mvp.BaseDialog
import com.loe.mvp.ext_view.setDelayClickListener
import kotlinx.android.synthetic.main.dialog_test.view.*

class TestDialog(activity: Activity) : BaseBottomDialog(activity, R.layout.dialog_test)
{
    override fun ViewGroup.onView()
    {
        buttonGo.setDelayClickListener()
        {
            toast("甲磺酸")
        }

        buttonNew.setDelayClickListener()
        {
            showLoading()
            dismiss()
        }

        dialog.setOnCancelListener { dismiss() }
    }
}