package com.kang.resume.http

import com.kang.resume.bean.BaseInfoBean
import com.kang.resume.bean.ResumeInfoBean
import com.kang.resume.bean.UserInfoBean
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

}