package com.kang.resume.utils

import android.R.bool
import android.R.string
import java.util.regex.Matcher
import java.util.regex.Pattern


/**
 * 类描述：
 * author:kanghuicong
 */
object VerifyUtils {
    /// 邮箱正则
    private const val REGEX_EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*\$"

    /// 检查是否是手机号
    fun isEmpty(str: String?): Boolean {
        return str == null || str == ""
    }

    /// 检查是否是手机号
    fun verifyPhone(phone: String?): Boolean {
        return !(phone == null || phone == "" || phone.length != 11)
    }


    /// 检查是否是邮箱格式
    fun verifyEmail(email: String?): Boolean {

        return if (email == null || email == "") {
            false
        } else {
            val m: Matcher
            val p = Pattern.compile(REGEX_EMAIL)
            m = p.matcher(email)
            m.matches()
        }
    }

    /// 检查密码是否正确
    fun verifyPassword(password: String?): Boolean {
        return if (password == null || password == "") {
            false
        } else {
            password.length >= 6
        }
    }

    /// 检查验证码是否正确
    fun verifySendCode(code: String?): Boolean {
        return !(code == null || code == "")
    }

    fun verifyEmpty(str: String?): Boolean {
        return !(str == null || str == "")
    }
}