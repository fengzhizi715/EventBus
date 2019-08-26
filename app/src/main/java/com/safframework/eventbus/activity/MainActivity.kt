package com.safframework.eventbus.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.safframework.eventbus.R
import kotlinx.android.synthetic.main.activity_main.*


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

    }
}