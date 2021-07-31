package org.kjh.mypracticeprojects.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import org.kjh.mypracticeprojects.R

@AndroidEntryPoint
class MainActivity : AppCompatActivity()
//    BlogAdapter.BlogItemListener
{

//    private val viewModel: MainViewModel by viewModels()
//    private lateinit var adapter: BlogAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        setupRecyclerView()
//        subscribeObservers()
//        viewModel.setStateEvent(MainStateEvent.GetBlogEvents)
//
//        swipeRefreshLayout.setOnRefreshListener {
//            viewModel.setStateEvent(MainStateEvent.GetBlogEvents)
//        }
    }

    private fun subscribeObservers() {
//        viewModel.dataState.observe(this, { dataState ->
//            when (dataState) {
//                is DataState.Success<List<Blog>> -> {
//                    displayLoading(false)
////                    populateRecyclerView(dataState.data)
//                }
//
//                is DataState.Loading -> {
//                    displayLoading(true)
//                }
//
//                is DataState.Error -> {
//                    displayLoading(false)
//                    displayError(dataState.exception.message)
//                }
//            }
//        })
    }

//    private fun displayError(message: String?) {
//        if (message.isNullOrEmpty()) {
//            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
//        } else {
//            Toast.makeText(this, "Unknown error", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    private fun displayLoading(b: Boolean) {
//        swipeRefreshLayout.isRefreshing = b
//    }

//    private fun populateRecyclerView(data: List<Blog>) {
//        if (data.isNotEmpty())
//            adapter.setItems(ArrayList(data))
//    }

//    private fun setupRecyclerView() {
//        adapter = BlogAdapter(this)
//        blog_recyclerview.layoutManager = LinearLayoutManager(this)
//        blog_recyclerview.adapter = adapter
//    }
//
//    override fun onClickBlog(blogTitle: CharSequence) {
//        Toast.makeText(this, blogTitle, Toast.LENGTH_SHORT).show()
//    }
}