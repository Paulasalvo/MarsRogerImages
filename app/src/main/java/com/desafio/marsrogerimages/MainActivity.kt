package com.desafio.marsrogerimages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.desafio.marsrogerimages.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val viewModel:MainViewModel= MainViewModel()
    private lateinit var binding:ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()

        viewModel.fetchCuriosityPhotos()
        viewModel.curiosityPhotosLiveData.observe(this){
            binding.progressBar1.visibility=View.VISIBLE
            viewModel.downloadImage(context = this, 0)
        }

        viewModel.imageLiveData.observe(this){
            when {
                it.keys.contains(0)->{
                    binding.progressBar1.visibility=View.GONE
                    binding.progressBar2.visibility=View.VISIBLE
                    binding.imageView1.setImageBitmap(it[0])
                    viewModel.downloadImage(context = this, 1)
                }
                it.keys.contains(1)->{
                    binding.progressBar2.visibility=View.GONE
                    binding.progressBar3.visibility=View.VISIBLE
                    binding.imageView2.setImageBitmap(it[1])
                    viewModel.downloadImage(context = this, 2)
                }
                it.keys.contains(2)->{
                    binding.progressBar3.visibility=View.GONE
                    binding.progressBar4.visibility=View.VISIBLE
                    binding.imageView3.setImageBitmap(it[2])
                    viewModel.downloadImage(context = this, 3)
                }
                it.keys.contains(3)->{
                    binding.progressBar4.visibility=View.GONE
                    binding.imageView4.setImageBitmap(it[3])
                }
            }
        }

        binding.btnAlert.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Mensaje en Thread UI")
            builder.setMessage("Alerta para mostrar el funcionamiento de corrutinas en background")
            builder.setCancelable(true)
            builder.setPositiveButton("OK") {
                    dialog, _ ->
                dialog.cancel()
            }
            builder.show()
        }
    }

    private fun setupUI(){
        binding.progressBar1.visibility= View.GONE
        binding.progressBar2.visibility= View.GONE
        binding.progressBar3.visibility= View.GONE
        binding.progressBar4.visibility= View.GONE
    }
}