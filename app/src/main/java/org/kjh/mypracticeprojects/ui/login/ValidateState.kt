package org.kjh.mypracticeprojects.ui.login

import org.kjh.mypracticeprojects.*

/**
 * MyPracticeProjects
 * Class: Validatestate
 * Created by mac on 2021/08/07.
 *
 * Description:
 */
enum class ValidateState(val errorMsg: String?) {
    INIT            (null),
    SUCCESS         (null),
    ERROR_PATTERN   (errorMsg = ERROR_INVALIDE_EMAIL),
    ERROR_DUPLICATED(errorMsg = ERROR_DUPLICATED_EMAIL),
    ERROR_LENGTH    (errorMsg = ERROR_PW_LESS_THAN_8),
    ERROR_NOT_MATCH (errorMsg = ERROR_PW_CONFIRM_NOT_MATCH),
    ERROR_EMAIL_NOTHING(errorMsg = ERROR_NOTHING_EMAIL),
    ERROR_PW           (errorMsg = ERROR_WRONG_PW);

    fun getError() = errorMsg
}