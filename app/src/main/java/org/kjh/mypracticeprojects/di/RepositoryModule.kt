package org.kjh.mypracticeprojects.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.kjh.mypracticeprojects.network.ApiService
import org.kjh.mypracticeprojects.network.KaKaoApiService
import org.kjh.mypracticeprojects.repository.PostRepository
import org.kjh.mypracticeprojects.repository.UserRepository
import javax.inject.Singleton

/**
 * MyPracticeProjects
 * Class: RepositoryModule
 * Created by mac on 2021/07/21.
 *
 * Description:
 */
@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    @Singleton
    @Provides
    fun provideUserRepository(
        apiService      : ApiService,
        kakaoApiService : KaKaoApiService
    ) = UserRepository(apiService, kakaoApiService)


    @Singleton
    @Provides
    fun providePostRepository(
        apiService: ApiService
    ) = PostRepository(apiService)
}