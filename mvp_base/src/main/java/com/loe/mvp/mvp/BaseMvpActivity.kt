package com.loe.mvp.mvp

import android.os.Bundle
import android.util.Log
import com.loe.mvp.BaseActivity
import com.loe.mvp.BaseModel
import com.loe.mvp.BaseModelPresenter
import java.lang.Exception
import java.lang.reflect.ParameterizedType

open class BaseMvpActivity<PRESENTER : BaseModelPresenter<MODEL>, MODEL: BaseModel> : BaseActivity()
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
            model = modelClass.newInstance()
            model.init(this)
            presenter = presenterClass.newInstance()
            presenter.init(this, model)
        } catch (e: Exception)
        {
            Log.e("ModelPresenterRuntime","Activity需传入Presenter和Model泛型")
        }

        super.onCreate(savedInstanceState)
    }
}