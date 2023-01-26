package com.example.tasklist.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.findNavController
import com.example.tasklist.R
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.text.TextRecognizer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.googlecode.tesseract.android.TessBaseAPI
import kotlinx.android.synthetic.main.activity_cam.*
import java.io.File
import java.io.FileOutputStream

class CamActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var tesseract: TessBaseAPI


    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cam)

        tesseract = TessBaseAPI()
        val file = File("assets/tessdata")
        if(file.exists()){
            println("Arquivo existe, configurando teseract")
            tesseract.init("assets/tessdata", "por")
        }else{
            Log.e("Tesseract Error","tessdata not found")
        }

        initClicks()

        auth = Firebase.auth

        btn_take_picture.isEnabled = false

        // memunculkan dialog permisson
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 100)
        } else {
            btn_take_picture.isEnabled = true
        }

        // ketika button take di klik ia memanggil camera
        btn_take_picture.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, 101)

        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 101){
            var picture: Bitmap? = data?.getParcelableExtra("data")
            img_view.setImageBitmap(picture)

            tesseract.setImage(picture) // setando a imagem para o tesseract
            val result = tesseract.utF8Text // obtendo o texto reconhecido

            Log.d("Tesseract", "Texto reconhecido: $result")

            val state = Environment.getExternalStorageState()
                if (Environment.MEDIA_MOUNTED != state) {
                    Log.e("error","External Storage Not available")
                }else{
                    Log.e("error","External Storage available")
                }

            val imageData = data?.extras?.get("data") as Bitmap

            val file = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "my_image.jpg")

                if(file.exists()){
                    Log.e("file","Created")
                    Log.e("Path",file.canonicalPath)

                }else{
                    Log.e("file","not created")
                    println(file.absolutePath)

                }
            val stream = FileOutputStream(file)
            imageData.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
//            finish()
        }
    }


    private fun initClicks(){
        println("logout")
        ibLogout.setOnClickListener { logoutApp() }

    }
    private fun logoutApp(){
        auth.signOut()
        this.finishAffinity()

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            btn_take_picture.isEnabled = false
        }
    }
}