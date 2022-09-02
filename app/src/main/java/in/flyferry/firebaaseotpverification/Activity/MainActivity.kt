package `in`.flyferry.firebaaseotpverification.Activity

import `in`.flyferry.firebaaseotpverification.R
import `in`.flyferry.firebaaseotpverification.databinding.ActivityMainBinding
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }

        binding.verifyBtn.setOnClickListener {
            checkAndVerify()
        }
    }

    private fun checkAndVerify() {
        val phone = binding.phoneET.text.toString()
//        PhoneNumberUtils.formatNumber(phone)

        val intent = Intent(this, OTPActivity::class.java)
        intent.putExtra("phoneNo", phone)
        startActivity(intent)
        finish()
    }
}