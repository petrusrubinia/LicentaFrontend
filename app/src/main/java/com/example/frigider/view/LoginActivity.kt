package com.example.frigider.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
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

    companion object{
        lateinit var shared: SharedPreferences
        public fun changeTheme(id:String){
            val editor = shared.edit()
            editor.remove("theme")
            editor.commit()
            editor.putString("theme",id)
            editor.commit()

        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        PreferenceManager.setDefaultValues(this, R.xml.pref_main, false)
        this.setTheme(getUserTheme())
        shared = PreferenceManager.getDefaultSharedPreferences(this);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        userViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
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
            this.finish()
        }catch (e:Exception){
            println(e.message)
            Toast.makeText(this, "error open intent!", Toast.LENGTH_LONG).show()
        }

    }
}
