package com.rs2.util;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class CustomPrintStream extends PrintStream {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss a");
    private final String logType;
    private final FileWriter fileWriter;
    
    private final boolean fileOutput;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public CustomPrintStream(OutputStream out, String logType, boolean fileOutput) throws IOException {
        super(out);
        this.fileOutput = fileOutput;
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        boolean throwable = false;
        if (stackTrace.length > 2) {
            throwable = stackTrace[2].getFileName() != null && stackTrace[2].getFileName().replace(".java", "").equals("Throwable");
        }
        this.logType =  throwable ? "ERROR" : logType;
        String date = new SimpleDateFormat("MM_dd_yyyy").format(new Date());
        String logFileName = "data/logs/server_" + logType.toLowerCase() + "_" + date + ".log";
        File logFile = this.fileOutput ? new File(logFileName) : null;
        if (logFile != null) {
            logFile.getParentFile().mkdirs(); // Ensure the directory exists
        } 
        this.fileWriter = logFile != null ? new FileWriter(logFile, true) : null;
    }

    private void log(String message) {
        // Get calling class and method
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        // Set the default caller if we can't find the class for some reason
        String caller = "Unknown";
        if (stack.length > 3) {
            StackTraceElement elem = stack[3];
            String className = elem.getClassName();
            // Remove instance from class name since we care about files, not objects
            className = className.replaceAll("\\$.*", "");
            String simpleClassName = className.substring(className.lastIndexOf('.') + 1); // Get simple class name
            String methodName = elem.getMethodName();
            caller = simpleClassName + "." + methodName + "()";
        }

        // Construct log message
        String logMessage = String.format("[%s] [%s] [%s] %s", dateFormat.format(new Date()), logType.toUpperCase(), caller, message);

        // Print to console and file
        super.println(logMessage);
        if (this.fileOutput) {
            executor.submit(() -> {
                try {
                    fileWriter.write(logMessage + "\n");
                    fileWriter.flush();
                } catch (IOException e) {
                    super.println("Failed to write to log file: " + e.getMessage());
                }
            });
        }
    }

    @Override
    public void println(String x) {
        log(x);
    }
    
    public void close() {
        this.executor.shutdown();
        if (fileWriter != null) {
            try {
                fileWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    
    private static void test() throws IOException {
        CustomPrintStream infoPrintStream = new CustomPrintStream(System.out, "INFO", true);
        System.setOut(infoPrintStream);
        CustomPrintStream errorPrintStream = new CustomPrintStream(System.err, "ERROR", true);
        System.setErr(errorPrintStream);
        
        System.out.println("This is a test message.");
        System.out.println("This is another test message.");
        System.err.println("This is an error message.");
        System.err.println("This is another error message.");
        
        infoPrintStream.close();
        errorPrintStream.close();
    }
    
    
    public static void main(String[] args) throws IOException {
        CustomPrintStream.test();
    }
}
