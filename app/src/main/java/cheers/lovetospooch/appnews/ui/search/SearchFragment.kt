package cheers.lovetospooch.appnews.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cheers.lovetospooch.appnews.R
import cheers.lovetospooch.appnews.databinding.FragmentSearchBinding

class SearchFragment: Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val mBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }
}