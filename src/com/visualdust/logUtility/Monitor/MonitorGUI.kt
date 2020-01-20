package com.visualdust.logUtility.Monitor

import com.visualdust.logUtility.Logger
import com.visualdust.logUtility.OutStreamWithType
import studio.visualdust.uiwidgets.stage.GSence
import studio.visualdust.uiwidgets.stage.GStage

@Suppress("JAVA_CLASS_ON_COMPANION")
class MonitorGUI {
    val stage = GStage()
    var historyDictionary = HashMap<Any, LogHistoryScene>()

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

fun main() {
    Logger.LimitStackTraceOnException=1
    var logger = Logger(String.javaClass)
    logger.add(OutStreamWithType(System.out, Logger.LogType.Shell))
    logger.log("sdad")
    try {
        var str = "";
        print(str[-1])
    } catch (e: Exception) {
        logger.log(e)
    }
}