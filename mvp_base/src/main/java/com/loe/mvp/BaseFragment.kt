package com.loe.mvp

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.loe.mvp.initer.BaseLoading
import com.loe.mvp.initer.BaseToast

abstract class BaseFragment : Fragment(), BaseView
{
    private lateinit var mToast: BaseToast
    private lateinit var mLoading: BaseLoading

    protected lateinit var inflater: LayoutInflater

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        this.inflater = inflater
        return inflater.inflate(getLayout(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        mToast = BaseToast(activity!!)
        mLoading = BaseLoading(activity!!)

        onCreated(savedInstanceState)
    }

    abstract fun getLayout(): Int
    abstract fun onCreated(savedInstanceState: Bundle?)

    override val root: Activity get() = activity!!
    override val toast: BaseToast get() = mToast
    override val loading: BaseLoading get() = mLoading
}