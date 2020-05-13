package com.example.frigider.view;

import android.Manifest
import android.app.Activity
import androidx.fragment.app.Fragment

import android.app.FragmentManager
import android.app.FragmentTransaction
import android.content.ClipData
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.frigider.R
import com.example.frigider.repository.scanner.ScanActivity
import com.example.frigider.viewModel.AddViewModel
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.nav_header_navigation.*
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {


    private lateinit var appBarConfiguration: AppBarConfiguration

    companion object {
        private const val CAMERA_REQUEST = 1888
        private const val REQUEST_ID_MULTIPLE_PERMISSIONS = 1999
        private const val ADD_CODE = 1
        private const val REQUEST_IMAGE_CAPTURE = 3
    }
    lateinit var currentPhotoPath: String
    lateinit var photoURI: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        checkAndRequestPermissions()

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_profile, R.id.nav_info,
                R.id.nav_settings, R.id.nav_share, R.id.nav_send, R.id.nav_logout
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


    }

    private fun checkAndRequestPermissions(): Boolean {
        var cameraPermission: Int = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA )
        var writeExternalStorage: Int = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        var readExternalStorage: Int = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        var arrayPermission = ArrayList<String>()
        if ( cameraPermission != PackageManager.PERMISSION_GRANTED)
            arrayPermission.add(Manifest.permission.CAMERA)
        if ( writeExternalStorage != PackageManager.PERMISSION_GRANTED)
            arrayPermission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if(readExternalStorage != PackageManager.PERMISSION_GRANTED)
            arrayPermission.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        if (!arrayPermission.isEmpty()) {
            ActivityCompat.requestPermissions(
                this, arrayPermission
                    .toArray(arrayOfNulls<String>(arrayPermission.size)),
                REQUEST_ID_MULTIPLE_PERMISSIONS
            )
            return false
        }
        return true
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.navigation, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.getItemId()) {
            R.id.action_scan -> {
                if (checkAndRequestPermissions()){
                val intent = Intent(this, ScanActivity::class.java)
                startActivityForResult(intent, ADD_CODE)
                }
                else
                {
                    Toast.makeText(this,
                        "Nu exista acces la camera",
                        Toast.LENGTH_SHORT).show();
                }
                return true

            }
            R.id.action_detect -> {
                println("detect")
                if (checkAndRequestPermissions())
                {
                    dispatchTakePictureIntent()
//                    val cameraIntent =
//                        Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//                    startActivityForResult(cameraIntent, CAMERA_REQUEST)

                }
                else
                    Toast.makeText(this,
                        "Nu exista acces la camera si la stocare",
                        Toast.LENGTH_SHORT).show();
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
            println(absolutePath)
        }
    }
    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    photoURI = FileProvider.getUriForFile(
                        this,
                        "com.example.frigider.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, CAMERA_REQUEST)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val code = data.getStringExtra("1")
            //se trimite codul de bare spre fragmentul AddFragment()
            var fragment: AddFragment = AddFragment()
            var bundle: Bundle = Bundle()
            bundle.putString("barcode", code)
            fragment.arguments = bundle
            this.replaceFragment(fragment)
        }
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            // val bitmapImage = data!!.extras!!["data"] as Bitmap?
            var path: String = ""
            //path = compressImage(photoURI.toString())
            println("path ->>>>>> " + photoURI.path)
            //if(bitmapImage != null) {
            //path = saveToInternalStorage(bitmapImage!!)
            //println("!!-> $path")

            path = compressImage(
                "/storage/emulated/0/Android/data/com.example.frigider/files/Pictures/" + photoURI.path!!.split(
                    "/"
                )[3]
            )


            var fragment: AddFragment = AddFragment()
            var bundle: Bundle = Bundle()
            bundle.putString("path", path)
            fragment.arguments = bundle
            this.replaceFragment(fragment)

            //imageView!!.setImageBitmap(photo)
        }
        }
