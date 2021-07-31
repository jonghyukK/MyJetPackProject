package org.kjh.mypracticeprojects.util

/**
 * MyPracticeProjects
 * Class: DataState
 * Created by mac on 2021/07/21.
 *
 * Description:
 */
//sealed class DataState<out T> {
//
//    data class Success<out T>(val data: T) : DataState<T>()
//    data class Error(val exception: Exception): DataState<Nothing>()
//    object Loading: DataState<Nothing>()
//
//    fun toData(): T? = when (this) {
//        is Success -> this.data
//        is Error -> "Test123"
//        is Loading ->
//    }
//}

sealed class DataState<out T> {
    data class Success<T>(val data: T?) : DataState<T>()
    data class Error(val exception: Exception) : DataState<Nothing>()
    object Loading: DataState<Nothing>()

    fun isLoading(): Boolean? = if (this is Loading) true else null
    fun toErrorMessage(): String? = if (this is Error) this.exception.message else null
}