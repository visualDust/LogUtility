package com.visualdust.logUtility.monitor

import com.visualdust.logUtility.Logger
import com.visualdust.logUtility.RelatedEvent
import studio.visualdust.uiwidgets.stage.GSence
import studio.visualdust.uiwidgets.stage.GStage
import java.awt.Color
import java.lang.Exception
import java.time.LocalDateTime
import java.util.function.Consumer
import javax.swing.JLabel
import javax.swing.JList
import kotlin.random.Random

@Suppress("JAVA_CLASS_ON_COMPANION")
class MonitorGUI {
    val stage = GStage()
    var recordDictionary = HashMap<Any, LogRecord>()
    //todo add display list here

    fun Launch() {
        if (!initilized) Initialize()
        var eventResolver = Consumer<RelatedEvent<Any, String>> {
            val generator = it.who
            if (!recordDictionary.containsKey(generator)) recordDictionary.put(generator, LogRecord(generator))
            recordDictionary.getValue(generator).accept(it.containing)
        }
        logger.addEventResolver(eventResolver)
        var exceptionResolver = Consumer<RelatedEvent<Any, Exception>> {
            val generator = it.who
            if (!recordDictionary.containsKey(generator)) recordDictionary.put(generator, LogRecord(generator))
            recordDictionary.getValue(generator).accept(it.containing)
        }
        logger.addExceptionResolver(exceptionResolver)
    }

    companion object {
        private val initilized = false
        private lateinit var logger: Logger
        fun Initialize() {
            logger = Logger(MonitorGUI.javaClass)
        }
    }

    class LogRecord {
        var generator: Any
        public val scene = GSence()
        val displayList = JList<String>()
        public var bump = 0
        var colorTag = Color(Random.nextInt(0, 200), Random.nextInt(0, 200), Random.nextInt(0, 200))

        constructor(who: Any) {
            this.generator = who
            /**
             * todo initialize GScene here
             */
        }

        fun accept(event: String) {
            displayList.add(JLabel(LocalDateTime.now().toString() + event), 0)
            displayList.repaint()
        }

        fun accept(exception: Exception) {
            displayList.add(JLabel(LocalDateTime.now().toString() + exception), 0)
            displayList.getComponent(0).foreground = Color(255, 255, 255)
            displayList.getComponent(0).background = Color(170, 0, 53)
        }

        class BumoUpdater : Runnable {
            var logRecord: LogRecord

            constructor(logRecord: LogRecord) {
                this.logRecord = logRecord
            }

            var pause = false
            override fun run() {
                while (true) {
                    try {
                        Thread.sleep(1000)
                        logRecord.bump++
                    } catch (e: Exception) {
                        logger.log(e)
                    }
                }
            }

        }
    }
}

fun main() {

}