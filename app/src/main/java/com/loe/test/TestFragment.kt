package com.loe.test

import android.os.Bundle
import android.util.Log
import com.loe.mvp.BaseFragment
import kotlinx.android.synthetic.main.fragment_test.*

class TestFragment : BaseFragment()
{
    override fun getLayout() = R.layout.fragment_test

    override fun onCreated(savedInstanceState: Bundle?)
    {
        initEvent()
    }

    override fun initEvent()
    {
        button.setOnClickListener()
        {
            toast("dfdfg水电费")
        }
    }

    override fun onDestroy()
    {
        Log.d("Runtime","$this: TestFragment被销毁了")
        super.onDestroy()
    }
}
