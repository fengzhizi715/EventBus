package com.safframework.eventbus.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.safframework.eventbus.EventBus
import com.safframework.eventbus.R
import com.safframework.eventbus.UI
import com.safframework.eventbus.domain.CrossActivityEvent
import com.safframework.eventbus.domain.ExceptionEvent
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.text2


/**
 *
 * @FileName:
 *          com.safframework.eventbus.activity.MainActivity
 * @author: Tony Shen
 * @date: 2019-08-26 19:39
 * @version: V1.0 <描述当前版本功能>
 */
class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        text1.setOnClickListener {

            val i = Intent(this@MainActivity, TestEventBusActivity::class.java)
            startActivity(i)
        }

        text2.setOnClickListener {

            val i = Intent(this@MainActivity, TestCrossActivity::class.java)
            startActivity(i)
        }

        text3.setOnClickListener {

            val i = Intent(this@MainActivity, TestExceptionActivity::class.java)
            startActivity(i)
        }

        registerEvents()

    }

    private fun registerEvents() {

        EventBus.registerEvent(this.javaClass.simpleName, UI, CrossActivityEvent::class.java) {
            Toast.makeText(this@MainActivity, "来自MainActivity的Toast", Toast.LENGTH_SHORT).show()
        }

        EventBus.registerEvent(this.javaClass.simpleName, UI, ExceptionEvent::class.java,{
            val str: String? = null
            println(str!!.substring(0))
        },{
            it.printStackTrace()
        })
    }
}