package com.example.jarnetor.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jarnetor.vo.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val _signInSuccess = MutableLiveData<Resource<Boolean>>()
    val signInSuccess: LiveData<Resource<Boolean>> = _signInSuccess

    fun firebaseAuthWithGoogle(mAuth: FirebaseAuth, idToken: String) {
        _signInSuccess.postValue(Resource.loading(null))
        try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getApplication()) { task ->
                    if (task.isSuccessful) {
                        _signInSuccess.postValue(Resource.success(true))
                    } else {
                        _signInSuccess.postValue(Resource.success(false))
                    }
                }
        } catch (e: Exception) {
            _signInSuccess.postValue(Resource.error("${e.message}", null))
        }
    }

}