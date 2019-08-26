package com.safframework.eventbus.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.safframework.eventbus.R
import com.safframework.eventbus.app.BaseFragment

/**
 *
 * @FileName:
 *          com.safframework.eventbus.fragment.Fragment2
 * @author: Tony Shen
 * @date: 2019-08-26 21:27
 * @version: V1.0 <描述当前版本功能>
 */
class Fragment2 : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_2, container, false)


        return v
    }
}