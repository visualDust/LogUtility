package Monitor

import com.visualdust.logUtility.Logger
import studio.visualdust.uiwidgets.stage.GSence
import studio.visualdust.uiwidgets.stage.GStage

@Suppress("JAVA_CLASS_ON_COMPANION")
class MonitorGUI {
    val stage = GStage()
    var historyDictionary = HashMap<Any,LogHistoryScene>()

    fun Launch() {
        if (!initilized) Initialize()

    }

    companion object {
        private val initilized = false
        private lateinit var logger: Logger
        fun Initialize() {
            logger = Logger(MonitorGUI.javaClass)
        }
    }

    class LogHistoryScene {
        public val scene = GSence()
        constructor() {}
    }
}

fun main(){

}