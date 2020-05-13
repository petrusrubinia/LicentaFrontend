package com.example.frigider.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.frigider.R
import com.example.frigider.model.User.AuthUser
import com.example.frigider.model.User.CreateUser
import com.example.frigider.viewModel.LoginViewModel
import java.lang.Exception

class CreateActivity : AppCompatActivity() {
    private lateinit var userViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        userViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

    }

    fun authentication(view: View) {
        val name: String = findViewById<EditText>(R.id.idNume_utilizator).text.toString()
        val prenume: String = findViewById<EditText>(R.id.idPrenume_utilizator).text.toString()
        val email: String = findViewById<EditText>(R.id.idEmail_utilizator).text.toString()
        val nume_utilizator: String = findViewById<EditText>(R.id.idNume_cont).text.toString()
        val parola1: String = findViewById<EditText>(R.id.idParola1).text.toString()
        val parola2: String = findViewById<EditText>(R.id.idParola2).text.toString()
        if (parola1.equals(parola2)) {
            userViewModel.createAccount(CreateUser(name, prenume, email, nume_utilizator, parola1))
            openMainActivity()
        } else {
            Toast.makeText(this, "Auth or password not found!", Toast.LENGTH_LONG).show()
        }

    }

    private fun openMainActivity() {
        try {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } catch (e: Exception) {
            println(e.message)
            Toast.makeText(this, "error open intent!", Toast.LENGTH_LONG).show()
        }

    }

}
