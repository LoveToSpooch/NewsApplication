package cheers.lovetospooch.appnews.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import cheers.lovetospooch.appnews.databinding.FragmentMainBinding
import cheers.lovetospooch.appnews.models.NewsResponse
import cheers.lovetospooch.appnews.ui.adapters.NewsAdapter
import cheers.lovetospooch.appnews.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*

@AndroidEntryPoint
class MainFragment: Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val mBinding get() = _binding!!

    lateinit var newsAdapter: NewsAdapter

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        viewModel.newsLiveData.observe(viewLifecycleOwner) { responce ->
            when(responce) {
                is Resource.Success -> {
                    progressBarMain.visibility = View.INVISIBLE
                    responce.data?.let {
                        newsAdapter.differ.submitList(it.articles)
                    }

                }

                is Resource.Error -> {
                    progressBarMain.visibility = View.INVISIBLE
                    responce.data?.let {
                        Log.e("Error", "MainFragment error: ${it}")
                    }

                }

                is Resource.Loading -> {
                    progressBarMain.visibility = View.VISIBLE

                }
            }

        }
    }

    private fun initAdapter() {
        newsAdapter = NewsAdapter()
        mainPageRecyclerView.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}