package org.marques999.acme.store

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import android.widget.Toast

class RegisterActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        _signupButton!!.setOnClickListener { signup() }

        _loginLink!!.setOnClickListener {
            // Finish the registration screen and return to the Login activity
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out)
        }
    }

    fun signup() {

        if (!validateForm()) {
            onSignupFailed()
            return
        }

        _signupButton!!.isEnabled = false

        val progressDialog = ProgressDialog(this@SignupActivity,
            R.style.AppTheme_Dark_Dialog)
        progressDialog.isIndeterminate = true
        progressDialog.setMessage("Creating Account...")
        progressDialog.show()

        val name = _nameText!!.text.toString()
        val address = _addressText!!.text.toString()
        val email = _emailText!!.text.toString()
        val mobile = _mobileText!!.text.toString()
        val password = _passwordText!!.text.toString()
        val reEnterPassword = _reEnterPasswordText!!.text.toString()

        // TODO: Implement your own signup logic here.

        android.os.Handler().postDelayed(
            {
                // On complete call either onSignupSuccess or onSignupFailed
                // depending on success
                onSignupSuccess()
                // onSignupFailed();
                progressDialog.dismiss()
            }, 3000)
    }


    fun onSignupSuccess() {
        _signupButton!!.isEnabled = true
        setResult(Activity.RESULT_OK, null)
        finish()
    }

    fun onSignupFailed() {
        Toast.makeText(baseContext, "Login failed", Toast.LENGTH_LONG).show()
        _signupButton!!.isEnabled = true
    }

    fun validateForm(): Boolean {
        var formValid = true
        val name = _nameText!!.text.toString()
        val address = _addressText!!.text.toString()
        val email = _emailText!!.text.toString()
        val mobile = _mobileText!!.text.toString()
        val password = _passwordText!!.text.toString()
        val reEnterPassword = _reEnterPasswordText!!.text.toString()

        if (name.isEmpty() || name.length < 3) {
            _nameText!!.error = "at least 3 characters"
            formValid = false
        } else {
            _nameText!!.error = null
        }

        if (address.isEmpty()) {
            _addressText!!.error = "Enter Valid Address"
            formValid = false
        } else {
            _addressText!!.error = null
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText!!.error = "enter a valid email address"
            formValid = false
        } else {
            _emailText!!.error = null
        }

        if (mobile.isEmpty() || mobile.length != 10) {
            _mobileText!!.error = "Enter Valid Mobile Number"
            formValid = false
        } else {
            _mobileText!!.error = null
        }

        if (password.isEmpty() || password.length < 4 || password.length > 10) {
            _passwordText!!.error = "between 4 and 10 alphanumeric characters"
            formValid = false
        } else {
            _passwordText!!.error = null
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length < 4 || reEnterPassword.length > 10 || reEnterPassword != password) {
            _reEnterPasswordText!!.error = "Password Do not match"
            formValid = false
        } else {
            _reEnterPasswordText!!.error = null
        }

        return formValid
    }

    companion object {
        private val TAG = "SignupActivity"
    }
}