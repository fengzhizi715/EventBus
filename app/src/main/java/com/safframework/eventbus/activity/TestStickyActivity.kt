package com.safframework.eventbus.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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

        EventBus.postSticky(StickyEvent()) // 发送Sticky Event
        EventBus.post(NormalEvent())       // 发送普通的 Event
    }

    private fun registerEvents() {

        // 注册 Sticky Event
        EventBus.registerSticky(this.javaClass.simpleName, UI, StickyEvent::class.java) {

            Toast.makeText(this@TestStickyActivity, "this is StickyEvent", Toast.LENGTH_SHORT).show()
        }

        EventBus.register(this.javaClass.simpleName, UI, NormalEvent::class.java) {

            Toast.makeText(this@TestStickyActivity, "this is NormalEvent", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.removeStickyEvent(StickyEvent::class.java) // 先 removeStickyEvent
        EventBus.unregister(this.javaClass.simpleName)      // 再 unregister
    }
}