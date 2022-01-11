package tech.linjiang.pandora.ui.view

import android.app.Activity
import android.content.Context
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView
import tech.linjiang.pandora.core.R
import tech.linjiang.pandora.ext.removeFromParent
import tech.linjiang.pandora.util.ViewKnife

/**
 * Created by wangchenyan.top on 2022/1/6.
 */
class EntranceView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatImageView(context, attrs), ILayer {
    private val lastPos by lazy {
        PointF()
    }
    private val lastMargin by lazy {
        PointF(
            ViewKnife.getScreenWidth(context) - SIZE,
            ViewKnife.getScreenHeight(context) / 3f
        )
    }
    private var clickListener: OnClickListener? = null
    private var isMove = false

    init {
        setImageResource(R.drawable.magic_box)
    }

    fun setClickListener(listener: OnClickListener) {
        this.clickListener = listener
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                isMove = false
                lastPos.x = event.rawX
                lastPos.y = event.rawY
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                val touchSlop = ViewConfiguration.getTouchSlop()
                val moveX = Math.abs(event.rawX - lastPos.x)
                val moveY = Math.abs(event.rawY - lastPos.y)
                if (isMove || (moveX >= touchSlop && moveY >= touchSlop)) {
                    isMove = true
                    lastPos.x = event.rawX
                    lastPos.y = event.rawY
                    val params = layoutParams as FrameLayout.LayoutParams
                    params.leftMargin = getValidMarginX(event.rawX - SIZE / 2).toInt()
                    params.topMargin = getValidMarginY(event.rawY - SIZE / 2).toInt()
                    layoutParams = params
                    lastMargin.x = params.leftMargin.toFloat()
                    lastMargin.y = params.topMargin.toFloat()
                    return true
                }
            }
            MotionEvent.ACTION_UP -> {
                if (isMove.not()) {
                    clickListener?.onClick(this)
                }
            }
        }
        return super.onTouchEvent(event)
    }

    override var isOpen: Boolean
        get() = true
        set(value) {}

    override fun attach(activity: Activity) {
        detach()
        layoutParams =
            FrameLayout.LayoutParams(SIZE.toInt(), SIZE.toInt()).apply {
                leftMargin = getValidMarginX(lastMargin.x).toInt()
                topMargin = getValidMarginY(lastMargin.y).toInt()
            }
        val decorView = activity.window.decorView as FrameLayout
        decorView.post { decorView.addView(this) }
    }

    override fun detach() {
        removeFromParent()
    }

    private fun getValidMarginX(margin: Float): Float {
        return Math.min(
            ViewKnife.getScreenWidth(context) - SIZE,
            Math.max(0f, margin)
        )
    }

    private fun getValidMarginY(margin: Float): Float {
        return Math.min(
            ViewKnife.getScreenHeight(context) - SIZE,
            Math.max(0f, margin)
        )
    }

    companion object {
        private val SIZE: Float by lazy { ViewKnife.dip2px(50f).toFloat() }
    }
}