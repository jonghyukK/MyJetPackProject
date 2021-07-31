package org.kjh.mypracticeprojects.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * MyPracticeProjects
 * Class: BlogCacheEntity
 * Created by mac on 2021/07/21.
 *
 * Description:
 */

@Entity(tableName = "blogs")
data class BlogCacheEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "body")
    var body: String,

    @ColumnInfo(name = "category")
    var category: String,

    @ColumnInfo(name = "image")
    var image: String
)