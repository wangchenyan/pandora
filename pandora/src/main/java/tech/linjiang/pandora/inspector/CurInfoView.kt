package tech.linjiang.pandora.inspector

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import android.view.Gravity
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatTextView
import tech.linjiang.pandora.ext.removeFromParent
import tech.linjiang.pandora.ui.view.ILayer
import tech.linjiang.pandora.util.Config
import tech.linjiang.pandora.util.ViewKnife

/**
 * Created by linjiang on 2018/7/26.
 */
class CurInfoView(context: Context) : AppCompatTextView(context), ILayer {
    private var _isOpen = false

    init {
        setBackgroundColor(0x6f000000)
        textSize = 14f
        setTextColor(Color.WHITE)
        gravity = Gravity.CENTER
        setPadding(ViewKnife.dip2px(4f), 0, ViewKnife.dip2px(4f), 0)
    }

    override var isOpen: Boolean
        get() = _isOpen
        set(value) {
            _isOpen = value
        }

    fun updateText(value: CharSequence?) {
        var newValue = value
        if (!TextUtils.isEmpty(newValue)) {
            lastInfo = text
        } else {
            newValue = lastInfo
        }
        text = newValue
    }

    override fun attach(activity: Activity) {
        detach()
        layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        ).apply { gravity = Config.getUI_ACTIVITY_GRAVITY() }
        val decorView = activity.window.decorView as FrameLayout
        decorView.post { decorView.addView(this) }
    }

    override fun detach() {
        removeFromParent()
    }

    companion object {
        private var lastInfo: CharSequence? = null
    }
}