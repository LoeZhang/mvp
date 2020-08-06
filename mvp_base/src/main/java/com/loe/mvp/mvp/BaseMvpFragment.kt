package com.loe.mvp.mvp

import android.os.Bundle
import android.util.Log
import com.loe.mvp.BaseFragment
import com.loe.mvp.BasePresenter
import java.lang.reflect.ParameterizedType

abstract class BaseMvpFragment<PRESENTER : BasePresenter> : BaseFragment()
{
    lateinit var presenter: PRESENTER
        private set

    override fun onCreate(savedInstanceState: Bundle?)
    {
        try
        {
            val presenterClass = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<PRESENTER>
            presenter = presenterClass.newInstance()
            presenter.init(this)
        } catch (e: Exception)
        {
            Log.e("PresenterRuntime","Fragment需传入BasePresenter泛型")
        }

        super.onCreate(savedInstanceState)
    }
}