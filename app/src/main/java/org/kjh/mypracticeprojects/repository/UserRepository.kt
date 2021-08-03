package org.kjh.mypracticeprojects.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.kjh.mypracticeprojects.model.DataResponse
import org.kjh.mypracticeprojects.model.UserModel
import org.kjh.mypracticeprojects.network.ApiService
import org.kjh.mypracticeprojects.util.DataState

/**
 * MyPracticeProjects
 * Class: UserRepository
 * Created by mac on 2021/07/31.
 *
 * Description:
 */
class UserRepository
constructor(private val apiService: ApiService){

    // Sign Up
    suspend fun reqSignUp(userModel: UserModel)
    : Flow<DataState<DataResponse>> = flow {
        emit(DataState.Loading)

        try {
            val result = apiService.reqSignUp(userModel)

            if (result.result == "Failed")
                throw Exception(result.errorMsg)

            emit(DataState.Success(result))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    // Login
    suspend fun reqLogin(email: String, pw: String,)
    : Flow<DataState<DataResponse>> = flow {
        emit(DataState.Loading)

        try {
            val result = apiService.reqLogin(email = email, pw = pw)

            if (result.result == "Failed")
                throw Exception(result.errorMsg)

            emit(DataState.Success(result))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}