package com.visualdust.logUtility

import java.awt.Color
import java.io.*
import java.time.LocalDateTime

import com.visualdust.logUtility.AttributedShellStr as ASS
import com.visualdust.logUtility.AttributedShellStr.Companion.Styles as ShellStyle
import com.visualdust.logUtility.AttributedShellStr.Companion.FGs as ShellFG
import com.visualdust.logUtility.AttributedShellStr.Companion.BGs as ShellBG

import com.visualdust.logUtility.AttributedHtmlStr as AHS
import com.visualdust.logUtility.AttributedHtmlStr.Companion.BuiltInColors as BIColor

class Logger {
    val generator: Class<Any>

    constructor(generator: Class<Any>) {
        this.generator = generator
    }

    /**
     * @param ch
     * Set ch to 0 to write to all the channel
     */
    fun log(str: String, ch: Int) {
        //todo what to do
    }

    fun log(str: String) = log(str, 0)

    fun log(e: Exception, channel: Int) {
        //todo what todo
    }

    fun log(e: Exception) = log(e, 0)

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
        var LDT = LocalDateTime.now()
        when (type) {
            LogType.Shell, LogType.Any -> {
                var processedStr = str
                    .replace(
                        "%gen%",
                        ASS(generator.typeName).applyBG(ShellBG.Purple).applyFG(ShellFG.White).applyStyle(ShellStyle.Underline).toString()
                    )
                    .replace("%true%", ASS("[√]").applyFG(ShellFG.Green).applyStyle(ShellStyle.Inverse).toString())
                    .replace("%false%", ASS("[×]").applyFG(ShellFG.Red).applyStyle(ShellStyle.Inverse).toString())
                    .replace("%tif%", ASS(LDT).applyStyle(ShellStyle.Inverse).toString())
                    .replace(
                        "%tis%",
                        ASS(LDT.dayOfYear + LDT.hour + LDT.minute + LDT.nano).applyStyle(ShellStyle.Inverse).toString()
                    )
                    .replace("%year%", ASS(LDT.year).applyStyle(ShellStyle.Inverse).toString())
                    .replace("%mon%", ASS(LDT.month).applyStyle(ShellStyle.Inverse).toString())
                    .replace("%dow%", ASS(LDT.dayOfWeek).applyStyle(ShellStyle.Inverse).toString())
                    .replace("%dom%", ASS(LDT.dayOfMonth).applyStyle(ShellStyle.Inverse).toString())
                    .replace("%doy%", ASS(LDT.dayOfYear).applyStyle(ShellStyle.Inverse).toString())
                    .replace("%hour%", ASS(LDT.hour).applyStyle(ShellStyle.Inverse).toString())
                    .replace("%min%", ASS(LDT.minute).applyStyle(ShellStyle.Inverse).toString())
                    .replace("%sec%", ASS(LDT.second).applyStyle(ShellStyle.Inverse).toString())
                    .replace("%nano%", ASS(LDT.nano).applyStyle(ShellStyle.Inverse).toString())
                broadcast(processedStr, LogType.Shell, channel)
            }
            LogType.HTML, LogType.Any -> {
                var processedStr = str
                    .replace(
                        "%gen%",
                        AHS(generator.typeName).applyBG(Color(140, 0, 176)).applyFG(BIColor.White).toString()
                    )
                    .replace("%true%", AHS("[√]").applyBG(BIColor.Green).applyFG(BIColor.White).toString())
                    .replace("%false%", AHS("[×]").applyBG(BIColor.Red).applyFG(BIColor.White).toString())
                    .replace("%tif%", AHS(LDT).applyBG(BIColor.Gray).applyFG(BIColor.White).toString())
                    .replace(
                        "%tis%",
                        AHS(LDT.dayOfYear + LDT.hour + LDT.minute + LDT.nano).applyBG(BIColor.Gray).applyFG(BIColor.White).toString()
                    )
                    .replace("%year%", AHS(LDT.year).applyBG(BIColor.Gray).applyFG(BIColor.White).toString())
                    .replace("%mon%", AHS(LDT.month).applyBG(BIColor.Gray).applyFG(BIColor.White).toString())
                    .replace("%dow%", AHS(LDT.dayOfWeek).applyBG(BIColor.Gray).applyFG(BIColor.White).toString())
                    .replace("%dom%", AHS(LDT.dayOfMonth).applyBG(BIColor.Gray).applyFG(BIColor.White).toString())
                    .replace("%doy%", AHS(LDT.dayOfYear).applyBG(BIColor.Gray).applyFG(BIColor.White).toString())
                    .replace("%hour%", AHS(LDT.hour).applyBG(BIColor.Gray).applyFG(BIColor.White).toString())
                    .replace("%min%", AHS(LDT.minute).applyBG(BIColor.Gray).applyFG(BIColor.White).toString())
                    .replace("%sec%", AHS(LDT.second).applyBG(BIColor.Gray).applyFG(BIColor.White).toString())
                    .replace("%nano%", AHS(LDT.nano).applyBG(BIColor.Gray).applyFG(BIColor.White).toString())
                broadcast(processedStr, LogType.HTML, channel)
            }
        }
    }

    fun add(stream: OutStreamWithType, channel: Int) {
        //todo what todo
    }

    fun add(stream: OutStreamWithType) = add(stream, 0)

    private fun broadcast(message: String, channel: Int) = broadcast(message, LogType.Any, channel)

    private fun broadcast(message: String, type: LogType, channel: Int) {
        //todo what to do
    }

    private fun write(stream: OutStreamWithType, message: String) {
        //todo what to do
    }

    companion object {
        @JvmStatic
        val DefauletLoggerName: String = this.javaClass.name
        private val OsProperties = System.getProperties()
        @JvmStatic
        var StartUpTime = LocalDateTime.now()
        @JvmStatic
        var DefaultLogFile = File(
            "${DefauletLoggerName}_" +
                    "${StartUpTime.year}_" +
                    "${StartUpTime.month}_" +
                    "${StartUpTime.dayOfMonth}.log"
        )
        private var chDic: MutableList<MutableList<OutputStream>> = mutableListOf()

        enum class LogType {
            Any, HTML, Shell
        }
    }

    private class StreamQueueAttendant : Runnable {
        public var timeout: Int = 10

        constructor() {}

        override fun run() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }
}

class OutStreamWithType(var channel: Int, var type: Logger.Companion.LogType)