package com.marshal.base_common.utils

import com.google.gson.Gson
import java.io.InputStream
import java.io.Reader
import java.lang.reflect.Type

object GsonUtils {

    private val gson = Gson()

    fun stringToJson(str:String):String{
       return gson.toJson(str)
    }

    fun streamToJson(input:InputStream):String{
        return gson.toJson(input)
    }

    fun stringFromJson(str:String,typeOfT: Type):Type{
        return gson.fromJson(str,typeOfT)
    }

    fun readerFromJson(reader: Reader):String{
        return gson.fromJson(reader,String::class.java)
    }

}