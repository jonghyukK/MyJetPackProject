package org.kjh.mypracticeprojects.network

import okhttp3.MultipartBody
import org.kjh.mypracticeprojects.model.PostModel
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
     *  [POST] Sign Up.
     *
     ***************************************************/
    @POST("user")
    suspend fun reqSignUp(
        @Query("email") email: String,
        @Query("pw"   ) pw   : String,
        @Query("token") token: String,
    ): UserResponse


    /***************************************************
     *
     *  [GET] Duplicate Check for Email.
     *
     ***************************************************/
    @GET("user/duplicate")
    suspend fun reqValidateEmail(
        @Query("email") email: String
    ): UserResponse


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
        @Query("pw"   ) pw   : String,
        @Query("token") token: String,
    ): UserResponse


    /***************************************************
     *
     * [POST] Upload Post.
     *
     ***************************************************/
    @Multipart
    @POST("user/upload")
    suspend fun uploadPost(
        @Part file: List<MultipartBody.Part>,
        @Query("email"          ) email         : String,
        @Query("content"        ) content       : String,
        @Query("address_name"   ) address_name  : String,
        @Query("category_name"  ) category_name : String,
        @Query("phone"          ) phone         : String,
        @Query("place_name"     ) place_name    : String,
        @Query("place_url"      ) place_url     : String,
        @Query("x"              ) x             : String,
        @Query("y"              ) y             : String,
        @Query("road_address_name"  ) road_address_name  : String,
        @Query("category_group_code") category_group_code: String,
        @Query("category_group_name") category_group_name: String,
    ): UserModel


    /***************************************************
     *
     * [PUT] Update User.
     *
     ***************************************************/
    @Multipart
    @PUT("user")
    suspend fun updateUser(
        @Part file: MultipartBody.Part,
        @Query("email") email: String,
    ): UserModel

    /***************************************************
     *
     * [PUT] Update User BookMark List.
     *
     ***************************************************/
    @PUT("user/bookmark")
    suspend fun updateUserBookMark(
        @Query("email"    ) email    : String,
        @Query("postId"   ) postId   : Int,
        @Query("placeName") placeName: String
    ): UserModel


    /***************************************************
     *
     * [DELETE] Delete Post.
     *
     ***************************************************/
    @DELETE("post")
    suspend fun deletePost(
        @Query("postId") postId: Int,
        @Query("email" ) email : String,
    ): UserModel

    /***************************************************
     *
     * [GET] Get Posts.
     *
     ***************************************************/
    @GET("post")
    suspend fun getPosts(
        @Query("city"     ) cityName : String? = null,
        @Query("placeName") placeName: String? = null
    ): List<PostModel>

}