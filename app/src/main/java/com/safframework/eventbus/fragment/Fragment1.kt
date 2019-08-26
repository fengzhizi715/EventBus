package com.safframework.eventbus.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.safframework.eventbus.EventBus
import com.safframework.eventbus.R
import com.safframework.eventbus.app.BaseFragment
import com.safframework.eventbus.domain.Fragment1Event
import kotlinx.android.synthetic.main.fragment_1.*

/**
 *
 * @FileName:
 *          com.safframework.eventbus.fragment.Fragment1
 * @author: Tony Shen
 * @date: 2019-08-26 21:25
 * @version: V1.0 <描述当前版本功能>
 */
class Fragment1 : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_1, container, false)

        v.findViewById<Button>(R.id.button1).setOnClickListener {

            EventBus.post(Fragment1Event())
        }

        return v
    }
}