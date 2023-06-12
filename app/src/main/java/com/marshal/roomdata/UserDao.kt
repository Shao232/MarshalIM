package com.marshal.roomdata

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    //记住，每次修改后，都需要rebuild一下
    @Insert
    fun insertAll(vararg users: User)
    /**
     * 增
     */
    @Insert()
    fun insert(user: User) // 增加/插入 对象（一行）
    @Insert
    fun insert(users:List<User>) // 增加/插入 对象集合（多行）
    @Insert
    fun insert(vararg user: User) // 增加/插入 n个对象（一行或者多行）
    /**
     * 删
     */
    @Delete
    fun delete(user: User) // 删除 对象（一行）
    @Delete
    fun delete(users: List<User>) // 删除多个对象 （多行）
    @Delete
    fun delete(vararg user: User) // 删除 n个对象（一行或者多行）
    /**
     * 改
     */
    @Update
    fun update(user: User)
    /**
     * 查
     */
    @Query("SELECT * FROM user")
    fun getAll(): List<User>
    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>
    @Query("SELECT * FROM user WHERE age LIKE :age")
    fun findByAge(age: Int): User
    /**
     * 查询 first_name这一列的数据
     */
    @Query("SELECT first_name FROM User")
    fun queryFirstName(): List<String>
    /**
     * 查询 多列 first_name 和 last_name
     */
  /*  @Query("SELECT first_name,last_name From User")
    fun queryManyColumn(): List<User>*/
    /**
     * 增加查询条件 比如 age的条件 传入参数 在 sql语句中，以 :age 的方式来使用
     */
    @Query("SELECT * FROM user WHERE age=:age")
    fun queryUserByAge(age: Int): List<User>
    @Query("SELECT * FROM User WHERE age BETWEEN :minAge AND :maxAge")
    fun queryUserByAge(minAge: Int, maxAge: Int): List<User>
    @Query("SELECT * FROM User WHERE age IN (:ages)")
    fun queryUserByAge(ages: List<Int>): List<User>
    //在room中其他方法都可以用Query替代，所以，只要sql语句熟悉，可以就使用@Query(“sql语句”)
    @Query("INSERT INTO User VALUES (:uid,:firstName,:lastName,:age,:city)")
    fun insertByQ(uid: Int?,firstName:String,lastName:String,age: Int,city:String)

}
