package test.ahmed.uc.ui.home

import android.annotation.SuppressLint
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.home_fragment.view.*
import kotlinx.android.synthetic.main.network_layout.view.*
import test.ahmed.uc.R
import test.ahmed.uc.helper.HomeAdapter
import test.ahmed.uc.model.Result
import test.ahmed.uc.repository.HomeRepository
import test.ahmed.uc.viewModel.HomeViewModel

@AndroidEntryPoint
class HomeFragment : Fragment(), View.OnClickListener {
    private val TAG: String = HomeFragment::class.java.simpleName
    private lateinit var homeAdapter: HomeAdapter
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun init() {
        homeAdapter = HomeAdapter()
        dataRV.apply {
            layoutManager =
                GridLayoutManager(activity, 1, GridLayoutManager.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
            dataRV.adapter = homeAdapter

        }

        swipe.setOnRefreshListener() {
            viewModel.checkNetwork(requireActivity())
        }
        viewModel.checkNetwork(requireActivity())
        networkLin.retryBtn.setOnClickListener(this)
        listenOnLiveData()
    }

    private fun listenOnLiveData() {
        viewModel.resultMLD.observe(requireActivity(), { result ->
            when (result) {

                "noInternetConnection" -> {
                    networkLin.visibility = View.VISIBLE
                    dataRV.visibility = View.GONE
                    swipe.isEnabled = false

                }
                "isInternetPresent" -> {
                    networkLin.visibility = View.GONE
                    dataRV.visibility = View.VISIBLE
                    swipe.isEnabled = true
                    collectData()
                }
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun collectData() {
        swipe.isRefreshing = true
        viewModel.getData()
        viewModel.MLD.observe(requireActivity(), {
            Log.d(
                TAG,
                "testTag getData:  vm getData : ${it.size}"
            )

            homeAdapter.setList(it as ArrayList<Result>)
            homeAdapter.notifyDataSetChanged()
            swipe.isRefreshing = false

        })
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onClick(view: View?) {
        if (networkLin.retryBtn == view) {
            viewModel.checkNetwork(requireActivity())
        }
    }

}