package cheers.lovetospooch.appnews.ui.search

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import cheers.lovetospooch.appnews.databinding.FragmentSearchBinding
import cheers.lovetospooch.appnews.ui.adapters.NewsAdapter
import cheers.lovetospooch.appnews.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment: Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val mBinding get() = _binding!!

    lateinit var newsAdapter: NewsAdapter

    private val viewModel by viewModels<SearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        var job: Job? = null
        editTextSearchNews.addTextChangedListener { text: Editable? ->
            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                text?.let {
                    if (it.toString().isNotEmpty()) {
                        viewModel.getSearchNews(query = it.toString())
                    }
                }
            }
        }

        viewModel.searchNewsLiveData.observe(viewLifecycleOwner) { responce ->
            when(responce) {
                is Resource.Success -> {
                    searchProgressbar.visibility = View.INVISIBLE
                    responce.data?.let {
                        newsAdapter.differ.submitList(it.articles)
                    }
                }

                is Resource.Error -> {
                    searchProgressbar.visibility = View.INVISIBLE
                    responce.data?.let {
                        Log.e("Error", "MainFragment error: ${it}")
                    }
                }

                is Resource.Loading -> {
                    searchProgressbar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun initAdapter() {
        newsAdapter = NewsAdapter()
        searchRecycler.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}