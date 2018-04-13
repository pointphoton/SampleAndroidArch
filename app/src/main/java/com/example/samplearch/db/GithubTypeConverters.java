package com.example.samplearch.db;

import android.arch.persistence.room.TypeConverter;


import com.example.samplearch.util.DebugLog;
import com.example.samplearch.util.StringUtil;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

public class GithubTypeConverters {
    @TypeConverter
    public static List<Integer> stringToIntList(String data) {
        if (data == null) {
            DebugLog.write("data -> null");
            return Collections.emptyList();
        }
        DebugLog.write(MessageFormat.format("data - > {0}",data));
        return StringUtil.splitToIntList(data);
    }

    @TypeConverter
    public static String intListToString(List<Integer> ints) {
        return StringUtil.joinIntoString(ints);
    }
}

