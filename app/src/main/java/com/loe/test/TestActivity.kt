package com.loe.test

import android.os.Bundle
import android.os.Handler
import android.support.annotation.Keep
import android.view.View.GONE
import com.loe.mvp.*
import com.loe.mvp.mvp.BaseMvpActivity
import kotlinx.android.synthetic.main.activity_model.*

class TestActivity : BaseMvpActivity<TestPresenter, TestModel>()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_model)

        buttonGo.setOnClickListener()
        {
            presenter.go()
//            "goMain".invokeToString()
//            model.same()
        }

        buttonNew.setOnClickListener()
        {
            start(TestListActivity::class)
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
        buttonNew.visibility = GONE
        toast("11111main哒哒哒哒哒哒")
        return "傻了吧唧"
    }
}

class TestPresenter : BaseModelPresenter<TestModel>()
{
    override fun onInit()
    {
        model.same()
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

        view.run()
        {
            finish()
        }
    }
}