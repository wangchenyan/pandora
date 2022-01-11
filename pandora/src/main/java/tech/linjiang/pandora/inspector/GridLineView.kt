package tech.linjiang.pandora.inspector

import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import android.widget.FrameLayout
import tech.linjiang.pandora.ext.removeFromParent
import tech.linjiang.pandora.ui.view.ILayer
import tech.linjiang.pandora.util.Config
import tech.linjiang.pandora.util.ViewKnife

/**
 * Created by linjiang on 2018/7/23.
 */
class GridLineView(context: Context?) : View(context), ILayer {
    private var lineInterval = 0

    private var _isOpen = false

    private val paint: Paint = Paint().apply {
        isAntiAlias = true
        color = 0x30000000
        strokeWidth = 1f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        var startX = 0
        while (startX < measuredWidth) {
            canvas.drawLine(startX.toFloat(), 0f, startX.toFloat(), measuredHeight.toFloat(), paint)
            startX += lineInterval
        }
        var startY = 0
        while (startY < measuredHeight) {
            canvas.drawLine(0f, startY.toFloat(), measuredWidth.toFloat(), startY.toFloat(), paint)
            startY += lineInterval
        }
    }

    override var isOpen: Boolean
        get() = _isOpen
        set(value) {
            _isOpen = value
        }

    override fun attach(activity: Activity) {
        detach()
        lineInterval = ViewKnife.dip2px(Config.getUI_GRID_INTERVAL().toFloat())
        layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        val decorView = activity.window.decorView as FrameLayout
        decorView.post { decorView.addView(this) }
    }

    override fun detach() {
        removeFromParent()
    }
}