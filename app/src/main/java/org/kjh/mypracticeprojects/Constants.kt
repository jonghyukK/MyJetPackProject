package org.kjh.mypracticeprojects

import android.util.Patterns
import android.widget.EditText

/**
 * MyPracticeProjects
 * Class: Constants
 * Created by mac on 2021/07/21.
 *
 * Description:
 */

const val BASE_API_URL       = "http://192.168.219.108:8080/"
const val BASE_KAKAO_API_URL = "https://dapi.kakao.com/"
const val KakaoRestAPI_KEY   = "KakaoAK 2d5a4fcef5ee358d99f97ae0a7083732"

const val ERROR_NOTHING_EMAIL        = "가입되지 않은 이메일입니다."
const val ERROR_INVALIDE_EMAIL       = "이메일 형식이 맞지 않습니다."
const val ERROR_DUPLICATED_EMAIL     = "이미 가입된 이메일입니다."
const val ERROR_PW_LESS_THAN_8       = "비밀번호는 8자리 이상 입력해주세요."
const val ERROR_PW_CONFIRM_NOT_MATCH = "비밀번호가 일치하지 않습니다."
const val ERROR_WRONG_PW             = "비밀번호가 맞지 않습니다."

val countryList = listOf("전국", "서울", "경기", "충북", "충남", "경북", "경남", "전북", "전남", "강원")

const val PREF_KEY_LOGIN_STATE = "LOGIN_STATE"
const val PREF_KEY_LOGIN_ID    = "LOGIN_ID"

enum class LoginState(val value: Int) {
    LOGOUT(0),
    LOGIN(1)
}


fun String.isValidPattern(): Boolean =
    Patterns.EMAIL_ADDRESS.matcher(this).matches()
