package com.example.chatwithfirebase.di

import android.util.Log
import android.util.Log.e
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.multidex.MultiDexApplication
import com.example.chatwithfirebase.base.manager.SharedPreferencesManager
import com.example.chatwithfirebase.data.repository.FirebaseNotificationRepository
import com.example.chatwithfirebase.data.repository.auth.FirebaseAuthRepository
import com.example.chatwithfirebase.data.repository.data.FirebaseDataRepository
import com.example.chatwithfirebase.di.component.DaggerAppComponent
import com.example.chatwithfirebase.di.rx.SchedulerProvider
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by Duc Minh
 */

class ChatWithFirebase : MultiDexApplication(), HasAndroidInjector, LifecycleObserver {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var schedulerProvider: SchedulerProvider

    @Inject
    lateinit var firebaseDataRepository: FirebaseDataRepository

    var compositeDisposable: CompositeDisposable = CompositeDisposable()


    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)

            ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {
        if(firebaseDataRepository.getCurrentUser()!=null) {
            compositeDisposable.add(
                firebaseDataRepository.updateStatusUser("offline")
                    .compose(schedulerProvider.ioToMainCompletableScheduler())
                    .subscribe()
            )
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() {
        if(firebaseDataRepository.getCurrentUser()!=null) {
            compositeDisposable.add(
                firebaseDataRepository.updateStatusUser("online")
                    .compose(schedulerProvider.ioToMainCompletableScheduler())
                    .subscribe()
            )
        }
    }

    override fun androidInjector(): AndroidInjector<Any>? {
        return dispatchingAndroidInjector
    }


}