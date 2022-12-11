package com.example.museoapp.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.museoapp.MainViewActivity
import com.example.museoapp.R
import com.example.museoapp.model.FireBase.GalleryFireBase
import com.example.museoapp.model.GalleryModel
import com.google.zxing.integration.android.IntentIntegrator

class MainViewModel: ViewModel() {
    val galleryFireBase = GalleryFireBase()
    val list = MutableLiveData<MutableList<GalleryModel>>()

    fun initScanner(mainViewActivity: MainViewActivity) {
        val integrator = IntentIntegrator(mainViewActivity)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        integrator.setPrompt(mainViewActivity.getString(R.string.prompt_qr))
        integrator.setTorchEnabled(false)
        integrator.setBeepEnabled(true)
        integrator.initiateScan()
    }

    fun getElement(contents: String) {
        galleryFireBase.getItem(contents, list, null)
    }
}