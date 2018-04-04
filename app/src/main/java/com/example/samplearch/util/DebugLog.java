package com.example.samplearch.util;

import android.util.Log;

import static com.example.samplearch.BuildConfig.DEBUG;



public class DebugLog {

    /**
     * Writes debugging log.
     */

    public static void write() {
        if (DEBUG) {
            final StackTraceElement stackTrace = new Exception().getStackTrace()[1];
            String fileName = stackTrace.getFileName();
            if (fileName == null)
                fileName = "";  // It is necessary if you want to use proguard obfuscation.
            final String info = stackTrace.getMethodName() + " (" + fileName + ":"
                    + stackTrace.getLineNumber() + ")";

            Log.d("***", info);
        }

    }


    public static void write(final Object message) {
        if (DEBUG) {
            final StackTraceElement stackTrace = new Exception().getStackTrace()[1];
            String fileName = stackTrace.getFileName();
            if (fileName == null)
                fileName = "";  // It is necessary if you want to use proguard obfuscation.
            final String info = stackTrace.getMethodName() + " (" + fileName + ":"
                    + stackTrace.getLineNumber() + ")";

            Log.d("***", info + " : " + String.valueOf(message));
        }

    }

    public static void write(final String tag, final Object message) {
        if (DEBUG) {
            final StackTraceElement stackTrace = new Exception().getStackTrace()[1];
            String fileName = stackTrace.getFileName();
            if (fileName == null)
                fileName = "";  // It is necessary if you want to use proguard obfuscation.
            final String info = stackTrace.getMethodName() + " (" + fileName + ":"
                    + stackTrace.getLineNumber() + ")";
            final String searcherMark = " *** ";
            Log.d("_" + tag, info + searcherMark + " : " + String.valueOf(message));
        }

    }

}
