package com.example.frigider.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.frigider.R
import com.example.frigider.model.User.AuthUser
import com.example.frigider.model.User.CreateUser
import com.example.frigider.viewModel.LoginViewModel
import java.lang.Exception

class LoginActivity : AppCompatActivity() {
    private lateinit var userViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        userViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

    }

    fun authentication(view: View) {
        val name: String = findViewById<EditText>(R.id.idNume).text.toString()
        val password : String = findViewById<EditText>(R.id.idParola).text.toString()
        var user = AuthUser(name, password)
        userViewModel.authentication(user)
        userViewModel.isLogValue.observe(this, Observer {
            if (it == true) {
                openMainActivity()
            } else {
                Toast.makeText(this, "Auth or password not found!", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun openMainActivity() {
        try {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }catch (e:Exception){
            println(e.message)
            Toast.makeText(this, "error open intent!", Toast.LENGTH_LONG).show()
        }

    }

    fun create(view: View) {
        try {
            val intent = Intent(this, CreateActivity::class.java)
            startActivity(intent)
        }catch (e:Exception){
            println(e.message)
            Toast.makeText(this, "error open intent!", Toast.LENGTH_LONG).show()
        }

    }
}
