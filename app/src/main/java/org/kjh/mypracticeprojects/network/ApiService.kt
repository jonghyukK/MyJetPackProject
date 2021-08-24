package org.kjh.mypracticeprojects.network

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.kjh.mypracticeprojects.model.DataResponse
import org.kjh.mypracticeprojects.model.LocationItem
import org.kjh.mypracticeprojects.model.LocationResponse
import org.kjh.mypracticeprojects.model.UserModel
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
     *  [POST] Sign Up.
     *
     ***************************************************/
    @POST("user")
    suspend fun reqSignUp(
        @Query("email") email: String,
        @Query("pw"   ) pw   : String,
    ): DataResponse


    /***************************************************
     *
     *  [GET] Duplicate Check for Email.
     *
     ***************************************************/
    @GET("user/duplicate")
    suspend fun reqValidateEmail(
        @Query("email") email: String
    ): DataResponse


    /***************************************************
     *
     *  [GET] User Data.
     *
     ***************************************************/
    @GET("user")
    suspend fun reqUser(
        @Query("email") email: String
    ): UserModel


    /***************************************************
     *
     * [GET] Login.
     *
     ***************************************************/
    @GET("user/login")
    suspend fun reqLogin(
        @Query("email") email: String,
        @Query("pw") pw: String,
    ): DataResponse


    /***************************************************
     *
     * [POST] Upload Post.
     *
     ***************************************************/
    @Multipart
    @POST("user/upload")
    suspend fun uploadPost(
        @Part file: MultipartBody.Part,
        @Query("email"          ) email: String,
        @Query("content"        ) content: String,
        @Query("address_name"   ) address_name: String,
        @Query("category_name"  ) category_name: String,
        @Query("phone"          ) phone: String,
        @Query("place_name"     ) place_name: String,
        @Query("place_url"      ) place_url: String,
        @Query("x"              ) x: String,
        @Query("y"              ) y: String,
        @Query("road_address_name"  ) road_address_name: String,
        @Query("category_group_code") category_group_code: String,
        @Query("category_group_name") category_group_name: String,
    ): UserModel



}