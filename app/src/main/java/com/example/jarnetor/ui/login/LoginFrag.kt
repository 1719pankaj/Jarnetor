package com.example.jarnetor.ui.login

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.jarnetor.R
import com.example.jarnetor.databinding.FragmentLoginBinding
import com.example.jarnetor.vo.Status
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.view.*

class LoginFrag : Fragment() {

    companion object {
        private const val RC_SIGN_IN = 16
    }

    private lateinit var mAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private val loginViewModel by viewModels<LoginViewModel>()

    private var binding: FragmentLoginBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

        mAuth = FirebaseAuth.getInstance()

        binding?.signInBtn?.setOnClickListener { signIn() }

        initiateObserver()
    }

    private fun initiateObserver() {
        Log.i("TAGGER", "initiateObserver: ")
        loginViewModel.signInSuccess.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAGGER", "signInWithCredential:success")
                    findNavController().navigate(R.id.action_loginFrag2_to_onBoarding)
                }
                Status.ERROR -> {
                    // If sign in fails, display a message to the user.
                    Log.w("TAGGER", "signInWithCredential:failure ${it.message}")
                }
                Status.LOADING -> {
                    //should add loading here
                    Log.i("TAGGER", "initiateObserver: loading")
                }
            }
        })
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startForResult.launch(signInIntent)
    }

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                val exception = task.exception
                if (task.isSuccessful) {
                    try {
                        // Google Sign In was successful, authenticate with Firebase
                        val account = task.getResult(ApiException::class.java)!!
                        Log.d("TAGGER", "firebaseAuthWithGoogle:" + account.id)
                        firebaseAuthWithGoogle(account.idToken!!)
                    } catch (e: ApiException) {
                        // Google Sign In failed, update UI appropriately
                        Log.w("TAGGER", "Google sign in failed", e)
                    }
                } else {
                    Log.w("TAGGER", exception.toString())
                }
            } else {
                Log.d("TAGGER", "result code is not Activity.RESULT_OK but ${result.resultCode}")
            }
        }

    private fun firebaseAuthWithGoogle(idToken: String) {
        loginViewModel.firebaseAuthWithGoogle(mAuth, idToken)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}