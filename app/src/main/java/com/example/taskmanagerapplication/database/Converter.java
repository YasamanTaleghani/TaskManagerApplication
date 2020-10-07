package com.example.taskmanagerapplication.database;

import androidx.room.TypeConverter;

import java.util.Date;
import java.util.UUID;


public class Converter {

    @TypeConverter
    public static Date LongToDate(Long logn){
        return new Date(logn);
    }

    @TypeConverter
    public static Long DateToLong(Date date){
        return date.getTime();
    }

    @TypeConverter
    public static UUID StringToUUID(String string){
        return UUID.fromString(string);
    }

    @TypeConverter
    public static String UUIDToString(UUID uuid){
        return uuid.toString();
    }
}
