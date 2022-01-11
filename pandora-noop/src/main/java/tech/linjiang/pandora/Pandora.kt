package tech.linjiang.pandora

import tech.linjiang.pandora.database.Databases
import tech.linjiang.pandora.function.IFunc
import tech.linjiang.pandora.inspector.attribute.AttrFactory
import tech.linjiang.pandora.network.OkHttpInterceptor
import tech.linjiang.pandora.preference.SharedPref

/**
 * Created by linjiang on 29/05/2018.
 */
class Pandora private constructor() {
    val interceptor: OkHttpInterceptor
        get() = OkHttpInterceptor()
    val databases: Databases
        get() = Databases()
    val sharedPref: SharedPref
        get() = SharedPref()
    val attrFactory: AttrFactory
        get() = AttrFactory()

    fun addFunction(func: IFunc?) {}
    fun toggle() {}
    fun disableShakeSwitch() {}

    companion object {
        @JvmStatic
        fun get(): Pandora {
            return Pandora()
        }
    }
}