package com.safframework.eventbus.app

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.os.Build
import android.support.v4.app.Fragment

/**
 *
 * @FileName:
 *          com.safframework.eventbus.app.BaseFragment
 * @author: Tony Shen
 * @date: 2019-08-26 21:24
 * @version: V1.0 <描述当前版本功能>
 */
open class BaseFragment : Fragment() {

    /**
     * Fragment 所在的 FragmentActivity
     */
    var mContext: Activity? = null

    /**
     * Deprecated on API 23
     * @param activity
     */
    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        if (Build.VERSION.SDK_INT < 23) {
            this.mContext = activity
        }
    }

    @TargetApi(23)
    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is Activity) {
            this.mContext = context
        }
    }
}