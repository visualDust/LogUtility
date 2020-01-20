package com.visualdust.logUtility.example

import com.visualdust.logUtility.Logger
import com.visualdust.logUtility.OutStreamWithType
import java.io.File
import java.io.FileOutputStream

class Example_Kotlin_EN {
    fun runExample() {
        Logger.WriteInitialMessage = false
        /* It's on by default. But you don't want to see any long-complex-meaningless logs
         * in this example right? So we set it to false here :)
         */

        /* Firstly you need a logger especially for this object. When you read your log later,
         * you will know about every log was occurred by which object.
         */
        val logger = Logger(this, false)
        /* When you initialize a logger with constructor above, the logger will automatically
         * add an output stream with the type of html which is pointed to a file named
         * "DefaultLoggerName_year_month_dayOfMonth_Log_.html"
         * If you don't like this behavior, just use constructor below:
         * Logger(this:generator, false:autoAddDefaultLogFile) so that
         * you have to add log streams manually.
         */

        /* Add System.out to the logger so that your log will be printed into terminal
         */
        logger.add(OutStreamWithType(System.out, Logger.LogType.Shell))
        /* When you call the Logger.add(stream:OutputStreamWithType) method,
         * your logger will add this output stream to a global log channel.
         * this action will be transfer into:
         * Logger.add(stream:OutputStreamWithType, 0:channel)
         * 0 is the default channel that will receive all the log broadcast.
         * When you want to log a something, you can assign it to a channel or
         * keep default to log it into ch0. I will show you about this later.
         */

        /* Add another output stream that assigned to a unique channel
         */
        try {
            val fos = FileOutputStream(File("ExceptionOnly.html"))
            /* As you can see on the file name, I'd like to record all the exceptions into an
             * unique file so that I can check exceptions later. I think ch233 is just a cute
             * channel number for recording exceptions. See stuff below:
             */logger.add(OutStreamWithType(fos, Logger.LogType.HTML), 233)
            /**
             * Well done. You've a great broadcaster!
             * So, about "LogType", you know, The format for every type is different. So declare
             * the type of the destination the stream pointed to when adding it, your logger will
             * know how to deal with this stream.
             * Sorry that for now you have only two choice for you log type:
             * Logger.LogType.Shell    Logger.LogType.HTML
             */
        } catch (e: Exception) {
            //Don't worry about exceptions here.... It's just an example right?
        }

        /* Be ready to broadcast your first log !
         */logger.log("Ready to throw an exception! (this message will only be received by streams in channel 0 cause you did't assign any channel number here.)")
        logger.log(
            "Ready to throw an exception! (this message will be received by both streams in channel 0 and streams in channel 3 cause. I declared this message should be sent to ch233. But ch0 is an channel that will receive all the messages so this will also be seen in ch0)",
            233
        )
        logger.log("This is a message again for ch0 only")
        logger.log("This is a message again for ch233 and ch0", 233)
        logger.log(Exception("AWSL"), 233)
        logger.log("Oops! Did I throw an exception into ch233?")
        /* As you can see, simple examples above introduced how to use these "lazy-friendly"
         * functions to make your log beautiful(?)
         */
    }
}

fun main() {
    /* Now run the example and enjoy this tool*/
    Example_Java_EN().runExample()
    /* After the code running, the logger may produced two html files in your project file dictionary.
    * See what's in it and you will surely like it :)
    */
}