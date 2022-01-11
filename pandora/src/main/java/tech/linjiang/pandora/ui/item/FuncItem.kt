package tech.linjiang.pandora.ui.item

import android.graphics.Color
import android.widget.ImageView
import tech.linjiang.pandora.core.R
import tech.linjiang.pandora.ui.recyclerview.BaseItem
import tech.linjiang.pandora.ui.recyclerview.UniversalAdapter

/**
 * Created by linjiang on 2019/3/4.
 */
class FuncItem(private val icon: Int, data: String?) : BaseItem<String?>(data) {
    private var isSelected = false

    fun setSelected(selected: Boolean) {
        isSelected = selected
    }

    override fun onBinding(position: Int, pool: UniversalAdapter.ViewPool?, data: String?) {
        if (pool == null || data == null) return
        pool.setBackgroundColor(R.id.item, colors[position.mod(colors.size)].toInt())
        pool.setImageResource(R.id.icon, icon).setText(R.id.title, data)
        val imageView = pool.getView<ImageView>(R.id.icon)
        imageView.setColorFilter(if (isSelected) -0x14cb92 else Color.TRANSPARENT)
        pool.setTextColor(R.id.title, if (isSelected) -0x14cb92 else Color.BLACK)
    }

    override fun getLayout(): Int {
        return R.layout.pd_item_func
    }

    companion object {
        private val colors = arrayOf(0xFFE83A37, 0xFFF39518, 0xFF31BDEB, 0xFF2AA992)
    }
}