package com.janob.mvvminstagram.login

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.janob.mvvminstagram.R

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    var id : MutableLiveData<String> = MutableLiveData("")
    var password : MutableLiveData<String> = MutableLiveData("")

    private var auth = FirebaseAuth.getInstance()

    var showInputNumberActivity : MutableLiveData<Boolean> = MutableLiveData(false)
    var showFindIdActivity : MutableLiveData<Boolean> = MutableLiveData(false)

    val context = getApplication<Application>().applicationContext

    var googleSignInClient : GoogleSignInClient

    init {

        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(context, gso)
    }

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

    fun loginGoogle(view : View){
        var i = googleSignInClient.signInIntent
        //googleSignInClient.signInIntent 와 googleLoginResult을 연결해주기 위해 (view.context as? LoginActivity)의 형태로 LoginActivity에 접근
        (view.context as? LoginActivity)?.googleLoginResult?.launch(i)
    }
    fun firebaseAuthWithGoogle(idToken : String?){
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if(it.isSuccessful){
                showInputNumberActivity.value = true
            } else {
                //아이디가 존재할 경우
            }
        }
    }
}