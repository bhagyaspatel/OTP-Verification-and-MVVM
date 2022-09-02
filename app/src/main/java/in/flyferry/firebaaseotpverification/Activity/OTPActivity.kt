package `in`.flyferry.firebaaseotpverification.Activity

import `in`.flyferry.firebaaseotpverification.R
import `in`.flyferry.firebaaseotpverification.databinding.ActivityOtpactivityBinding
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

class OTPActivity : AppCompatActivity() {

    private lateinit var binding : ActivityOtpactivityBinding
    private val TAG = "OTP_Class"

    private lateinit var auth : FirebaseAuth
    private lateinit var callbacks : PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var authCode : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otpactivity)

        auth = Firebase.auth

        binding = ActivityOtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }

        val phone = intent.getStringExtra("phoneNo")
        Log.d(TAG, "phone Number ${phone}")

        sendVerification(phone)

        binding.verifyOtpBtn.setOnClickListener {
            if(!binding.otpET.text.toString().isNullOrBlank())
                verifyCode(binding.otpET.text.toString())
        }
    }

    private fun sendVerification(phoneNumber : String?) {
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.

                Log.d(TAG, "onVerificationCompleted:$credential")
//                signInWithPhoneAuthCredential(credential) : Automatic process if sms comes on same phone
                val code : String? = credential.smsCode

                if(code != null){
                    binding.progressBar.visibility = View.GONE
                    verifyCode(code)
                }
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                Log.d(TAG, "on Code sent")
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
//                Log.d(TAG, "onCodeSent:$verificationId")
                authCode = verificationId

                if(!binding.otpET.text.toString().isNullOrBlank())
                    verifyCode(binding.otpET.text.toString())
                // Save verification ID and resending token so we can use them later
//                storedVerificationId = verificationId
//                resendToken = token
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
//                Log.w(TAG, "onVerificationFailed", e)
                Log.d(TAG, "Verification failed ${e.message}")

                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    Toast.makeText(this@OTPActivity, "Invalid Request", Toast.LENGTH_SHORT).show()

                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    Toast.makeText(this@OTPActivity, "See log", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "Error : ${e.message}")
                }
            }
        }

        if (phoneNumber != null) {
            Log.d(TAG, "phone number not null")
            val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber("+91" + phoneNumber)       // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this)                 // Activity (for callback binding)
                .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)
        }
    }


    private fun verifyCode(codeByUser : String) {
        Log.d(TAG, "verify code ()")
        val credential : PhoneAuthCredential = PhoneAuthProvider.getCredential(authCode, codeByUser)
        signInUserByCredentials(credential)
    }

    private fun signInUserByCredentials(credential: PhoneAuthCredential) {
        val firebaseAuth = FirebaseAuth.getInstance()
        Log.d(TAG, "sign in user ()")

        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful){
                Log.d(TAG, "successful")
                binding.progressBar.visibility = View.GONE
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this, "Error sign in", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "Error : ${it.exception}")
            }
        }
    }
}