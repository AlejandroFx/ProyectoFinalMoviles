package com.rockbass2560.mycamera

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FilenameFilter
import java.io.OutputStream



class ActivityEnviar () : AppCompatActivity(){
    private lateinit var imageView: ImageView
    private val REQUEST= 1

         @SuppressLint("WrongThread")
         override fun onCreate(savedInstanceState: Bundle?) {
             super.onCreate(savedInstanceState)
             setContentView(R.layout.activity_image_pager)

             if (ContextCompat.checkSelfPermission(this@ActivityEnviar,
                     android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {


                 if (ActivityCompat.shouldShowRequestPermissionRationale(this@ActivityEnviar,
                         android.Manifest.permission.READ_EXTERNAL_STORAGE)) {


                 } else {

                     ActivityCompat.requestPermissions(this@ActivityEnviar,
                         arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),REQUEST)

                 }
             }
             if (ContextCompat.checkSelfPermission(this@ActivityEnviar,
                     android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {


                 if (ActivityCompat.shouldShowRequestPermissionRationale(this@ActivityEnviar,
                         android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {


                 } else {

                     ActivityCompat.requestPermissions(this@ActivityEnviar,
                         arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),REQUEST)

                 }
             }


             val folderImage = File(filesDir,"image")
             val files = folderImage.listFiles(FilenameFilter { dir, name ->
                 name.contains("image_")
             })?.reversed()


                 val imagen = BitmapFactory.decodeFile(files?.first()?.absolutePath)


                 val enviarImg = Intent(Intent.ACTION_SEND)

                 enviarImg.type = "image/jpeg"


                 val valoresImg = ContentValues()
                 valoresImg.put(MediaStore.Images.Media.TITLE, "Photo")
                 valoresImg.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")


                 val uri = contentResolver.insert(
                     MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                     valoresImg
                 )


                 val outstreamgf: OutputStream?
                 try {
                     outstreamgf = contentResolver.openOutputStream(uri!!)


                     imagen.compress(Bitmap.CompressFormat.JPEG, 100, outstreamgf)
                     outstreamgf!!.close()
                 } catch (e: Exception) {
                     System.err.println(e.toString())
                 }

                 enviarImg.putExtra(Intent.EXTRA_STREAM, uri)

                 startActivity(Intent.createChooser(enviarImg, getString(R.string.enviarImagen)))

         }


    }

