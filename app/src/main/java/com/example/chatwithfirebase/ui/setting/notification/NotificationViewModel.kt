package com.example.chatwithfirebase.ui.setting.notification

import android.util.Log.e
import androidx.lifecycle.MutableLiveData
import com.example.chatwithfirebase.base.BaseViewModel
import javax.inject.Inject

class NotificationViewModel @Inject constructor() : BaseViewModel() {

    val statusSwitch = MutableLiveData<Boolean>()

    companion object{
       const val CHECKED = 10
        const val NOT_CHECKED = 9
    }

    fun isChecked(status:Boolean){
        if(status){
            e("s",status.toString())
            uiEventLiveData.value = CHECKED
            sharedPreferencesManager.saveChecked(status)
        }

        else{
            e("s",status.toString())
            uiEventLiveData.value = NOT_CHECKED
            sharedPreferencesManager.saveChecked(status)
        }
    }

    fun setChecked(){
        statusSwitch.value = sharedPreferencesManager.getChecked()==true
    }

    fun getCurrentUserId(): String{
        return firebaseDataRepository.getCurrentUserId()
    }
}