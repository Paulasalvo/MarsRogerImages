package com.desafio.marsrogerimages

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.desafio.marsrogerimages.api.CuriosityPhotosDTO
import com.desafio.marsrogerimages.api.NasaApiService
import com.desafio.marsrogerimages.api.getNasaApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL


class MainViewModel: ViewModel() {
    private val apiService:NasaApiService= getNasaApiService()
    private val _curiosityPhotosLiveData: MutableLiveData<CuriosityPhotosDTO> = MutableLiveData()
    val curiosityPhotosLiveData: LiveData<CuriosityPhotosDTO> = _curiosityPhotosLiveData

    val imageLiveData:MutableLiveData<Map<Int, Bitmap>> = MutableLiveData()

    private var imageListUrl: MutableList<String> = mutableListOf()

    fun fetchCuriosityPhotos(){
        viewModelScope.launch {
            val curiosityPhotosDTO=apiService.getCuriosityPhotos()

            imageListUrl.add(curiosityPhotosDTO.photos[0].img_src)
            imageListUrl.add(curiosityPhotosDTO.photos[1].img_src)
            imageListUrl.add(curiosityPhotosDTO.photos[2].img_src)
            imageListUrl.add(curiosityPhotosDTO.photos[3].img_src)

            _curiosityPhotosLiveData.postValue(curiosityPhotosDTO)
        }
    }

    fun downloadImage(context: Context, index: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.i("URL", imageListUrl[index])
                val bitmap = Glide
                    .with(context)
                    .asBitmap()
                    .load(imageListUrl[index])
                    .submit()
                    .get()
                Log.i("BITMAP", bitmap.toString())
                imageLiveData.postValue(mapOf(index to bitmap))

            } catch (e: Exception) {
                // Handle exceptions, e.g., network errors, URL parsing errors, etc.
                // You can log the error or take appropriate action.
                Log.e("Error", e.toString())
            }
        }
    }


}
