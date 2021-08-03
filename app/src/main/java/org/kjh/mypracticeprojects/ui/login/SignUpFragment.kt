package org.kjh.mypracticeprojects.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.kjh.mypracticeprojects.R
import org.kjh.mypracticeprojects.databinding.FragmentSignUpBinding
import org.kjh.mypracticeprojects.model.DataResponse
import org.kjh.mypracticeprojects.util.DataState


@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private val viewModel: SignUpViewModel by viewModels()
    private lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_sign_up,
            container,
            false
        )
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.dataState.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is DataState.Success<DataResponse> -> {
                    findNavController().navigate(R.id.next_action)
                    Toast.makeText(activity, getString(R.string.success_sign_up), Toast.LENGTH_SHORT).show()
                }

                is DataState.Error -> {
                    Toast.makeText(activity, dataState.exception.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}