//    private fun saveToInternalStorage(bitmapImage:Bitmap):String {
//        val cw = ContextWrapper(this)
//        // path to /data/data/yourapp/app_data/imageDir
//       // val directory = cw.getDir("imageDir", Context.MODE_PRIVATE)
//        // Create imageDir
//        var mypath: File? = getOutputMediaFile();
//
//        println("absolut path:  -> " + mypath!!.absolutePath)
//        var fos: FileOutputStream? = null
//        try
//        {
//            fos = FileOutputStream(mypath)
//            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 80, fos)
//        }
//        catch (e:Exception) {
//            e.printStackTrace()
//            println("exceptie")
//        }
//        finally
//        {
//            try
//            {
//                fos!!.close()
//            }
//            catch (e:IOException) {
//                e.printStackTrace()
//                println("close")
//            }
//        }
//        return mypath.absolutePath
//    }
//
//    /** Create a File for saving an image or video */
//    private fun getOutputMediaFile(): File? {
//        // To be safe, you should check that the SDCard is mounted
//        // using Environment.getExternalStorageState() before doing this.
//        val mediaStorageDir = File(Environment.getExternalStorageDirectory() ,"/Android/data/" + getApplicationContext().getPackageName() + "/Files")
//        // This location works best if you want the created images to be shared
//        // between applications and persist after your app has been uninstalled.
//        // Create the storage directory if it does not exist
//        if (!mediaStorageDir.exists())
//        {
//            if (!mediaStorageDir.mkdirs())
//            {
//                return null
//            }
//        }
//        // Create a media file name
//        val timeStamp = SimpleDateFormat("ddMMyyyy_HHmm").format(Date())
//        val mediaFile:File
//        val mImageName = "MI_" + timeStamp + ".jpg"
//        mediaFile = File(mediaStorageDir.getPath() + File.separator + mImageName)
//        return mediaFile
//    }


    fun compressImage(imageUri:String):String {
        var filePath = getRealPathFromURI(imageUri)
        var scaledBitmap:Bitmap? = null
        var options = BitmapFactory.Options()
        // by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
        // you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true
        var bmp = BitmapFactory.decodeFile(filePath, options)
        var actualHeight = options.outHeight
        var actualWidth = options.outWidth
        // max Height and width values of the compressed image is taken as 816x612
        var maxHeight = 816.0f
        var maxWidth = 612.0f
        println("ACTUAL HEIGHT ->$actualHeight")
        println("ACTUAL WIDTH -> $actualWidth")
        var imgRatio = (actualWidth / actualHeight).toFloat()
        var maxRatio = maxWidth / maxHeight
        // width and height values are set maintaining the aspect ratio of the image
        if (actualHeight > maxHeight || actualWidth > maxWidth)
        {
            if (imgRatio < maxRatio)
            {
                imgRatio = maxHeight / actualHeight
                actualWidth = (imgRatio * actualWidth).toInt()
                actualHeight = maxHeight.toInt()
            }
            else if (imgRatio > maxRatio)
            {
                imgRatio = maxWidth / actualWidth
                actualHeight = (imgRatio * actualHeight).toInt()
                actualWidth = maxWidth.toInt()
            }
            else
            {
                actualHeight = maxHeight.toInt()
                actualWidth = maxWidth.toInt()
            }
        }
        // setting inSampleSize value allows to load a scaled down version of the original image
        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight)
        // inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false
        // this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true
        options.inInputShareable = true
        options.inTempStorage = ByteArray(16 * 1024)
        try
        {
            // load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options)
        }
        catch (exception:OutOfMemoryError) {
            exception.printStackTrace()
        }
        try
        {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888)
        }
        catch (exception:OutOfMemoryError) {
            exception.printStackTrace()
        }
        val ratioX:Float = (actualWidth / options.outWidth).toFloat()
        val ratioY = (actualHeight / options.outHeight).toFloat()
        val middleX = actualWidth / 2.0f
        val middleY = actualHeight / 2.0f
        val scaleMatrix = Matrix()
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY)
        val canvas = Canvas(scaledBitmap!!)
        canvas.setMatrix(scaleMatrix)
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, Paint(Paint.FILTER_BITMAP_FLAG))
        // check the rotation of the image and display it properly
        val exif: ExifInterface
        try
        {
            exif = ExifInterface(filePath)
            val orientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION, 0)
            Log.d("EXIF", "Exif: " + orientation)
            val matrix = Matrix()
            if (orientation == 6)
            {
                matrix.postRotate(90F)
                Log.d("EXIF", "Exif: " + orientation)
            }
            else if (orientation == 3)
            {
                matrix.postRotate(180F)
                Log.d("EXIF", "Exif: " + orientation)
            }
            else if (orientation == 8)
            {
                matrix.postRotate(270F)
                Log.d("EXIF", "Exif: " + orientation)
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                true)
        }
        catch (e: IOException) {
            e.printStackTrace()
        }
        var out: FileOutputStream? = null
        val filename = getFilename()
        try
        {
            out = FileOutputStream(filename)
            // write the compressed bitmap at the destination specified by filename.
            scaledBitmap!!.compress(Bitmap.CompressFormat.JPEG, 80, out)
        }
        catch (e: FileNotFoundException) {
            e.printStackTrace()
            println("ddsfbask")
        }
        return filename
    }
    private fun getFilename(): String {
        val mediaStorageDir = File("${Environment.getExternalStorageDirectory()}/Android/data/${this.applicationContext.packageName}/Files/Compressed")
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            mediaStorageDir.mkdirs()
        }

        val mImageName = "IMG_" + System.currentTimeMillis().toString() + ".jpg"
        return mediaStorageDir.getAbsolutePath() + "/" + mImageName
    }
    private fun getRealPathFromURI(contentURI:String):String {
        val contentUri = Uri.parse(contentURI)
        val cursor = getContentResolver().query(contentUri, null, null, null, null)
        if (cursor == null)
        {
            return contentUri.getPath()!!
        }
        else
        {
            cursor.moveToFirst()
            val index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            return cursor.getString(index)
        }
    }
    fun calculateInSampleSize(options:BitmapFactory.Options, reqWidth:Int, reqHeight:Int):Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1
        if (height > reqHeight || width > reqWidth)
        {
            val heightRatio = Math.round(height.toFloat() / reqHeight.toFloat())
            val widthRatio = Math.round(width.toFloat() / reqWidth.toFloat())
            inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
        }
        val totalPixels = (width * height).toFloat()
        val totalReqPixelsCap = (reqWidth * reqHeight * 2).toFloat()
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap)
        {
            inSampleSize++
        }
        return inSampleSize
    }



}

fun AppCompatActivity.replaceFragment(fragment: Fragment) {
    val fragmentManager = supportFragmentManager
    val transaction = fragmentManager.beginTransaction()
    //transaction.remove(HomeFragment())
    transaction.addToBackStack(null)
    transaction.replace(R.id.nav_host_fragment, fragment)
    transaction.addToBackStack(null)
    transaction.commit()
}