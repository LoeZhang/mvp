package com.loe.mvp

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.loe.mvp.initer.BaseLoading
import com.loe.mvp.initer.BaseToast

open class BaseActivity: AppCompatActivity(), BaseView
{
    lateinit var activity: Activity
    private lateinit var mToast: BaseToast
    private lateinit var mLoading: BaseLoading

    override fun onCreate(savedInstanceState: Bundle?)
    {
        activity = this
        mToast = BaseToast(activity)
        mLoading = BaseLoading(activity)
        super.onCreate(savedInstanceState)
    }

    override val root: Activity get() = activity
    override val toast: BaseToast get() = mToast
    override val loading: BaseLoading get() = mLoading
}