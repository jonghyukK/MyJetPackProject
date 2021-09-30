package org.kjh.mypracticeprojects.ui.login

/**
 * MyPracticeProjects
 * Class: Validatestate
 * Created by mac on 2021/08/07.
 *
 * Description:
 */

const val ERROR_NOTHING_EMAIL        = "가입되지 않은 이메일입니다."
const val ERROR_INVALIDE_EMAIL       = "이메일 형식이 맞지 않습니다."
const val ERROR_DUPLICATED_EMAIL     = "이미 가입된 이메일입니다."
const val ERROR_PW_LESS_THAN_8       = "비밀번호는 8자리 이상 입력해주세요."
const val ERROR_PW_CONFIRM_NOT_MATCH = "비밀번호가 일치하지 않습니다."
const val ERROR_WRONG_PW             = "비밀번호가 맞지 않습니다."

enum class ValidateState(val errorMsg: String?) {
    INIT               (null),
    SUCCESS            (null),
    ERROR_PATTERN      (errorMsg = ERROR_INVALIDE_EMAIL),
    ERROR_DUPLICATED   (errorMsg = ERROR_DUPLICATED_EMAIL),
    ERROR_LENGTH       (errorMsg = ERROR_PW_LESS_THAN_8),
    ERROR_NOT_MATCH    (errorMsg = ERROR_PW_CONFIRM_NOT_MATCH),
    ERROR_EMAIL_NOTHING(errorMsg = ERROR_NOTHING_EMAIL),
    ERROR_PW           (errorMsg = ERROR_WRONG_PW);

    fun getError() = errorMsg
}