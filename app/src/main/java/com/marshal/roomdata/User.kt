package com.marshal.roomdata

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class User( 
    // 主键 唯一 autoGenerate = true 表示自增
    @PrimaryKey(autoGenerate = true) val uid: Int?,
    // 表头名
    @ColumnInfo(name = "first_name")
    val firstName: String?,
    // 表头名
    @ColumnInfo(name = "last_name")
    val lastName: String?,
    @ColumnInfo(name = "age")
    val age: Int? = 0,
    @ColumnInfo(name = "city")
    val city: String?
)
