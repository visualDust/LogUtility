package com.visualdust.logUtility

import java.awt.Color
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.time.LocalDateTime
import kotlin.random.Random
import com.visualdust.logUtility.AttributedHtmlStr as AHS
import com.visualdust.logUtility.AttributedHtmlStr.Companion.BuiltInColors as BIColor
import com.visualdust.logUtility.AttributedShellStr as ASS
import com.visualdust.logUtility.AttributedShellStr.Companion.BGs as ShellBG
import com.visualdust.logUtility.AttributedShellStr.Companion.FGs as ShellFG
import com.visualdust.logUtility.AttributedShellStr.Companion.Styles as ShellStyle
import com.visualdust.logUtility.AttributedHtmlStr.Companion.BuiltInColors as WebColor


class Logger {
    private val generator: Any
    private var genShellBG = ShellBG.White
    private var genHtmlBG = WebColor.White
    private var timeout = DefaultTimeout

    init {
        while (genShellBG == ShellBG.White || genShellBG == ShellBG.Black)
            genShellBG = ShellBG.values().elementAt(Random.nextInt(0, ShellBG.values().size))
        while (genHtmlBG == WebColor.White)
            genHtmlBG = WebColor.values().elementAt(Random.nextInt(0, WebColor.values().size))
    }

    constructor(generator: Any) {
        this.generator = generator
        add(OutStreamWithType(FileOutputStream(File(DefaultLogFileName), true), LogType.HTML))
    }

    constructor(generator: Any, autoAddDefaultLogFile: Boolean) {
        this.generator = generator
        if (autoAddDefaultLogFile)
            add(OutStreamWithType(FileOutputStream(File(DefaultLogFileName), true), LogType.HTML))
    }

    fun add(stream: OutStreamWithType, channel: Int) {
        if (!channelDictionary.containsKey(channel))
            channelDictionary[channel] = mutableListOf()
        channelDictionary.getValue(channel).add(stream)
        var initMessage =
            "<p>---[---(LogUtility)---(github.com/VisualDust/LogUtility)---(Version:${Version})---]--- got involved.</p>\n" +
                    "<p>---[PlatForm info]</p>\n"
        for (item in OsProperties)
            initMessage += "<p>" + item.key + " : " + item.value + "</p>\n"
        write(stream.stream, initMessage)
    }

    fun add(stream: OutStreamWithType) = add(stream, 0)

    fun remove(stream: OutputStream, channel: Int) {
        for (streams in channelDictionary.getValue(channel)) {
            if (stream == streams.stream)
                channelDictionary.getValue(channel).remove(streams)
        }
    }

    fun log(str: String, channel: Int) = logFormatted("%tif%${LogSeparator}%gen%${LogSeparator}${str}\n", channel)
    fun log(str: String) = log(str, 0)
    fun log(e: Exception, channel: Int) =
        logFormatted("%false%${LogSeparator}%tif%${LogSeparator}%gen%${LogSeparator}${e}\n", channel)

    fun log(e: Exception) = log(e, 0)
    fun log(succeed: Boolean, message: String, channel: Int) =
        logFormatted(
            "%${succeed.toString().toLowerCase()}%${LogSeparator}%tif%${LogSeparator}%gen%${LogSeparator}${message}\n",
            channel
        )

    fun log(succeed: Boolean, message: String) = log(succeed, message, 0)

    /**
     * @param formattedStr can include escape characters below :
     * %gen%   ->  generator class name
     * %true%  ->  show a "√"
     * %false% ->  show a "×"
     * %tif%   ->  time in full
     * %tis%   ->  time in short
     * %year%  ->  year
     * %mon%   ->  month
     * %dow%   ->  day of week
     * %dom%   ->  day of month
     * %doy%   ->  day of year
     * %hour%  ->  hour
     * %min%   ->  min
     * %sec%   ->  second
     * %nano%  ->  nano of second
     */
    fun logFormatted(formattedStr: String, channel: Int) = logFormatted(formattedStr, LogType.Any, channel)

    fun logFormatted(formattedStr: String) = logFormatted(formattedStr, 0)

