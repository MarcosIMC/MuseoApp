package com.example.museoapp.ui.information

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InformationViewModel : ViewModel() {
    var action = MutableLiveData<Int>()

    fun openRRSS(rrss: InformationFragment.RRSS){
        when(rrss){
            InformationFragment.RRSS.TWITTER -> action.value = 0

            InformationFragment.RRSS.FACEBOOK -> action.value = 1

            InformationFragment.RRSS.INSTRAGRAM -> action.value = 2

            InformationFragment.RRSS.PINTEREST -> action.value = 3
        }
    }


}