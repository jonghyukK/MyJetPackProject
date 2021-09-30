package org.kjh.mypracticeprojects.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.kjh.mypracticeprojects.model.PostModel
import org.kjh.mypracticeprojects.model.UserModel
import org.kjh.mypracticeprojects.network.ApiService
import org.kjh.mypracticeprojects.util.DataState

/**
 * MyPracticeProjects
 * Class: PostRepository
 * Created by mac on 2021/09/01.
 *
 * Description:
 */
class PostRepository
constructor(
    private val apiService: ApiService
){
    suspend fun deletePost(
        postId: Int,
        email : String
    ): Flow<DataState<UserModel>> = flow {
        emit(DataState.Loading)

        try {
            val result = apiService.deletePost(
                postId = postId,
                email  = email
            )

            emit(DataState.Success(result))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun getPosts(
        cityName : String? = null,
        placeName: String? = null
    ): Flow<DataState<List<PostModel>>> = flow {
        emit(DataState.Loading)

        try {
            val result = apiService.getPosts(cityName, placeName)

            emit(DataState.Success(result))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}