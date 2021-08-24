package org.kjh.mypracticeprojects.network

import org.kjh.mypracticeprojects.model.LocationResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * MyPracticeProjects
 * Class: KaKaoApiService
 * Created by mac on 2021/08/23.
 *
 * Description:
 */
interface KaKaoApiService {

    /***************************************************
     *
     * [GET] search Location from "keyword.json"
     *
     ***************************************************/
    @GET("v2/local/search/keyword.json")
    suspend fun searchLocation(
        @Header("Authorization") key: String,
        @Query("query"         ) query: String
    ): LocationResponse
}