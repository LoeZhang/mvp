package com.loe.mvp.mvp

import android.os.Bundle
import android.util.Log
import com.loe.mvp.BaseFragment
import com.loe.mvp.BaseModel
import com.loe.mvp.BaseModelPresenter
import java.lang.Exception
import java.lang.reflect.ParameterizedType

abstract class BaseMvpFragment<PRESENTER : BaseModelPresenter<MODEL>, MODEL: BaseModel> : BaseFragment()
{
    lateinit var presenter: PRESENTER
        private set

    lateinit var model: MODEL
        private set

    override fun onCreate(savedInstanceState: Bundle?)
    {
        try
        {
            val presenterClass = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<PRESENTER>
            val modelClass = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<MODEL>
            presenter = presenterClass.newInstance()
            model = modelClass.newInstance()
            presenter.init(this, model)
        } catch (e: Exception)
        {
            Log.e("ModelPresenterRuntime","Fragment需传入Presenter和Model泛型")
        }

        super.onCreate(savedInstanceState)
    }
}