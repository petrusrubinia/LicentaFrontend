package com.example.frigider.view

import android.R.attr
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Icon
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.frigider.R
import com.example.frigider.model.User.User
import com.example.frigider.viewModel.SettingsViewModel
import de.hdodenhof.circleimageview.CircleImageView
import java.io.IOException
import java.io.InputStream


class SettingsFragment: Fragment(){
    private lateinit var settingsViewModel: SettingsViewModel
    private lateinit var theme1:CardView
    private lateinit var theme2:CardView
    private lateinit var theme3:CardView
    private lateinit var theme4:CardView
    private lateinit var theme5:CardView
    private lateinit var old_userInfo: User
    private lateinit var new_userInfo: User
    val PICK_IMAGE = 1
    private  lateinit var root:View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        settingsViewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)

        root = inflater.inflate(R.layout.fragment_settings, container, false)
        //val textView: TextView = root.findViewById(R.id.text_send)
        settingsViewModel.getUser()
        settingsViewModel.userAccount.observe(this, Observer {

            if(it!=null ) {
                old_userInfo = it!!
                println("->>>" + old_userInfo)
                showInfo(root)
            }
        })
        var buttonSave: CardView = root.findViewById(R.id.idSave_edit)
        buttonSave.setOnClickListener {
            saveChanges(root)
        }
        //theme1
        theme1 = root.findViewById(R.id.theme1)
        theme1.setOnClickListener {
           MainActivity.changeTheme(R.style.AppTheme1.toString())
            LoginActivity.changeTheme(R.style.AppTheme1.toString())
            CreateActivity.changeTheme(R.style.AppTheme1.toString())
            resetApp();
        }
        //theme2
        theme2 = root.findViewById(R.id.theme2)
        theme2.setOnClickListener {
            MainActivity.changeTheme(R.style.AppTheme2.toString())
            LoginActivity.changeTheme(R.style.AppTheme2.toString())
            CreateActivity.changeTheme(R.style.AppTheme2.toString())
            resetApp();
        }
        //theme3
        theme3 = root.findViewById(R.id.theme3)
        theme3.setOnClickListener {
            MainActivity.changeTheme(R.style.AppTheme3.toString())
            LoginActivity.changeTheme(R.style.AppTheme3.toString())
            CreateActivity.changeTheme(R.style.AppTheme3.toString())
            resetApp();
        }
        //theme4
        theme4 = root.findViewById(R.id.theme4)
        theme4.setOnClickListener {
            MainActivity.changeTheme(R.style.AppTheme4.toString())
            LoginActivity.changeTheme(R.style.AppTheme4.toString())
            CreateActivity.changeTheme(R.style.AppTheme4.toString())
            resetApp();
        }
        //theme5
        theme5 = root.findViewById(R.id.theme5)
        theme5.setOnClickListener {
            MainActivity.changeTheme(R.style.AppTheme5.toString())
            LoginActivity.changeTheme(R.style.AppTheme5.toString())
            CreateActivity.changeTheme(R.style.AppTheme5.toString())
            resetApp();
        }
        var editPhoto: CardView = root.findViewById(R.id.idProfile_edit)
        editPhoto.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
        }


        return root
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICK_IMAGE) {
            var photoChooser: CircleImageView = root.findViewById(R.id.idProfilePicture)
            if(data!=null) {
                var uri = data.data
                try {
                    var bitmap: Bitmap = MediaStore.Images.Media.getBitmap((context as AppCompatActivity)!!.contentResolver,uri)
                    println(bitmap)
                    photoChooser.setImageBitmap(bitmap)
                }catch (e: IOException)
                {
                    println(e.printStackTrace())
                }
                println("da")
            }
            else
                println("NU")
        }
    }

    private fun saveChanges(root: View) {
        val nume: String = root.findViewById<EditText>(R.id.idNume_util).text.toString()
        val prenume : String = root.findViewById<EditText>(R.id.idPrenume_util).text.toString()
        val email : String = root.findViewById<EditText>(R.id.idEmail_util).text.toString()
        val numeCont : String = root.findViewById<EditText>(R.id.idNume_cont).text.toString()
        val parola : String = root.findViewById<EditText>(R.id.idParola_util).text.toString()
        if(nume!="" && prenume!="" && email!="" && numeCont!="")
        {
            if(parola!= "" && parola == old_userInfo.parola)
                new_userInfo = User(old_userInfo.id,nume,prenume,email,numeCont,parola)
            else if(parola == "")
                new_userInfo = User(old_userInfo.id,nume,prenume,email,numeCont,old_userInfo.parola)
            else //if(parola != "" && parola != old_userInfo.parola)
                new_userInfo = User(old_userInfo.id,nume,prenume,email,numeCont,parola)

            settingsViewModel.saveChanges(new_userInfo)
        }


    }

    private fun showInfo(root:View) {
        val nume: EditText = root.findViewById<EditText>(R.id.idNume_util)
        val prenume : EditText = root.findViewById<EditText>(R.id.idPrenume_util)
        val email : EditText = root.findViewById<EditText>(R.id.idEmail_util)
        val numeCont : EditText = root.findViewById<EditText>(R.id.idNume_cont)

        println(old_userInfo)
        //setare text
        if(old_userInfo.nume!="" && old_userInfo.prenume!="" && old_userInfo.email!="" && old_userInfo.utilizator!="")
        nume.setText(old_userInfo.nume)
        prenume.setText(old_userInfo.prenume)
        email.setText(old_userInfo.email)
        numeCont.setText(old_userInfo.utilizator)
    }


    private fun resetApp() {
        var i: Intent = Intent(context,MainActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(i)
        var context = activity as AppCompatActivity
        context.finish()
    }
}