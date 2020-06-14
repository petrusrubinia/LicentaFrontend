package com.example.frigider.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.frigider.R
import com.example.frigider.model.User.AuthUser
import com.example.frigider.model.User.CreateUser
import com.example.frigider.model.User.User
import com.example.frigider.viewModel.LoginViewModel
import java.lang.Exception

class CreateActivity : AppCompatActivity() {
    private lateinit var userViewModel: LoginViewModel
    private lateinit var create_button:CardView
    companion object{
        //lateinit var shared: SharedPreferences
        public fun changeTheme(id:String){
            val editor = LoginActivity.shared.edit()
            editor.remove("theme")
            editor.commit()
            editor.putString("theme",id)
            editor.commit()

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        PreferenceManager.setDefaultValues(this, R.xml.pref_main, false)
        this.setTheme(getUserTheme())
        //shared = PreferenceManager.getDefaultSharedPreferences(this);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
        userViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        setTheme(R.style.AppTheme2)
        create_button = findViewById(R.id.idCreate)
        create_button.setOnClickListener {
            saveAccount()
        }

    }
    fun getTheme(context: Context, theme1:Int, theme2:Int, theme3:Int, theme4:Int, theme5:Int):Int {
        LoginActivity.shared = PreferenceManager.getDefaultSharedPreferences(this)
        var theme = LoginActivity.shared.getString("theme", "2131755017")!!.toInt()
        if (theme == 2131755014)
        {
            return theme1
        }
        else if (theme == 2131755015)
        {
            return theme2
        }
        else if(theme == 2131755016)
        {
            return theme3
        }
        else if(theme == 2131755017)
        {
            return theme4
        }
        else
        {
            return theme5
        }
    }
    private fun getUserTheme():Int {
        return getTheme(this, R.style.AppTheme1, R.style.AppTheme2, R.style.AppTheme3, R.style.AppTheme4,R.style.AppTheme5)
    }

    fun saveAccount() {
        var user: User
        val name: String = findViewById<EditText>(R.id.idNume_utilizator).text.toString()
        val prenume: String = findViewById<EditText>(R.id.idPrenume_utilizator).text.toString()
        val email: String = findViewById<EditText>(R.id.idEmail_utilizator).text.toString()
        val nume_utilizator: String = findViewById<EditText>(R.id.idNume_cont).text.toString()
        val parola1: String = findViewById<EditText>(R.id.idParola1).text.toString()
        val parola2: String = findViewById<EditText>(R.id.idParola2).text.toString()
        if (parola1.equals(parola2)) {
            userViewModel.createAccount(CreateUser(name,prenume,email,nume_utilizator,parola1))
            userViewModel.userAccount.observe(this, Observer {
                if (it != null && (it.parola != "" && it.utilizator != "")) {
                    openMainActivity()
                    Toast.makeText(this, "Cont creat cu succes!", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Contul nu a fost creat cu succes!", Toast.LENGTH_LONG).show()
                }
            })
        } else {
            Toast.makeText(this, "Parolele nu coincid!", Toast.LENGTH_LONG).show()
        }

    }

    private fun openMainActivity() {
        try {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } catch (e: Exception) {
            println(e.message)
            Toast.makeText(this, "Eroare!", Toast.LENGTH_LONG).show()
        }

    }

}
