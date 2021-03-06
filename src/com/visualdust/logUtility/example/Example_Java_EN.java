package com.visualdust.logUtility.example;

import com.visualdust.logUtility.Logger;
import com.visualdust.logUtility.OutStreamWithType;

import java.io.File;
import java.io.FileOutputStream;

public class Example_Java_EN {
    void runExample() {

        /* Firstly you need a logger especially for this object. When you read your log later,
         * you will know about every log was occurred by which object.
         */
        Logger logger = new Logger(this, false);
        /* When you initialize a logger with constructor above, the logger will automatically
         * add an output stream with the type of html which is pointed to a file named
         * "DefaultLoggerName_year_month_dayOfMonth_Log_.html"
         * If you don't like this behavior, just use constructor below:
         * Logger(this:generator, false:autoAddDefaultLogFile) so that
         * you have to add file streams manually or you just don't need to log to a file.
         */

        /* For now there is no need to add System.out to the logger manually. It will be added automatically.
         * If you don't like this behavior, do like this:
         * Logger.setAutoBindToTerminal(false)
         * And you may add it where you need
         */
//        logger.add(new OutStreamWithType(System.out, Logger.LogType.Shell));
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
            FileOutputStream fos = new FileOutputStream(new File("ExceptionOnly.html"));
            /* As you can see on the file name, I'd like to record all the exceptions into an
             * unique file so that I can check exceptions later. I think ch233 is just a cute
             * channel number for recording exceptions. See stuff below:
             */
            logger.add(new OutStreamWithType(fos, Logger.LogType.HTML), 233);
            /**
             * Well done. You've a great broadcaster!
             * So, about "LogType", you know, The format for every type is different. So declare
             * the type of the destination the stream pointed to when adding it, your logger will
             * know how to deal with this stream.
             * Sorry that for now you have only two choice for you log type:
             *      Logger.LogType.Shell    Logger.LogType.HTML
             */
        } catch (Exception e) {
            //Don't worry about exceptions here.... It's just an example right?
        }

        /* Be ready to broadcast your first log !
         */
        logger.log("Ready to throw an exception! (this message will only be received by streams in channel 0 cause you did't assign any channel number here.)");
        logger.log("Ready to throw an exception! (this message will be received by both streams in channel 0 and streams in channel 3 cause. I declared this message should be sent to ch233. But ch0 is an channel that will receive all the messages so this will also be seen in ch0)", 233);
        logger.log("This is a message again for ch0 only", 0);
        logger.log("This is a message again for ch233 and ch0", 233);
        logger.log(new Exception("AWSL"), 233);
        logger.log("Oops! Did I throw an exception into ch233?");
        /* As you can see, simple examples above introduced how to use these "lazy-friendly"
         * functions to make your log beautiful(?)
         */
    }

    public static void main(String[] args) {
        /* Now run the example and enjoy this tool
         */
        new Example_Java_EN().runExample();
        /* After the code running, the logger may produced two html files in your project file dictionary.
         * See what's in it and you will surely like it :)
         */
    }
}
