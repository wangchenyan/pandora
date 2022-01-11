package tech.linjiang.pandora.function

import android.app.Application
import androidx.annotation.DrawableRes

/**
 * Created by linjiang on 2019/3/4.
 *
 *
 * Please check @[tech.linjiang.pandora.Pandora.addFunction]
 */
interface IFunc {
    /**
     * 唯一
     * @return the name of function.
     */
    val name: String

    /**
     * @return the icon of function.
     */
    @get:DrawableRes
    val icon: Int

    /**
     * 点击时是否清空浮层
     */
    fun needCleanWidgets(): Boolean = false

    /**
     * Click event.
     *
     * @return "Turn on" the state of the Func once return true, turn off otherwise.
     */
    fun onClick(app: Application): Boolean
}
