package com.example.museoapp.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.museoapp.model.FireBase.UserFireBase
import com.example.museoapp.model.UserModel

class UserDataViewModel : ViewModel(){
    val error = MutableLiveData<String?>()
    val userFireBase = UserFireBase()
    val userDatas = MutableLiveData<UserModel>()

    fun getUserDatas(id_user : String){
        userFireBase.getUserData(id_user, userDatas, error)
    }

    @JvmName("getUserDatas1")
    fun getUserDatas(): MutableLiveData<UserModel> {
        return userDatas
    }
}