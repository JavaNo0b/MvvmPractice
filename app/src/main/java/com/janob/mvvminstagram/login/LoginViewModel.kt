package com.janob.mvvminstagram.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {
    var id : MutableLiveData<String> = MutableLiveData("")
    var password : MutableLiveData<String> = MutableLiveData("")

    private var auth = FirebaseAuth.getInstance()

    var showInputNumberActivity : MutableLiveData<Boolean> = MutableLiveData(false)
    var showFindIdActivity : MutableLiveData<Boolean> = MutableLiveData(false)


    fun loginWithSignupEmail(){
        println("Email")
        auth.createUserWithEmailAndPassword(id.value.toString(), password.value.toString()).addOnCompleteListener {
            if(it.isSuccessful){
                showInputNumberActivity.value = true
            } else {
                //아이디가 존재할 경우
            }
        }
    }
}