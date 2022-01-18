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
    @POST("/resume/delEducation/{educationId}")
    suspend fun delEducation(@Path("educationId") educationId: Int): ApiResponse<Any>

    //新增或编辑工作经历
    @POST("/resume/saveOrUpdateWorkExperience")
    suspend fun saveOrUpdateWorkExperience(@Body workExperienceBean: WorkExperienceBean): ApiResponse<Any>

    //删除工作经历
    @POST("/resume/delWorkExperience/{experienceId}")
    suspend fun delWorkExperience(@Path("experienceId") experienceId: Int): ApiResponse<Any>

    //新增或编辑项目
    @POST("/resume/saveOrUpdateProject")
    suspend fun saveOrUpdateProject(@Body projectBean: ProjectBean): ApiResponse<Any>

    //删除项目
    @POST("/resume/delProject/{projectId}")
    suspend fun delProject(@Path("projectId") projectId: Int): ApiResponse<Any>

    //新增或编辑技能
    @POST("/resume/saveOrUpdateSkill")
    suspend fun saveOrUpdateSkill(@Body skillBean: SkillBean): ApiResponse<Any>

    //删除技能
    @POST("/resume/delSkill/{skillId}")
    suspend fun delSkill(@Path("skillId") skillId: Int): ApiResponse<Any>

    //新增或编辑证书
    @POST("/resume/saveOrUpdateCertificate")
    suspend fun saveOrUpdateCertificate(@Body certificateBean: CertificateBean): ApiResponse<Any>

    //删除证书
    @POST("/resume/delCertificate/{certificateId}")
    suspend fun delCertificate(@Path("certificateId") certificateId: Int): ApiResponse<Any>

    //新增或编辑爱好
    @POST("/resume/saveOrUpdateHobby")
    suspend fun saveOrUpdateHobby(@Body hobbyBean: HobbyBean): ApiResponse<Any>

    //删除爱好
    @POST("/resume/delHobby/{hobbyId}")
    suspend fun delHobby(@Path("hobbyId") hobbyId: Int): ApiResponse<Any>

    //新增或编辑自我评价
    @POST("/resume/saveOrUpdateSelfEvaluation")
    suspend fun saveOrUpdateSelfEvaluation(@Body selfEvaluationBean: SelfEvaluationBean): ApiResponse<Any>

    //删除自我评价
    @POST("/resume/delSelfEvaluation/{resumeId}")
    suspend fun delSelfEvaluation(@Path("resumeId") resumeId: Int): ApiResponse<Any>

    //导出-->判断是否有权限导出
    @POST("/export/{resumeId}")
    suspend fun export(@Path("resumeId") resumeId: Int): ApiResponse<Any>


}