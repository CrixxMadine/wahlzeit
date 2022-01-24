package org.wahlzeit.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Utility class to trace object creation
 * Can be configured using TraceLevel
 */
public class Tracer {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    private static final String TRACE_DIRECTORY = "logs";
    private static final String TRACE_FILE_EXTENSION = ".log";
    private static final String LINE_SEPARATOR = "\n";

    private static final Object FILE_LOCK = new Object();

    private static boolean isInitialized = false;

    private static TraceLevel TRACE_LEVEL = TraceLevel.NONE;

    private static Path logFile;

    public synchronized static void initialize(TraceLevel traceLevel) {
        synchronized (FILE_LOCK) {
            TRACE_LEVEL = traceLevel;

            if (traceLevel == TraceLevel.NONE) {
                isInitialized = false;
                logFile = null;

            } else {
                isInitialized = true;
                createNewTraceFile();
            }

        }
    }

    public static void trace(String message, TraceLevel level) {
        if (isInitialized) {
            switch (TRACE_LEVEL) {
                case DEBUG:
                    writeLineToFile(message);
                    break;

                case ERROR:
                    if (level == TraceLevel.ERROR) {
                        writeLineToFile(message);
                    }
                    break;

                default:
                    break;
            }
        }
    }

    private static void createNewTraceFile() {
        try {

            var logDirectory = Paths.get(TRACE_DIRECTORY).toAbsolutePath();
            if (Files.notExists(logDirectory)) {
                Files.createDirectories(logDirectory);
            }

            var timeStamp = new Timestamp(System.currentTimeMillis());
            var logFileName = DATE_FORMAT.format(timeStamp) + TRACE_FILE_EXTENSION;

            var logFilePath = Paths.get(logDirectory.toString() + File.separatorChar + logFileName);

            int duplicateCount = 0;

            while (Files.exists(logFilePath)) {
                duplicateCount++;
                logFileName = DATE_FORMAT.format(timeStamp) + "_" + duplicateCount + ".log";
                logFilePath = Paths.get(logDirectory.toString() + File.separatorChar + logFileName);
            }
            logFile = Files.createFile(logFilePath);

            System.out.println("Created new trace file " + logFile.toAbsolutePath());

        } catch (Exception e) {
            System.err.println("File error, can not create new trace file");
            System.err.println("Exception is: " + e.getMessage());
            System.err.println("Tracing is aborted");
        }
    }

    /**
     * Write a single line in the trace
     * NOTE: This code is highly not performant -> prefer using something like java.util.logging.Logger
     * However this Tracer was just built for academic purposes and used for homework 11
     */
    private static void writeLineToFile(String message) {
        synchronized (FILE_LOCK) {
            try (var fileStream = new FileOutputStream(logFile.toAbsolutePath().toString(), true);
                 var outputWriter = new OutputStreamWriter(fileStream)) {

                outputWriter.write(message + LINE_SEPARATOR);
                outputWriter.flush();

                // Force OS to actually write file before existing program
                fileStream.getChannel().force(true);

            } catch (IOException e) {
                isInitialized = false;
                TRACE_LEVEL = TraceLevel.NONE;

                System.err.println("File error, can not write to trace file");
                System.err.println("Exception is: " + e.getMessage());
                System.err.println("Tracing is aborted");
            }
        }
    }
}
