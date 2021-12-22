package com.kang.resume.bean

import com.google.gson.annotations.SerializedName

/**
 * 类描述：
 * author:kanghuicong
 */
data class ResumeInfoBean(
    @SerializedName("accountId") var accountId: Int? = null,
    @SerializedName("createTime") var createTime: String? = null,
    @SerializedName("resumeId") var resumeId: Int? = null,
    @SerializedName("resumeCover") var resumeCover: Int? = null,
    @SerializedName("resumeName") var resumeName: String? = "",
    @SerializedName("selfEvaluation") var selfEvaluation: String? = "",
    @SerializedName("baseInfo") var baseInfo: BaseInfoBean? = null,
    @SerializedName("certificates") var certificates: List<CertificateBean>? = null,
    @SerializedName("educations") var educations: List<EducationBean>? = null,
    @SerializedName("hobbies") var hobbies: List<HobbyBean>? = null,
    @SerializedName("jobIntention") var jobIntention: JobIntentionBean? = null,
    @SerializedName("projects") var projects: List<ProjectBean>? = null,
    @SerializedName("skills") var skills: List<SkillBean>? = null,
    @SerializedName("workExperiences") var workExperiences: List<WorkExperienceBean>? = null,
)

data class BaseInfoBean(
    @SerializedName("id") var id: Int?,
    @SerializedName("resumeId") var resumeId: Int?,
    @SerializedName("address") var address: String = "",
    @SerializedName("avatar") var avatar: String = "",
    @SerializedName("birthday") var birthday: String = "",
    @SerializedName("email") var email: String = "",
    @SerializedName("gender") var gender: String = "",
    @SerializedName("height") var height: String = "",
    @SerializedName("weight") var weight: String = "",
    @SerializedName("marryStatus") var marryStatus: String = "",
    @SerializedName("name") var name: String = "",
    @SerializedName("nation") var nation: String = "",
    @SerializedName("phone") var phone: String = "",
    @SerializedName("politicalStatus") var politicalStatus: String = "",
    @SerializedName("province") var province: String = "",
    @SerializedName("startWorkTime") var startWorkTime: String = ""
)

data class CertificateBean(
    @SerializedName("certificateId") var certificateId: Int,
    @SerializedName("resumeId") var resumeId: Int,
    @SerializedName("certificate") var certificate: String,
)

data class EducationBean(
    @SerializedName("educationId") var educationId: Int?,
    @SerializedName("resumeId") var resumeId: Int?,
    @SerializedName("school") var school: String = "",
    @SerializedName("major") var major: String = "",
    @SerializedName("record") var record: String = "",
    @SerializedName("startTime") var startTime: String = "",
    @SerializedName("endTime") var endTime: String = "",
    @SerializedName("experience") var experience: String = "",
)

data class HobbyBean(
    @SerializedName("hobbyId") var hobbyId: Int,
    @SerializedName("resumeId") var resumeId: Int,
    @SerializedName("hobby") var hobby: String,
)

data class JobIntentionBean(
    @SerializedName("id") var id: Int?,
    @SerializedName("resumeId") var resumeId: Int?,
    @SerializedName("city") var city: String = "",
    @SerializedName("entryTime") var entryTime: String = "",
    @SerializedName("position") var position: String = "",
    @SerializedName("salary") var salary: String = "",
)

data class ProjectBean(
    @SerializedName("projectId") var projectId: Int,
    @SerializedName("resumeId") var resumeId: Int,
    @SerializedName("description") var description: String,
    @SerializedName("endTime") var endTime: String,
    @SerializedName("projectName") var projectName: String,
    @SerializedName("role") var role: String,
    @SerializedName("startTime") var startTime: String,
    @SerializedName("url") var url: String,
)

data class SkillBean(

    @SerializedName("skillId") var skillId: Int,
    @SerializedName("resumeId") var resumeId: Int,
    @SerializedName("skillName") var skillName: String,
    @SerializedName("proficiency") var proficiency: String,
    @SerializedName("proficiencyValue") var proficiencyValue: Int,
)

data class WorkExperienceBean(

    @SerializedName("experienceId") var experienceId: Int,
    @SerializedName("resumeId") var resumeId: Int,
    @SerializedName("company") var company: String,
    @SerializedName("endTime") var endTime: String,
    @SerializedName("position") var position: String,
    @SerializedName("startTime") var startTime: String,
    @SerializedName("workContent") var workContent: String,
)