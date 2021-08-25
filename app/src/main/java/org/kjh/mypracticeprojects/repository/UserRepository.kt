package org.kjh.mypracticeprojects.repository

import com.orhanobut.logger.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import org.kjh.mypracticeprojects.KakaoRestAPI_KEY
import org.kjh.mypracticeprojects.model.DataResponse
import org.kjh.mypracticeprojects.model.LocationItem
import org.kjh.mypracticeprojects.model.LocationResponse
import org.kjh.mypracticeprojects.model.UserModel
import org.kjh.mypracticeprojects.network.ApiService
import org.kjh.mypracticeprojects.network.KaKaoApiService
import org.kjh.mypracticeprojects.util.DataState

/**
 * MyPracticeProjects
 * Class: UserRepository
 * Created by mac on 2021/07/31.
 *
 * Description:
 */
class UserRepository
constructor(
    private val apiService: ApiService,
    private val kakaoApiService: KaKaoApiService
) {

    // Sign up.
    suspend fun reqSignUp(
        email: String,
        pw   : String
    ): Flow<DataState<DataResponse>> = flow {
        emit(DataState.Loading)

        try {
            val result = apiService.reqSignUp(
                email = email,
                pw    = pw
            )

            if (result.result == "Failed")
                throw Exception(result.errorMsg)

            emit(DataState.Success(result))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    // Duplicate Check for Email.
    suspend fun reqValidateEmail(
        email: String
    ): Flow<DataState<DataResponse>> = flow {
        emit(DataState.Loading)

        try {
            val result = apiService.reqValidateEmail(email = email)

            if (result.result == "Failed")
                throw Exception(result.errorMsg)

            emit(DataState.Success(result))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    // Get User Data.
    suspend fun reqUser(
        email: String
    ): Flow<DataState<UserModel>> = flow {
        emit(DataState.Loading)

        try {
            val result = apiService.reqUser(email = email)

            emit(DataState.Success(result))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    // Login.
    suspend fun reqLogin(
        email: String,
        pw: String
    ): Flow<DataState<DataResponse>> = flow {
        emit(DataState.Loading)

        try {
            val result = apiService.reqLogin(
                email = email,
                pw    = pw
            )

            if (result.result == "Failed")
                throw Exception(result.errorMsg)

            emit(DataState.Success(result))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    // Upload Posts.
    suspend fun uploadPost(
        email       : String,
        content     : String,
        file        : MultipartBody.Part,
        locationData: LocationItem
    ): Flow<DataState<UserModel>> = flow {
        emit(DataState.Loading)

        try {
            val result = apiService.uploadPost(
                file        = file,
                email       = email,
                content     = content,
                x           = locationData.x,
                y           = locationData.y,
                phone       = locationData.phone,
                place_name  = locationData.place_name,
                place_url   = locationData.place_url,
                address_name  = locationData.address_name,
                category_name = locationData.category_name,
                road_address_name   = locationData.road_address_name,
                category_group_code = locationData.category_group_code,
                category_group_name = locationData.category_group_name
            )

            emit(DataState.Success(result))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }


    // [KAKAO] Search Location.
    suspend fun searchLocation(
        keyword: String
    ): Flow<DataState<LocationResponse>> = flow {
        emit(DataState.Loading)

        try {
            val result = kakaoApiService.searchLocation(
                key   = KakaoRestAPI_KEY,
                query = keyword
            )

            emit(DataState.Success(result))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}