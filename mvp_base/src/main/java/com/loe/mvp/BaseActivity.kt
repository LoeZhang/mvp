package com.loe.mvp

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import com.loe.mvp.initer.BaseIniter
import com.loe.mvp.initer.BaseLoading
import com.loe.mvp.initer.BaseToast

open class BaseActivity : AppCompatActivity(), BaseView
{
    lateinit var activity: Activity
    private lateinit var mToast: BaseToast
    private lateinit var mLoading: BaseLoading

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        activity = this
        mToast = BaseToast(activity)
        mLoading = BaseLoading(activity)

        BaseIniter.onBaseView?.onCreate(this)
    }

    override val root: Activity get() = activity
    override val toast: BaseToast get() = mToast
    override val loading: BaseLoading get() = mLoading

    override fun onResume()
    {
        super.onResume()
        BaseIniter.onBaseView?.onResume(this)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean
    {
        if (ev != null) BaseIniter.onBaseView?.onTouchEvent(ev)
        return super.dispatchTouchEvent(ev)
    }

    override fun onDestroy()
    {
        super.onDestroy()
        BaseIniter.onBaseView?.onDestroy(this)
    }
}