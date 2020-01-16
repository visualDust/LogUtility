package com.visualdust.logUtility;

import java.io.File;
import java.io.FileOutputStream;

public class Test {
    public static void main(String[] args) {
        Logger logger = new Logger(Test.class);
        logger.add(new OutStreamWithType(System.out, Logger.LogType.Shell));
        logger.log(new Exception("AWSLException"), 0);
        logger.log(true, "successfully logged awsl exception");
    }
}
