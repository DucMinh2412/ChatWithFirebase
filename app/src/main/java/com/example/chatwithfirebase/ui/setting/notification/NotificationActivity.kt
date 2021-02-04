package com.example.chatwithfirebase.ui.setting.notification

import android.os.Bundle
import android.util.Log.e
import androidx.lifecycle.ViewModelProvider
import com.example.chatwithfirebase.BR
import com.example.chatwithfirebase.R
import com.example.chatwithfirebase.base.BaseActivityGradient
import com.example.chatwithfirebase.databinding.ActivityNotificationBinding
import com.example.chatwithfirebase.di.ViewModelFactory
import com.google.firebase.messaging.FirebaseMessaging
import javax.inject.Inject

class NotificationActivity : BaseActivityGradient<ActivityNotificationBinding, NotificationViewModel>() {

    @Inject
    lateinit var factory: ViewModelFactory

    @Inject
    lateinit var firebaseMessaging: FirebaseMessaging

    private lateinit var notificationViewModel: NotificationViewModel

    override fun getViewModel(): NotificationViewModel {
        notificationViewModel = ViewModelProvider(this, factory).get(NotificationViewModel::class.java)
        return notificationViewModel
    }

    override fun getLayoutId(): Int = R.layout.activity_notification

    override fun getBindingVariable(): Int = BR.notificationViewModel

    override fun updateUI(savedInstanceState: Bundle?) {

        binding.imgBack.setOnClickListener {
            onBackPressed()
        }

        binding.setCheckToggle{ _, isChecked -> notificationViewModel.isChecked(isChecked) }
        notificationViewModel.setChecked()
        notificationViewModel.uiEventLiveData.observe(this,{
            when(it){
                NotificationViewModel.CHECKED-> onNotification()
                    NotificationViewModel.NOT_CHECKED -> offNotification()
            }
        })

    }

    private fun onNotification(){
        firebaseMessaging.subscribeToTopic("/topics/${notificationViewModel.getCurrentUserId()}")
    }

    private fun offNotification() {
        firebaseMessaging.unsubscribeFromTopic("/topics/${notificationViewModel.getCurrentUserId()}")
    }
}