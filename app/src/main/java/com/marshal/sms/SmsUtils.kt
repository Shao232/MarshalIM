package com.marshal.sms

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.net.Uri
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.text.MessageFormat
import java.text.SimpleDateFormat
import java.util.Date


object SmsUtils {

    /**
     * 获取短信
     *
     * @return
     */
    fun obtainPhoneMessage(context: Context): List<Map<String, String?>>? {
        val smsIndex = Uri.parse("content://sms/")
        val list: MutableList<Map<String, String?>> = ArrayList()
        val projection = arrayOf("_id", "address", "person", "body", "date", "type")
        //        Cursor cur = cr.query(SMS_INBOX, projection, null, null, "date desc");
        val cur: Cursor =
            context.contentResolver?.query(smsIndex, projection, null, null, null)
                ?: return null
        while (cur.moveToNext()) {
            //手机号
            @SuppressLint("Range") val number:String? = cur.getString(cur.getColumnIndex("address"))
            //联系人姓名列表
//            @SuppressLint("Range") val name = cur.getString(cur.getColumnIndex("person"))
            //短信内容
            @SuppressLint("Range") val body:String? = cur.getString(cur.getColumnIndex("body"))
            //短信type,1，接受，2，发送
            @SuppressLint("Range") val type:String? = cur.getString(cur.getColumnIndex("type"))
            //Log.d("TAG", "number ${number},body:${body},type:${type}")
            val map: MutableMap<String, String?> = HashMap()

            map["number"] = number
            map["content"] = body
            map["type"] = type
            list.add(map)
        }
        cur.close()
        return list
    }

    /**
     * @param receive true:收到的彩信  false： 发出的彩信
     */
    @SuppressLint("Range")
    private fun getMMS(context: Context, receive: Boolean): List<Map<String, String?>>? {
        val list: MutableList<Map<String, String?>> = ArrayList()
        var parse: Uri? = null
        //这里是为了区分收件或发件，，如果不需要区分直接获取全部，可以直接使用 Uri.parse("content://mms/inbox")
        //查询所有彩信：Uri.parse("content://mms/inbox")
        parse = if (receive) {
            //查询收件箱的彩信
            Uri.parse("content://mms/inbox")
        } else {
            //查询已经发送成功的彩信
            Uri.parse("content://mms/sent")
        }
        val MMScursor: Cursor =
            context.contentResolver.query(parse, null, null, null, null)
                ?: return null //查出所有彩信
        if (!MMScursor.moveToFirst()) {
            return null
        }
        do {
            @SuppressLint("Range") val id =
                MMScursor.getString(MMScursor.getColumnIndex("_id")) // 获取pdu表里 彩信的id
            val phonenumber = getAddressNumber(context, id)
            //            KLog.e("<<彩信>>  手机号：" + phonenumber);
            @SuppressLint("Range") val timess = MMScursor.getInt(MMScursor.getColumnIndex("date"))
            val timesslong = timess.toLong() * 1000 //彩信获取的时间是以秒为单位的。
            //            KLog.e("<<彩信>>  时间：" + timeStampDate(timesslong));
            val selectionPart = "mid=$id" // part表mid字段即为 pdu表 _id 字段
            //String[]        projection = new String[]{"_id", "address", "person", "body", "date", "type","protocol"};
            //从part表 获取彩信详情
            val cursor: Cursor? = context.contentResolver.query(
                Uri.parse("content://mms/part"),
                null,
                selectionPart,
                null,
                null
            ) //查询 part 指定mid的数据
            if (cursor == null) {
                continue
            } else {
                if (cursor.moveToFirst()) {
                    var body: String? = "" //彩信文本
                    do {
                        @SuppressLint("Range") val type =
                            cursor.getString(cursor.getColumnIndex("ct"))
                        //part表 ct字段 标识 此part内容类型，彩信始末：application/smil；如果是文本附件：text/plain；
                        //图像附件：jpg：image/jpeg，gif：image/gif；音频附件：audio/mpeg
                        body = if ("text/plain" == type) {
                            @SuppressLint("Range") val data =
                                cursor.getString(cursor.getColumnIndex("_data"))
                            if (data != null) { //附件地址不为空
                                @SuppressLint("Range") val partId =
                                    cursor.getString(cursor.getColumnIndex("_id"))
                                getMmsText(context, partId)
                            } else { //附件地址为空时通过text获取文本
                                //如果是彩信始末，为彩信的SMIL内容；如果是文本附件，为附件内容；如果是视频、音频附件，text为空
                                cursor.getString(cursor.getColumnIndex("text"))
                            }
                            //                            KLog.e("<<彩信>>  内容：" + body);
                        } else {
                            ""
                        }
                    } while (cursor.moveToNext())
                    val map: MutableMap<String, String?> = HashMap()
                    map["phone"] = phonenumber
                    map["time"] = timeStampDate(timesslong)
                    map["content"] = body
                    if (receive) {
                        map["type"] = "收到一条彩信"
                    } else {
                        map["type"] = "发送一条彩信"
                    }
                    list.add(map)
                }
            }
        } while (MMScursor.moveToNext())
        return list
    }


    /**
     * 时间戳转换为字符串
     *
     * @param time:时间戳
     * @return
     */
    private fun timeStampDate(time: Long): String {
        val format = "yyyy-MM-dd HH:mm:ss"
        val sdf = SimpleDateFormat(format)
        return sdf.format(Date(time))
    }

    private fun getAddressNumber(context: Context, id: String): String? {
        //此处id 也是pdu表的_id字段
        val selectionAdd = "msg_id=$id"
        val uriStr: String = MessageFormat.format("content://mms/{0}/addr", id)
        val uriAddress = Uri.parse(uriStr)
        val cAdd: Cursor? = context.contentResolver?.query(uriAddress, null, null, null, null)
        var name: String? = null
        if (cAdd?.moveToFirst() == true) {
            do {
                @SuppressLint("Range") val number = cAdd.getString(cAdd.getColumnIndex("address"))
                if (number != null) {
                    try {
                        number.replace("-", "").toLong()
                        name = number
                    } catch (nfe: NumberFormatException) {
                        if (name == null) {
                            name = number
                        }
                    }
                }
            } while (cAdd.moveToNext())
        }
        cAdd?.close()
        return name
    }


    private fun getMmsText(context: Context, id: String): String {
        val partURI = Uri.parse("content://mms/part/$id")
        var `is`: InputStream? = null
        val sb = StringBuilder()
        try {
            `is` = context.contentResolver.openInputStream(partURI)
            if (`is` != null) {
                val isr = InputStreamReader(`is`, "UTF-8")
                val reader = BufferedReader(isr)
                var temp = reader.readLine()
                while (temp != null) {
                    sb.append(temp)
                    temp = reader.readLine()
                }
            }
        } catch (_: IOException) {
        } finally {
            if (`is` != null) {
                try {
                    `is`.close()
                } catch (_: IOException) {
                }
            }
        }
        return sb.toString()
    }

}