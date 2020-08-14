package com.loe.test

import android.os.Bundle
import android.support.annotation.Keep
import android.view.View.GONE
import com.loe.mvp.*
import com.loe.mvp.ext_app.after
import com.loe.mvp.ext_app.setStatusBar
import com.loe.mvp.ext_app.transOut
import com.loe.mvp.ext_view.visible
import com.loe.mvp.mvp.BaseMvpActivity
import kotlinx.android.synthetic.main.activity_model.*

class TestActivity : BaseMvpActivity<TestPresenter, TestModel>()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setStatusBar()
        setContentView(R.layout.activity_model)

        buttonGo.setOnClickListener()
        {
            presenter.go()
//            "goMain".invokeToString()
//            model.same()
        }

        buttonNew.setOnClickListener()
        {

        }
    }

    override fun initView()
    {
    }

    /**
     * Presenter需要反射调用的方法，@Keep保持不被混淆
     */
    @Keep
    fun goHome(s: String): String
    {
        buttonGo.text = "哈哈哈$s~  goHome！！"
        return "我是返回"
    }

    @Keep
    fun goMain():String
    {
        buttonNew.visible = false
        toast("11111main哒哒哒哒哒哒")
        return "傻了吧唧"
    }
}

class TestPresenter : BaseModelPresenter<TestModel>()
{
    override fun onInit()
    {
//        model.same()
    }

    fun go()
    {
//        val s = "goHome".invokeToString(233, "顺丰到付都是")
//        toast(s)
//        "goMain".invoke()

        model.same()
    }
}

class TestModel : BaseModel()
{
    fun same()
    {
//        toast("goMain".invokeToString())
        "goHome".invokeToString("顺丰到付都是")
        
    }
}