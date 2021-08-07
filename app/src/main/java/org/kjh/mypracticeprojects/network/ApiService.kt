package org.kjh.mypracticeprojects.network

import org.kjh.mypracticeprojects.model.DataResponse
import org.kjh.mypracticeprojects.model.UserModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * MyPracticeProjects
 * Class: BlogApi
 * Created by mac on 2021/07/21.
 *
 * Description:
 */
interface ApiService {

    /***************************************************
     *
     *  Sign Up
     *
     ***************************************************/
    @POST("user")
    suspend fun reqSignUp(@Body body: UserModel): DataResponse

    @GET("user/validate")
    suspend fun reqValidateEmail(@Query("email") email: String): DataResponse


    /***************************************************
     *
     * Log in
     *
     ***************************************************/
    @GET("login")
    suspend fun reqLogin(
        @Query("email") email: String,
        @Query("pw") pw: String,
    ): DataResponse


}