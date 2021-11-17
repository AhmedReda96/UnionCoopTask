package test.ahmed.uc.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import test.ahmed.uc.api.Service
import test.ahmed.uc.model.MainResponse
import test.ahmed.uc.model.Result
import javax.inject.Inject

class HomeRepository @Inject constructor(private val service: Service) {
    private val TAG: String = HomeRepository::class.java.simpleName
    private lateinit var list: MutableLiveData<List<Result>>
    private lateinit var parentJob: Job
    private lateinit var scope: CoroutineScope

    init {
        parentJob = Job()
        scope = CoroutineScope(Dispatchers.Main + parentJob)
        list = MutableLiveData<List<Result>>()
    }

    fun getData(): MutableLiveData<List<Result>> {
        scope.launch {
            val response = service.getData(
                "kjWL3IMsOTCUgfZWnsq9HJix6bGqMjGS",
                "c5555ca5-304f-4f7a-918f-bc6963b55c5e",
                "51Kjxaeudnnadb060520190ADMIN3360520190345@ucs.ae",
                "application/json",
                "en",
                "IOS/Android"
            )
            if (response?.isSuccessful!!) {
                Log.d(
                    TAG,
                    "testTag getData: response isSuccessful : ${response.body()?.results?.size}"
                )
                list.value = response.body()?.results
                parentJob.cancel()
            } else {

                Log.d(
                    TAG,
                    "testTag getData: response isNotSuccessful ${
                        response.message().toString()
                    }"
                )
                parentJob.cancel()
            }
        }
        return list

    }
}