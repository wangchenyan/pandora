package tech.linjiang.pandora.ext

import android.view.View
import android.view.ViewGroup

/**
 * Created by wangchenyan.top on 2022/1/6.
 */

fun View.removeFromParent() {
    (parent as? ViewGroup)?.removeView(this)
}