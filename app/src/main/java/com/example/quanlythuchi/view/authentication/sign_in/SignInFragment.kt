package com.example.quanlythuchi.view.authentication.sign_in

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.quanlythuchi.R
import com.example.quanlythuchi.base.BaseFragment
import com.example.quanlythuchi.base.KeyboardManager
import com.example.quanlythuchi.databinding.FragmentSignInBinding
import com.example.quanlythuchi.view.activity.authen.AuthenticationActivity
import com.example.quanlythuchi.view.activity.home.HomeActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding,SignInViewModel>(),SignInListener,
    Observer<KeyboardManager.KeyboardStatus>{
    override val viewModel: SignInViewModel by viewModels()
    override val layoutID: Int = R.layout.fragment_sign_in

    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    lateinit var googleSignInClient : GoogleSignInClient
    private var signInLauncher: ActivityResultLauncher<Intent>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.apply {
            listener = this@SignInFragment
            viewModel = this@SignInFragment.viewModel
        }
        this.getOwnerActivity<AuthenticationActivity>()
            ?.let { KeyboardManager.init(it).status()?.observeForever(this) }

        checkSignIn()
    }

    private fun checkSignIn() {
        context?.let {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(it.getString(R.string.client_id))
                .requestEmail().build()
            googleSignInClient = GoogleSignIn.getClient(it, gso)
        }
        signInLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val signInAccountTask: Task<GoogleSignInAccount> =
                    GoogleSignIn.getSignedInAccountFromIntent(data)
                if (signInAccountTask.isSuccessful) {
                    val s = "Google sign in successful"
                    displayToast(s)
                    // Initialize sign in account
                    try {
                        // Initialize sign in account
                        val googleSignInAccount = signInAccountTask.getResult(ApiException::class.java)
                        if (googleSignInAccount != null) {
                            val authCredential: AuthCredential = GoogleAuthProvider.getCredential(
                                googleSignInAccount.idToken, null
                            )
                            firebaseAuth.signInWithCredential(authCredential)
                                .addOnCompleteListener(this.requireActivity()) { task ->
                                    // Check condition
                                    if (task.isSuccessful)
                                        displayToast("Firebase authentication successful")
                                    else
                                        displayToast("Authentication Failed :" + task.exception?.message)
                                    val isNewsUser = task.result.additionalUserInfo?.isNewUser ?: false
                                    task.result.user?.uid
                                    var user : FirebaseUser? = FirebaseAuth.getInstance().currentUser
                                    navigateActivityHome(isNewsUser)
                                }
                        }
                    } catch (e: ApiException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }
    private fun navigateActivityHome(isNewsUser : Boolean) {
        viewModel.insertDefaultCategory(isNewsUser) {
            val intent = Intent(this.requireActivity(), HomeActivity::class.java)
            startActivity(intent)
            getOwnerActivity<AuthenticationActivity>()?.finishAffinity()
        }
    }
    private fun displayToast(s: String) {
        Toast.makeText(this.requireContext().applicationContext, s, Toast.LENGTH_SHORT).show()
    }

    override fun openSignInGoogle() {
        val intent = googleSignInClient.signInIntent
        signInLauncher?.launch(intent)
    }

    override fun openSignInFacebook() {
        Toast.makeText(requireContext(), "Chức năng đang phát triển", Toast.LENGTH_SHORT).show()
    }

    override fun openApp() {
        val intent = Intent(getOwnerActivity<AuthenticationActivity>(), HomeActivity::class.java)
        getOwnerActivity<AuthenticationActivity>()?.startActivity(intent)
    }

    override fun openSignUp() {
        findNavController().navigate(R.id.fag_sign_up)
    }

    override fun onChanged(value: KeyboardManager.KeyboardStatus) {
        if (value == KeyboardManager.KeyboardStatus.OPEN) {
            val layoutParamsLogo = viewBinding.logo.layoutParams as ConstraintLayout.LayoutParams
            layoutParamsLogo.verticalBias = 0.1f
            viewBinding.logo.layoutParams = layoutParamsLogo

            val layoutParamsLoginWithEmail = viewBinding.loginWithEmail.layoutParams as ConstraintLayout.LayoutParams
            layoutParamsLoginWithEmail.verticalBias = 0.1f
            viewBinding.loginWithEmail.layoutParams = layoutParamsLoginWithEmail
        }
        else {
            val layoutParamsLogo = viewBinding.logo.layoutParams as ConstraintLayout.LayoutParams
            layoutParamsLogo.verticalBias = 0.17f
            viewBinding.logo.layoutParams = layoutParamsLogo

            val layoutParamsLoginWithEmail = viewBinding.loginWithEmail.layoutParams as ConstraintLayout.LayoutParams
            layoutParamsLoginWithEmail.verticalBias = 0.2f
            viewBinding.loginWithEmail.layoutParams = layoutParamsLoginWithEmail
        }
    }
}