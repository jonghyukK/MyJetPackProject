package org.kjh.mypracticeprojects.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * MyPracticeProjects
 * Class: BaseActivity
 * Created by mac on 2021/08/09.
 *
 * Description:
 */
abstract class BaseActivity<B: ViewDataBinding>(
    private val layoutId: Int
): AppCompatActivity() {
    protected lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.setLifecycleOwner { this.lifecycle }
    }
}