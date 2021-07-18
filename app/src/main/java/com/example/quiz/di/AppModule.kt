package com.example.quiz.di

import android.content.Context
import com.example.quiz.data.local.AppDatabase
import com.example.quiz.data.local.QuestionAnswerDao
import com.example.quiz.data.local.QuestionDao
import com.example.quiz.data.remote.QuestionRemoteDataSource
import com.example.quiz.data.remote.QuestionService
import com.example.quiz.data.repository.QuestionAnswereRepository
import com.example.quiz.data.repository.QuestionRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl("https://assessed-da.s3-ap-southeast-1.amazonaws.com/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideCharacterService(retrofit: Retrofit): QuestionService =
        retrofit.create(QuestionService::class.java)

    @Singleton
    @Provides
    fun provideCharacterRemoteDataSource(questionService: QuestionService) =
        QuestionRemoteDataSource(questionService)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideQuestionDao(db: AppDatabase) = db.questionDao()

    @Singleton
    @Provides
    fun provideRepository(
        remoteDataSource: QuestionRemoteDataSource,
        localDataSource: QuestionDao
    ) =
        QuestionRepository(remoteDataSource, localDataSource)

    @Singleton
    @Provides
    fun provideQuestionAnswerDao(db: AppDatabase) = db.questionAnswerDao()

    @Singleton
    @Provides
    fun provideQuestionAnswereRepository(localDataSource: QuestionAnswerDao) =
        QuestionAnswereRepository(localDataSource)
}