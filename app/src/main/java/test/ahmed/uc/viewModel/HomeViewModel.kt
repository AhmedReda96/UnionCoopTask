package test.ahmed.uc.viewModel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import test.ahmed.uc.model.Result
import test.ahmed.uc.repository.HomeRepository
import test.ahmed.uc.utlis.checkNetwork
import javax.inject.Inject

@HiltViewModel

class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {
    private val TAG: String = HomeViewModel::class.java.simpleName
    var MLD = MutableLiveData<List<Result>>()
    var resultMLD = MutableLiveData<String>()

    @RequiresApi(Build.VERSION_CODES.M)
    fun checkNetwork(context: FragmentActivity) {
        if (!context.checkNetwork()) {
            resultMLD.value = "noInternetConnection"
            Log.d(
                TAG,
                "testTag checkNetwork: noInternetConnection"
            )

        } else {
            resultMLD.value = "isInternetPresent"
            Log.d(
                TAG,
                "testTag checkNetwork:  isInternetPresent"
            )
        }
    }


    fun getData() {
        repository.getData().also {
            MLD = it
        }

    }

}