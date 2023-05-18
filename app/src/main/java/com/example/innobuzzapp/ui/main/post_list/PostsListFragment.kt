package com.example.innobuzzapp.ui.main.post_list

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.innobuzzapp.R
import com.example.innobuzzapp.adapters.PostListAdapter
import com.example.innobuzzapp.base.BaseFragment
import com.example.innobuzzapp.databinding.FragmentPostsListBinding
import com.example.innobuzzapp.listeners.PostListClickListener
import com.example.innobuzzapp.local_db.entity.PostEntity
import com.example.innobuzzapp.utils.NetworkUtil
import com.example.innobuzzapp.utils.State
import com.example.innobuzzapp.utils.makeGone
import com.example.innobuzzapp.utils.makeVisible
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@SuppressLint("NotifyDataSetChanged")
@AndroidEntryPoint
class PostsListFragment : BaseFragment<PostListsViewModel>(), PostListClickListener {
    private var _binding: FragmentPostsListBinding? = null
    private val binding get() = _binding!!
    private lateinit var postListAdapter: PostListAdapter

    @Inject
    lateinit var networkUtil: NetworkUtil
    override fun setViewModel(): PostListsViewModel {
        return ViewModelProvider(this)[PostListsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initObservers()
        initListeners()
        getDataFromLocalStorage()
    }

    private fun getDataFromLocalStorage() {
        viewModel.getPostsFromDatabase()
    }

    private fun initListeners() {
        with(binding) {
            errorPostIcd.retryBtn.setOnClickListener {
                fetchDataFromServer()
            }
            noPostIcd.retryBtn.setOnClickListener {
                fetchDataFromServer()
            }
            refreshTv.setOnClickListener {
                fetchDataFromServer(true)
            }
            permitServiceTv.setOnClickListener {
                startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
            }
        }
    }

    private fun fetchDataFromServer(deleteAllData: Boolean = false) {
        if (networkUtil.hasNetwork()) {
            viewModel.getPostsFromServer(deleteAllData)
        } else {
            with(binding) {
                shimmerPostIcd.root.stopShimmer()
                errorPostIcd.root.makeVisible()
                errorPostIcd.errorDescriptionTv.text = getString(R.string.no_internet_connection)
                shimmerPostIcd.root.makeGone()
                postRv.makeGone()
                noPostIcd.root.makeGone()
                refreshTv.makeGone()
                permitServiceTv.makeGone()
            }
        }
    }

    private fun initObservers() {
        observePostListFromServer()
        observePostListFromDatabase()
    }

    private fun observePostListFromDatabase() {
        viewModel.postListFromDbLiveData.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                with(binding) {
                    when (it) {
                        is State.Loading -> {
                            shimmerPostIcd.root.makeVisible()
                            errorPostIcd.root.makeGone()
                            shimmerPostIcd.root.startShimmer()
                            postRv.makeGone()
                            noPostIcd.root.makeGone()
                            refreshTv.makeGone()
                            permitServiceTv.makeGone()
                        }

                        is State.Success -> {
                            shimmerPostIcd.root.stopShimmer()
                            errorPostIcd.root.makeGone()
                            shimmerPostIcd.root.makeGone()
                            noPostIcd.root.makeGone()
                            postRv.makeVisible()
                            refreshTv.makeVisible()
                            permitServiceTv.makeVisible()
                            if (viewModel.postList.isEmpty()) {
                                fetchDataFromServer()
                            } else {
                                postListAdapter.notifyDataSetChanged()
                            }
                        }

                        is State.Error -> {
                            //no need to handle it ,it will never come into this scope
                        }
                    }
                }
            }
        }
    }

    private fun observePostListFromServer() {
        viewModel.postListFromServerLiveData.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                with(binding) {
                    when (it) {
                        is State.Loading -> {
                            shimmerPostIcd.root.makeVisible()
                            errorPostIcd.root.makeGone()
                            shimmerPostIcd.root.startShimmer()
                            postRv.makeGone()
                            noPostIcd.root.makeGone()
                            refreshTv.makeGone()
                            permitServiceTv.makeGone()
                        }

                        is State.Success -> {
                            shimmerPostIcd.root.stopShimmer()
                            errorPostIcd.root.makeGone()
                            shimmerPostIcd.root.makeGone()
                            postRv.makeVisible()
                            noPostIcd.root.makeGone()
                            refreshTv.makeVisible()
                            permitServiceTv.makeVisible()
                            if (viewModel.postList.isEmpty()) {
                                noPostIcd.root.makeVisible()
                            }
                            postListAdapter.notifyDataSetChanged()
                        }

                        is State.Error -> {
                            shimmerPostIcd.root.stopShimmer()
                            errorPostIcd.root.makeVisible()
                            permitServiceTv.makeGone()
                            errorPostIcd.errorDescriptionTv.text = it.errorMessage
                            shimmerPostIcd.root.makeGone()
                            postRv.makeGone()
                            noPostIcd.root.makeGone()
                            refreshTv.makeGone()
                        }
                    }
                }
            }
        }
    }


    private fun initUi() {
        with(binding) {
            toolbar.titleTv.text = getString(R.string.posts)
            postListAdapter = PostListAdapter(viewModel.postList, this@PostsListFragment)
            postRv.adapter = postListAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onPostClicked(postEntity: PostEntity) {
        val action =
            PostsListFragmentDirections.actionPostsListFragmentToPostDetailsFragment(postEntity)
        findNavController().navigate(action)
    }

}