package com.safframework.eventbus.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.safframework.eventbus.R
import com.safframework.eventbus.fragment.Fragment1
import com.safframework.eventbus.fragment.Fragment2

/**
 *
 * @FileName:
 *          com.safframework.eventbus.activity.TestEventBusActivity
 * @author: Tony Shen
 * @date: 2019-08-26 20:10
 * @version: V1.0 <描述当前版本功能>
 */
class TestEventBusActivity: AppCompatActivity() {

    private lateinit var fragment1: Fragment1
    private lateinit var fragment2: Fragment2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_event_bus)

        initViews()
        registerEvents()
    }

    private fun initViews() {

        fragment1 = Fragment1()
        fragment2 = Fragment2()

        val mTransactiont = supportFragmentManager.beginTransaction()
        mTransactiont.replace(R.id.layout1, fragment1, fragment1::class.java.name)
        mTransactiont.replace(R.id.layout2, fragment2, fragment2::class.java.name)
        mTransactiont.commit()
    }

    private fun registerEvents() {
    }
}