package org.kjh.mypracticeprojects.ui.login

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import org.kjh.mypracticeprojects.R

/**
 * MyPracticeProjects
 * Class: LoginActivity
 * Created by mac on 2021/07/21.
 *
 * Description:
 */
@AndroidEntryPoint
class LoginActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
}