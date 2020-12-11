package com.rockbass2560.mycamera

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import java.io.File
import java.io.FilenameFilter

class ImagePager : AppCompatActivity() {
    lateinit var buttonEnviar: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_pager)

        buttonEnviar = findViewById<Button>(R.id.buttonEnviar)
        buttonEnviar.setOnClickListener {
            val intent = Intent (this, ActivityEnviar::class.java )
            startActivity(intent)
        }

        val folderImage = File(filesDir,"image")
        val files = folderImage.listFiles(FilenameFilter { dir, name ->
            name.contains("image_")
        })?.reversed()

        val viewPager = findViewById<ViewPager2>(R.id.viewPager)

        val photoPageAdapter = PhotoPageAdapter(this,files!!)

        viewPager.adapter = photoPageAdapter



    }

    private inner class PhotoPageAdapter(fa: FragmentActivity, private val files: List<File>): FragmentStateAdapter(fa){
        val cacheFragments = mutableMapOf<Int,Fragment>()

        override fun getItemCount(): Int = files.size

        override fun createFragment(position: Int): Fragment {
            val imagePageFragment: Fragment
            if (!cacheFragments.containsKey(position)){
                val file = files[position]
                imagePageFragment = ImagePageFragment(file)
                cacheFragments[position] = imagePageFragment
            }else{
                imagePageFragment = cacheFragments[position]!!
            }

            return imagePageFragment
        }

    }
}