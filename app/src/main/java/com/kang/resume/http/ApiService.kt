package com.kang.resume.http

import com.kang.resume.bean.*
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.http.*

/**
 * 类描述：
 * author:kanghuicong
 */
interface ApiService {

    //登录
    @FormUrlEncoded
    @POST("/account/login")
    suspend fun login(
        @Field("account") account: String,
        @Field("password") password: String
    ): ApiResponse<String>

    //注册
    @FormUrlEncoded
    @POST("/account/register")
    suspend fun register(
        @Field("account") account: String,
        @Field("password") password: String,
        @Field("repeatPassword") repeatPassword: String
    ): ApiResponse<Any>

    //查询用户详情
    @GET("/account/queryAccountInfo")
    suspend fun queryAccountInfo(): ApiResponse<UserInfoBean>

    //查询用户详情
    @GET("/resume/queryResumeInfoList")
    suspend fun queryResumeInfoList(): ApiResponse<List<ResumeInfoBean>>

    //新增一份简历
    @POST("/resume/saveOrUpdateResume")
    suspend fun saveOrUpdateResume(): ApiResponse<Any>

    //删除一份简历
    @POST("/resume/delResume/{resumeId}")
    suspend fun delResume(@Path("resumeId") resumeId: Int): ApiResponse<Any>

    //新增或编辑简历用户基础信息
    @POST("/resume/saveOrUpdateBaseInfo")
    suspend fun saveOrUpdateBaseInfo(@Body baseInfoBean: BaseInfoBean): ApiResponse<Any>

    //删除用户基础信息
    @POST("/resume/delBaseInfo/{resumeId}")
    suspend fun delBaseInfo(@Path("resumeId") resumeId: Int): ApiResponse<Any>

    //新增或编辑职位
    @POST("/resume/saveOrUpdateJobIntention")
    suspend fun saveOrUpdateJobIntention(@Body jobIntentionBean: JobIntentionBean): ApiResponse<Any>

    //删除职位
    @POST("/resume/delJobIntention/{resumeId}")
    suspend fun delJobIntention(@Path("resumeId") resumeId: Int): ApiResponse<Any>


    //新增或编辑教育经历
    @POST("/resume/saveOrUpdateEducation")
    suspend fun saveOrUpdateEducation(@Body educationBean: EducationBean): ApiResponse<Any>

    //删除教育经历
    @POST("/resume/delCertificate/{educationId}")
    suspend fun delCertificate(@Path("educationId") educationId: Int): ApiResponse<Any>

}