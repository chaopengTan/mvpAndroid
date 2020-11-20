package com.example.myapplication

import android.widget.TextView
import com.base.mvp.abs.MVPActivityImpl
import com.example.myapplication.contorl.TestContorl
import com.example.myapplication.contorl.TestPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : MVPActivityImpl<TestPresenter,TestContorl.View>() ,TestContorl.View{
    override fun showResults(results: String) {
        tv_results.text = results
    }

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun createPresenter(): TestPresenter {
       return TestPresenter()
    }

    override fun initView() {
        findViewById<TextView>(R.id.tv_start_test).setOnClickListener {
            mPresenter!!.login("15116416759","1234")
        }
    }

}
