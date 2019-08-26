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
 *          com.safframework.eventbus.fragment.Fragment1
 * @author: Tony Shen
 * @date: 2019-08-26 21:25
 * @version: V1.0 <描述当前版本功能>
 */
class Fragment1 : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_1, container, false)



        return v
    }
}