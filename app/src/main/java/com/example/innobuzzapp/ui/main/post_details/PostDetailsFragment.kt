package com.example.innobuzzapp.ui.main.post_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.innobuzzapp.R
import com.example.innobuzzapp.databinding.FragmentPostDetailsBinding
import com.example.innobuzzapp.local_db.entity.PostEntity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostDetailsFragment : Fragment() {
    private var _binding: FragmentPostDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var postDetails: PostEntity
    private val args by navArgs<PostDetailsFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postDetails = args.postDetails
        initUi()
    }

    private fun initUi() {
        with(binding) {
            toolbar.titleTv.text = getString(R.string.details)
            postTitleTv.text = postDetails.title
            postDescriptionTv.text = postDetails.body
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}