package tech.linjiang.pandora.ui.view

import android.app.Activity
import tech.linjiang.pandora.Pandora

/**
 * Created by wangchenyan.top on 2022/1/6.
 */
interface ILayer {
    var isOpen: Boolean

    fun attach(activity: Activity)

    fun detach()

    fun toggle() {
        if (isOpen) {
            close()
        } else {
            Pandora.get().getTopActivity()?.also {
                open(it)
            }
        }
    }

    fun onActivityResume(activity: Activity) {
        if (isOpen) {
            attach(activity)
        }
    }

    private fun open(activity: Activity) {
        isOpen = true
        attach(activity)
    }

    private fun close() {
        isOpen = false
        detach()
    }
}