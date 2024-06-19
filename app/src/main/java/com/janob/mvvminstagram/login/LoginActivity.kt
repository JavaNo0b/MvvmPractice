package com.janob.mvvminstagram.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.janob.mvvminstagram.R
import com.janob.mvvminstagram.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding : ActivityLoginBinding
    val loginViewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.viewModel = loginViewModel
        binding.activity = this
        binding.lifecycleOwner = this
        setObserve()
    }

    fun setObserve() {
        loginViewModel.showInputNumberActivity.observe(this){
            if(it){  //showInputNumberActivity가 true로 바꼈을 때
                finish()
                startActivity(Intent(this,InputNumberActivity::class.java))
            }
        }

        loginViewModel.showFindIdActivity.observe(this){
            if(it){  //showFindIdActivity가 true로 바꼈을 때
                finish()
                startActivity(Intent(this,FindIdActivity::class.java))
            }
        }
    }



    fun findId(){
        println("findId")
        loginViewModel.showFindIdActivity.value = true
    }

    // 구글로그인이 성공한 결과값 받는 함수
    // 이 함수를 뷰모델에 넣고 싶었으나 registerForActivityResult함수가 뷰모델에서는 사용할 수 없으므로 실패
    var googleLoginResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result ->

        val data = result.data
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        val account = task.getResult(ApiException::class.java)
         //로그인한 사용자 정보를 암호화한 값
        loginViewModel.firebaseAuthWithGoogle(account.idToken)
    }


}