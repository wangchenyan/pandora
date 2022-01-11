package tech.linjiang.pandora

import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import androidx.annotation.DrawableRes
import tech.linjiang.pandora.core.R
import tech.linjiang.pandora.ext.removeFromParent
import tech.linjiang.pandora.function.IFunc
import tech.linjiang.pandora.inspector.CurInfoView
import tech.linjiang.pandora.inspector.GridLineView
import tech.linjiang.pandora.ui.Dispatcher
import tech.linjiang.pandora.ui.connector.Type
import tech.linjiang.pandora.ui.view.EntranceView
import tech.linjiang.pandora.ui.view.FuncView
import tech.linjiang.pandora.util.Utils
import tech.linjiang.pandora.util.ViewKnife
import java.util.*

/**
 * Created by linjiang on 2019/3/4.
 */
internal class FuncController(private val app: Application) : ActivityLifecycleCallbacks,
    FuncView.OnItemClickListener {
    private val entranceView = EntranceView(app)
    private val funcView = FuncView(app)
    private val curInfoView = CurInfoView(app)
    private val gridLineView = GridLineView(app)

    // private var activeCount = 0
    private val functions: MutableList<IFunc> = ArrayList()

    fun addFunc(func: IFunc) {
        if (functions.find { it.name == func.name } == null) {
            functions.add(func)
            funcView.addItem(func.icon, func.name)
        }
    }

    init {
        entranceView.setClickListener { toggle() }
        funcView.setOnItemClickListener(this)
        app.registerActivityLifecycleCallbacks(this)
        addDefaultFunctions()
    }

    /*fun open() {
        if (funcView.isVisible()) {
            val succeed: Boolean = funcView.open()
            if (!succeed) {
                Dispatcher.start(Utils.getContext(), Type.PERMISSION)
            }
        }
    }

    fun close() {
        funcView.close()
    }

    private fun showOverlay() {
        funcView.visibility = View.VISIBLE
        curInfoView.visibility = View.VISIBLE
        gridLineView.visibility = View.VISIBLE
    }

    private fun hideOverlay() {
        funcView.visibility = View.GONE
        curInfoView.visibility = View.GONE
        gridLineView.visibility = View.GONE
    }*/

    fun toggle() {
        funcView.toggle()
    }

    override fun onItemClick(index: Int): Boolean {
        val func = functions[index]
        if (func.needCleanWidgets()) {
            cleanWidgets()
        }
        return func.onClick(app)
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}

    override fun onActivityStarted(activity: Activity) {
        /*activeCount++
        if (activeCount == 1) {
            showOverlay()
        }*/
    }

    override fun onActivityResumed(activity: Activity) {
        if (activity !is Dispatcher) {
            funcView.onActivityResume(activity)
            curInfoView.onActivityResume(activity)
            gridLineView.onActivityResume(activity)
            entranceView.onActivityResume(activity)
        }
        curInfoView.updateText(activity.javaClass.name)
    }

    override fun onActivityPaused(activity: Activity) {
        /*if (activity is Dispatcher) {
            if (activeCount > 0) {
                showOverlay()
            }
        }*/
        curInfoView.updateText(null)
    }

    override fun onActivityStopped(activity: Activity) {
        /*activeCount--
        if (activeCount <= 0) {
            hideOverlay()
        }*/
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

    override fun onActivityDestroyed(activity: Activity) {}

    private fun addDefaultFunctions() {
        addFunc(newFunc(ViewKnife.getString(R.string.pd_name_network), R.drawable.pd_network) {
            Dispatcher.start(Utils.getContext(), Type.NET)
            false
        })
        addFunc(newFunc(ViewKnife.getString(R.string.pd_name_sandbox), R.drawable.pd_disk) {
            Dispatcher.start(Utils.getContext(), Type.FILE)
            false
        })
        addFunc(newFunc(ViewKnife.getString(R.string.pd_name_select), R.drawable.pd_select, true) {
            Dispatcher.start(Utils.getContext(), Type.SELECT)
            false
        })
        addFunc(newFunc(ViewKnife.getString(R.string.pd_name_crash), R.drawable.pd_bug) {
            Dispatcher.start(Utils.getContext(), Type.BUG)
            false
        })
        addFunc(newFunc(ViewKnife.getString(R.string.pd_name_layer), R.drawable.pd_layer, true) {
            Dispatcher.start(Utils.getContext(), Type.HIERARCHY)
            false
        })
        addFunc(newFunc(ViewKnife.getString(R.string.pd_name_baseline), R.drawable.pd_ruler, true) {
            Dispatcher.start(Utils.getContext(), Type.BASELINE)
            false
        })
        addFunc(newFunc(ViewKnife.getString(R.string.pd_name_navigate), R.drawable.pd_map) {
            Dispatcher.start(Utils.getContext(), Type.ROUTE)
            false
        })
        addFunc(newFunc(ViewKnife.getString(R.string.pd_name_history), R.drawable.pd_history) {
            Dispatcher.start(Utils.getContext(), Type.HISTORY)
            false
        })
        addFunc(newFunc(ViewKnife.getString(R.string.pd_name_activity), R.drawable.pd_windows) {
            val isOpen = curInfoView.isOpen
            curInfoView.toggle()
            !isOpen
        })
        addFunc(newFunc(ViewKnife.getString(R.string.pd_name_gridline), R.drawable.pd_grid) {
            val isOpen = gridLineView.isOpen
            gridLineView.toggle()
            !isOpen
        })
        addFunc(newFunc(ViewKnife.getString(R.string.pd_name_config), R.drawable.pd_config) {
            Dispatcher.start(Utils.getContext(), Type.CONFIG)
            false
        })
    }

    private fun newFunc(
        name: String,
        @DrawableRes icon: Int,
        needCleanWidgets: Boolean = false,
        onClick: (app: Application) -> Boolean
    ): IFunc {
        return object : IFunc {
            override val name: String
                get() = name
            override val icon: Int
                get() = icon

            override fun needCleanWidgets(): Boolean {
                return needCleanWidgets
            }

            override fun onClick(app: Application): Boolean {
                return onClick.invoke(app)
            }
        }
    }

    private fun cleanWidgets() {
        entranceView.removeFromParent()
        funcView.removeFromParent()
        curInfoView.removeFromParent()
        gridLineView.removeFromParent()
    }
}