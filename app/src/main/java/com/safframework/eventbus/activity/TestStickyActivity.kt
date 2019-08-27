package com.safframework.eventbus.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.safframework.eventbus.EventBus
import com.safframework.eventbus.UI
import com.safframework.eventbus.domain.NormalEvent
import com.safframework.eventbus.domain.StickyEvent

/**
 *
 * @FileName:
 *          com.safframework.eventbus.activity.TestStickyActivity
 * @author: Tony Shen
 * @date: 2019-08-27 15:48
 * @version: V1.0 <描述当前版本功能>
 */
class TestStickyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initData()
        registerEvents()
    }

    private fun initData() {

        EventBus.postSticky(StickyEvent())
        EventBus.post(NormalEvent())
    }

    private fun registerEvents() {

        EventBus.registerSticky(this.javaClass.simpleName, UI, StickyEvent::class.java) {

            Toast.makeText(this@TestStickyActivity, "this is StickyEvent", Toast.LENGTH_SHORT).show()
        }

        EventBus.register(this.javaClass.simpleName, UI, NormalEvent::class.java) {

            Toast.makeText(this@TestStickyActivity, "this is NormalEvent", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.removeStickyEvent(StickyEvent::class.java)
        EventBus.unregister(this.javaClass.simpleName)
    }
}