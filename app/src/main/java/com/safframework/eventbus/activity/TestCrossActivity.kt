package com.safframework.eventbus.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.safframework.eventbus.EventBus
import com.safframework.eventbus.domain.CrossActivityEvent

/**
 *
 * @FileName:
 *          com.safframework.eventbus.activity.TestCrossActivity
 * @author: Tony Shen
 * @date: 2019-08-27 00:13
 * @version: V1.0 <描述当前版本功能>
 */
class TestCrossActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        EventBus.post(CrossActivityEvent())
    }
}