package org.kjh.mypracticeprojects.util

/**
 * MyPracticeProjects
 * Class: DataState
 * Created by mac on 2021/07/21.
 *
 * Description:
 */

sealed class DataState<out T> {
    data class Success<T>(val data: T?) : DataState<T>()
    data class Error(val exception: Exception) : DataState<Nothing>()
    object Loading: DataState<Nothing>()

    fun successData()   : T?      = if (this is Success) this.data else null
    fun isLoading()     : Boolean = this is Loading
    fun toErrorMessage(): String? = if (this is Error) this.exception.message else null
}