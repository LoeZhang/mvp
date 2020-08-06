package com.loe.test

import android.os.Bundle
import androidx.annotation.Keep
import com.loe.mvp.BasePresenter
import com.loe.mvp.mvp.BaseMvpActivity
import com.loe.mvp.start
import kotlinx.android.synthetic.main.activity_model.*

class TestPresenter : BasePresenter()
{
    fun go()
    {
        toast(invoke("goHome", 233, "顺丰到付都是") as String)
    }
}

class TestActivity : BaseMvpActivity<TestPresenter>()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_model)

        buttonGo.setOnClickListener()
        {
            presenter.go()
        }

        buttonNew.setOnClickListener()
        {
            start(TestListActivity::class)
        }
    }

    /**
     * Presenter需要反射调用的方法，@Keep保持不被混淆
     */
    @Keep
    fun goHome(n: Int, s: String): String
    {
        buttonGo.text = "哈哈哈$s~  gohome！！$n"
        return "我是返回"
    }
}
