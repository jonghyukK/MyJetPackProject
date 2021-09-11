package org.kjh.mypracticeprojects.ui.main.post

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.kjh.mypracticeprojects.R
import org.kjh.mypracticeprojects.databinding.FragmentPostBottomSheetBinding

/**
 * MyPracticeProjects
 * Class: PostBottomSheetFragment
 * Created by mac on 2021/09/01.
 *
 * Description:
 */
class PostBottomSheetFragment(
    private val listener: PostBottomSheetEventListener
): BottomSheetDialogFragment() {

    private lateinit var binding: FragmentPostBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post_bottom_sheet, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnPostRemove.setOnClickListener {
            listener.onClickDeletePost()
            dismiss()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        listener.onDismiss()
    }
}

interface PostBottomSheetEventListener {
    fun onClickDeletePost()
    fun onDismiss()
}