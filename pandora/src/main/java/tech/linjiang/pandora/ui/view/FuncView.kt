package tech.linjiang.pandora.ui.view

import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tech.linjiang.pandora.core.R
import tech.linjiang.pandora.ext.removeFromParent
import tech.linjiang.pandora.ui.item.FuncItem
import tech.linjiang.pandora.ui.recyclerview.UniversalAdapter
import tech.linjiang.pandora.util.ViewKnife

/**
 * Created by linjiang on 2019/3/4.
 */
class FuncView constructor(context: Context) : FrameLayout(context), ILayer {
    private var adapter: UniversalAdapter = UniversalAdapter()

    private var _isOpen = false

    init {
        inflate(context, R.layout.magic_func_view, this)
        val recyclerView: RecyclerView = findViewById(R.id.rvFunc)
        recyclerView.layoutManager = GridLayoutManager(context, 3)
        recyclerView.adapter = adapter
    }

    fun addItem(@DrawableRes icon: Int, name: String) {
        adapter.insertItem(FuncItem(icon, name))
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        adapter.setListener { position, item ->
            val selected = listener.onItemClick(position)
            (item as FuncItem).setSelected(selected)
            adapter.notifyDataSetChanged()
        }
    }

    override var isOpen: Boolean
        get() = _isOpen
        set(value) {
            _isOpen = value
        }

    override fun attach(activity: Activity) {
        detach()
        val size =
            Math.min(ViewKnife.getScreenWidth(activity), ViewKnife.getScreenHeight(activity)) / 2
        layoutParams = LayoutParams(size, size).apply { gravity = Gravity.CENTER }
        val decorView = activity.window.decorView as FrameLayout
        decorView.post { decorView.addView(this) }
    }

    override fun detach() {
        removeFromParent()
    }

    interface OnItemClickListener {
        fun onItemClick(index: Int): Boolean
    }

    companion object {
        private const val TAG = "PanelView"
    }
}