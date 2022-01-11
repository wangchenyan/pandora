package tech.linjiang.pandora

import android.app.Activity
import android.app.Application
import androidx.core.content.FileProvider
import tech.linjiang.pandora.crash.CrashHandler
import tech.linjiang.pandora.database.Databases
import tech.linjiang.pandora.function.IFunc
import tech.linjiang.pandora.history.HistoryRecorder
import tech.linjiang.pandora.inspector.attribute.AttrFactory
import tech.linjiang.pandora.network.OkHttpInterceptor
import tech.linjiang.pandora.preference.SharedPref
import tech.linjiang.pandora.util.SensorDetector
import tech.linjiang.pandora.util.Utils

/**
 * Created by linjiang on 29/05/2018.
 */
class Pandora : FileProvider(), SensorDetector.Callback {
    private var notHostProcess = false
    private var crashHandler: CrashHandler? = null
    private var historyRecorder: HistoryRecorder? = null
    private var funcController: FuncController? = null
    private var sensorDetector: SensorDetector? = null

    var interceptor: OkHttpInterceptor? = null
        private set
    var databases: Databases? = null
        private set
    var sharedPref: SharedPref? = null
        private set
    var attrFactory: AttrFactory? = null
        private set

    init {
        if (INSTANCE != null) {
            throw RuntimeException()
        }
    }

    override fun onCreate(): Boolean {
        INSTANCE = this
        val context = Utils.makeContextSafe(context)
        init(context as Application)
        return super.onCreate()
    }

    private fun init(app: Application) {
        Utils.init(app)
        funcController = FuncController(app)
        // sensorDetector = SensorDetector(if (notHostProcess) null else this)
        interceptor = OkHttpInterceptor()
        databases = Databases()
        sharedPref = SharedPref()
        attrFactory = AttrFactory()
        crashHandler = CrashHandler(app)
        historyRecorder = HistoryRecorder(app)
    }

    fun getTopActivity(): Activity? = historyRecorder?.topActivity

    /**
     * Add a custom entry to the panel.
     * also see @[IFunc]
     *
     * @param func
     */
    fun addFunction(func: IFunc) {
        funcController?.addFunc(func)
    }

    /**
     * Open the panel.
     */
    /*fun open() {
        if (notHostProcess) {
            return
        }
        funcController?.open()
    }*/

    /**
     * Close the panel.
     */
    /*fun close() {
        funcController?.close()
    }*/

    fun toggle() {
        funcController?.toggle()
    }

    /**
     * Disable the Shake feature.
     */
    fun disableShakeSwitch() {
        sensorDetector?.unRegister()
    }

    override fun shakeValid() {
        // open()
    }

    companion object {
        private var INSTANCE: Pandora? = null

        @JvmStatic
        fun get(): Pandora {
            if (INSTANCE == null) {
                // Not the host process
                val pandora = Pandora()
                pandora.notHostProcess = true
                pandora.onCreate()
            }
            return INSTANCE!!
        }
    }
}