    private fun logFormatted(str: String, type: LogType, channel: Int) {
        val ldt = LocalDateTime.now()
        when (type) {
            LogType.Shell -> {
                val processedStr = str
                    .replace(
                        "%gen%",
                        ASS(generator).applyBG(genShellBG).applyFG(ShellFG.White).applyStyle(ShellStyle.Underline).toString()
                    )
                    .replace("%true%", ASS("[√]").applyFG(ShellFG.Green).applyStyle(ShellStyle.Inverse).toString())
                    .replace("%false%", ASS("[×]").applyFG(ShellFG.Red).applyStyle(ShellStyle.Inverse).toString())
                    .replace("%tif%", ASS(ldt).applyStyle(ShellStyle.Inverse).toString())
                    .replace(
                        "%tis%",
                        ASS(ldt.dayOfYear + ldt.hour + ldt.minute + ldt.nano).applyStyle(ShellStyle.Inverse).toString()
                    )
                    .replace("%year%", ASS(ldt.year).applyStyle(ShellStyle.Inverse).toString())
                    .replace("%mon%", ASS(ldt.month).applyStyle(ShellStyle.Inverse).toString())
                    .replace("%dow%", ASS(ldt.dayOfWeek).applyStyle(ShellStyle.Inverse).toString())
                    .replace("%dom%", ASS(ldt.dayOfMonth).applyStyle(ShellStyle.Inverse).toString())
                    .replace("%doy%", ASS(ldt.dayOfYear).applyStyle(ShellStyle.Inverse).toString())
                    .replace("%hour%", ASS(ldt.hour).applyStyle(ShellStyle.Inverse).toString())
                    .replace("%min%", ASS(ldt.minute).applyStyle(ShellStyle.Inverse).toString())
                    .replace("%sec%", ASS(ldt.second).applyStyle(ShellStyle.Inverse).toString())
                    .replace("%nano%", ASS(ldt.nano).applyStyle(ShellStyle.Inverse).toString())
                broadcast(processedStr, LogType.Shell, channel)
            }
            LogType.HTML -> {
                val processedStr = "<p>" + str
                    .replace(
                        "%gen%",
                        AHS(generator).applyBG(Color(140, 0, 176)).applyFG(BIColor.White).toString()
                    )
                    .replace("%true%", AHS("[√]").applyBG(BIColor.Green).applyFG(BIColor.White).toString())
                    .replace("%false%", AHS("[×]").applyBG(BIColor.Red).applyFG(BIColor.White).toString())
                    .replace("%tif%", AHS(ldt).applyBG(BIColor.Gray).applyFG(BIColor.White).toString())
                    .replace(
                        "%tis%",
                        AHS(ldt.dayOfYear + ldt.hour + ldt.minute + ldt.nano).applyBG(BIColor.Gray).applyFG(BIColor.White).toString()
                    )
                    .replace("%year%", AHS(ldt.year).applyBG(BIColor.Gray).applyFG(BIColor.White).toString())
                    .replace("%mon%", AHS(ldt.month).applyBG(BIColor.Gray).applyFG(BIColor.White).toString())
                    .replace("%dow%", AHS(ldt.dayOfWeek).applyBG(BIColor.Gray).applyFG(BIColor.White).toString())
                    .replace("%dom%", AHS(ldt.dayOfMonth).applyBG(BIColor.Gray).applyFG(BIColor.White).toString())
                    .replace("%doy%", AHS(ldt.dayOfYear).applyBG(BIColor.Gray).applyFG(BIColor.White).toString())
                    .replace("%hour%", AHS(ldt.hour).applyBG(BIColor.Gray).applyFG(BIColor.White).toString())
                    .replace("%min%", AHS(ldt.minute).applyBG(BIColor.Gray).applyFG(BIColor.White).toString())
                    .replace("%sec%", AHS(ldt.second).applyBG(BIColor.Gray).applyFG(BIColor.White).toString())
                    .replace("%nano%", AHS(ldt.nano).applyBG(BIColor.Gray).applyFG(BIColor.White).toString()) + "</p>"
                broadcast(processedStr, LogType.HTML, channel)
            }
            LogType.Any -> {
                logFormatted(str, LogType.HTML, channel)
                logFormatted(str, LogType.Shell, channel)
            }
        }
    }

    private fun broadcast(message: String, channel: Int) = broadcast(message, LogType.Any, channel)

    private fun broadcast(message: String, type: LogType, channel: Int) {
        if (!channelDictionary.containsKey(channel)) return
        if (channel != 0) broadcast(message, type, 0)
        val subscriberList = channelDictionary.getValue(channel)
        for (subscriber in subscriberList)
            if (subscriber.type == type) write(subscriber.stream, message)
    }

    //todo rewrite this as an in queue writer and add exception resolvers
    private fun write(stream: OutputStream, message: String) {
        Thread(StreamAttendant(stream, message, timeout)).start()
    }

    companion object {
        @JvmStatic
        val Version = "0.0.1.5"
        @JvmStatic
        val DefaultLoggerName: String = "Logger"
        private val OsProperties = System.getProperties()
        @JvmStatic
        var StartUpTime: LocalDateTime = LocalDateTime.now()
        @JvmStatic
        val DefaultTimeout = 500L
        @JvmStatic
        var LogSeparator = ">"
        @JvmStatic
        var DefaultLogFileName =
            "${DefaultLoggerName}_" + "${StartUpTime.year}_" + "${StartUpTime.month}_" + "${StartUpTime.dayOfMonth}_Log_.html"
        private var channelDictionary = HashMap<Int, MutableList<OutStreamWithType>>()
    }

    enum class LogType {
        Any, HTML, Shell
    }

    private class StreamAttendant
        (var stream: OutputStream, message: String, var timeout: Long) : Runnable {
        init {
            stream.write(message.toByteArray())
            stream.flush()
        }

        override fun run() {
            try {
                Thread.sleep(timeout)
                stream.close()
            } catch (e: Exception) {
            }
        }
    }
}

class OutStreamWithType(var stream: OutputStream, var type: Logger.LogType)