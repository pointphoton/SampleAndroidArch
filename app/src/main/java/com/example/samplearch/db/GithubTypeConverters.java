package com.example.samplearch.db;

import android.arch.persistence.room.TypeConverter;


import com.example.samplearch.util.StringUtil;

import java.util.Collections;
import java.util.List;

public class GithubTypeConverters {
    @TypeConverter
    public static List<Integer> stringToIntList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }
        return StringUtil.splitToIntList(data);
    }

    @TypeConverter
    public static String intListToString(List<Integer> ints) {
        return StringUtil.joinIntoString(ints);
    }
}

