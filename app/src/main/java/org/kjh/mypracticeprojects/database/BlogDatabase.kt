package org.kjh.mypracticeprojects.database

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * MyPracticeProjects
 * Class: BlogDatabase
 * Created by mac on 2021/07/21.
 *
 * Description:
 */

@Database(entities = [BlogCacheEntity::class], version = 1)
abstract class BlogDatabase: RoomDatabase() {
    abstract fun blogDao(): BlogDao

    companion object {
        const val DATABASE_NAME: String = "blog_db"
    }
}