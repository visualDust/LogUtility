package com.visualdust.logUtility.example;

import com.visualdust.logUtility.Logger;
import com.visualdust.logUtility.OutStreamWithType;

import java.io.File;
import java.io.FileOutputStream;

public class Example_Java_CN {
    void runExample() {
        Logger.setWriteInitialOSProperties(false);
        /* 默认情况下处于启用状态。 但是您不希望在此示例中看到任何冗长无意义的日志，对吗？ 所以我们在这里将其设置为false :)
         */

        /* 首先，您需要一个专门用于此对象的记录器。 以后阅读日志时，您将知道每个日志是由哪个对象发生的。
         */
        Logger logger = new Logger(this, false);
        /* 当您使用上述构造函数初始化记录器时，记录器将自动添加指向名为“ DefaultLoggerName_year_month_dayOfMonth_Log_.html”文件的html类
         * 型的输出流。如果您不喜欢这种行为，请使用下面的构造函数：
         * Logger（this：generator，false：autoAddDefaultLogFile）
         * 不过这样您必须手动添加日志流。
         */

        /* 将System.out添加到记录器，以便将您的日志打印到终端中
         */
        logger.add(new OutStreamWithType(System.out, Logger.LogType.Shell));
        /* 当您调用Logger.add（stream：OutputStreamWithType）方法时，记录器会将此输出流添加到全局日志通道。
         * 该操作将转换为：
         * Logger.add（stream：OutputStreamWithType，0：channel）
         * 0是将接收所有日志广播的默认通道。
         * 当您想记录某物时，可以将其分配给频道或
         * 保留默认值以将其记录到到频道0。 稍后我将向您展示。
         */

        /* 添加另一个分配给唯一通道的输出流
         */
        try {
            FileOutputStream fos = new FileOutputStream(new File("ExceptionOnly.html"));
            /* 正如您在文件名上看到的那样，我想将所有异常记录到一个特定的文件中，以便以后可以检查异常。
             * 我认为233恰好是一个记录异常的可爱频道号。 请看以下内容：
             */
            logger.add(new OutStreamWithType(fos, Logger.LogType.HTML), 233);
            /**
             * 做得好。 您是一个很棒的广播员！
             * 因此，关于“ LogType”，每种类型的格式都不同。 因此，在添加流时声明该流指向的目标的类型，您的记录器将知道如何处理此流。
             * 抱歉，您现在只能选择两种日志类型：
             *      Logger.LogType.Shell    Logger.LogType.HTML
             */
        } catch (Exception e) {
            //不要问问什么没加异常处理...这只是一个例子对吗？
        }

        /* 准备广播您的第一个日志！
         */
        logger.log("准备抛出异常！（此消息将仅由通道0中的流接收，因为您尚未在此处分配任何通道号。）");
        logger.log("准备抛出异常！（此消息将同时被通道0的流和通道3的流接收。我声明此消息应发送到ch233。但是ch0是将接收所有消息的通道，因此也将在ch0中看到）", 233);
        logger.log("这是仅适广播到频道0的消息", 0);
        logger.log("这又是一条既发送给233频道又发送给频道0的消息", 233);
        logger.log(new Exception("阿伟死了"), 233);
        logger.log("哦! 我是不是在频道233里抛出了一个阿伟死了异常?");
        /* 如您所见，上面的简单示例介绍了如何使用这些“惰性友好”功能使您的日志漂亮（？）。
         */
    }

    public static void main(String[] args) {
        /* 现在运行示例并享受此工具
         */
        new Example_Java_EN().runExample();
        /* 代码运行后，记录器可能会在您的项目文件目录中生成两个html文件。 看看里面有什么，您一定会喜欢的:)
         */
    }
}
