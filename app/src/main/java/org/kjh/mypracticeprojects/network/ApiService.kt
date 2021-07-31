package org.kjh.mypracticeprojects.network

import org.kjh.mypracticeprojects.model.UserModel
import org.kjh.mypracticeprojects.model.UserResponse
import retrofit2.http.*

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
    suspend fun reqSignUp(@Body body: UserModel): UserResponse
}