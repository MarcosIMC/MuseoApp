package com.example.museoapp.ui.information

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel

class InformationViewModel : ViewModel() {

    fun openRRSS(rrss: InformationFragment.RRSS){
        when(rrss){
            InformationFragment.RRSS.TWITTER -> Log.i(TAG, "Se lanza Twitter")

            InformationFragment.RRSS.FACEBOOK -> Log.i(TAG, "Se lanza Facebook")

            InformationFragment.RRSS.INSTRAGRAM -> Log.i(TAG, "Se lanza Instagram")

            InformationFragment.RRSS.PINTEREST -> Log.i(TAG, "Se lanza Pinterest")
        }
    }


}