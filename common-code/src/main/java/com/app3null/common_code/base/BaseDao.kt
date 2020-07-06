package com.app3null.common_code.base

import androidx.room.*
import androidx.room.OnConflictStrategy.*

@Dao
abstract class BaseDao<T> {
    @Insert(onConflict =  IGNORE)
    abstract fun insert(obj: T): Long

    @Insert(onConflict = IGNORE)
    abstract fun insert(obj: List<T>): List<Long>

    @Update
    abstract fun update(obj: T)

    @Update
    abstract fun update(obj: List<T>)

    @Transaction
    open fun insertOrUpdate(obj: T) {
        val id = insert(obj)
        if (id == -1L) update(obj)
    }

    @Delete
    abstract fun delete(obj: T)

    abstract fun deleteAll()

    @Transaction
    open fun repopulateData(obj: List<T>) {
        deleteAll()
        insert(obj)
    }

    @Transaction
    open fun insertOrUpdate(objList: List<T>) {
        val insertResult = insert(objList)
        val updateList = mutableListOf<T>()

        for (i in insertResult.indices) {
            if (insertResult[i] == -1L) updateList.add(objList[i])
        }

        if (!updateList.isEmpty()) update(updateList)
    }
}