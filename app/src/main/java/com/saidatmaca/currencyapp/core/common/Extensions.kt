package com.saidatmaca.currencyapp.core.common

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.reflect.TypeToken
import com.saidatmaca.currencyapp.core.WebServiceError
import com.saidatmaca.currencyapp.core.utils.ResponseResult
import com.saidatmaca.currencyapp.data.local.entity.User
import com.saidatmaca.currencyapp.domain.model.Coin
import com.saidatmaca.currencyapp.domain.use_case.UserLiveUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.text.DecimalFormat
import java.util.Locale
import kotlin.math.abs


fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}


fun View.gone() {
    visibility = View.GONE
}


inline fun <reified T> String.toListByGson(): ArrayList<T> = if (isNotEmpty()) {
    Gson().fromJson(this, TypeToken.getParameterized(ArrayList::class.java, T::class.java).type)
} else {
    arrayListOf()
}



inline fun <reified T> String.jsonStringToModel(): ResponseResult<T> {
    val gson = Gson()

    Log.e("jsonDönüşümü",this)

    return if (contains("ErrorCode")) {
        val jsonArray = gson.fromJson(this, JsonArray::class.java)
        if (jsonArray.size() > 0) {
            val firstElement = jsonArray[0]
            val errorModel: WebServiceError = gson.fromJson(firstElement, WebServiceError::class.java)
            Log.e("sd333adasda", errorModel.toString())
            ResponseResult(emptyList(), true, errorModel.errorMessageStr)
        } else {
            ResponseResult(emptyList(), true, "Geçersiz JSON biçimi")
        }
    } else if (this.isNotEmpty()){
        val dataList = gson.fromJson<List<T>>(
            this,
            object : TypeToken<List<T>>() {}.type
        )
        ResponseResult(dataList, false, "")
    }else{
        ResponseResult(arrayListOf(),false,"")
    }
}





fun Uri.imageToString(context: Context): String {
    val thumbnail: Bitmap = if (Build.VERSION.SDK_INT < 28) {
        MediaStore.Images.Media.getBitmap(context.contentResolver, this)
    } else {
        val source: ImageDecoder.Source = ImageDecoder.createSource(context.contentResolver, this)
        ImageDecoder.decodeBitmap(source)
    }
    val stream = ByteArrayOutputStream()
    thumbnail.compress(Bitmap.CompressFormat.JPEG, 5, stream)
    val byteArray: ByteArray = stream.toByteArray()

    return Base64.encodeToString(byteArray, Base64.DEFAULT)


}

fun String.stringToBitmap(): Bitmap? {
    val imageByte: ByteArray = Base64.decode(this, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(imageByte, 0, imageByte.size)
}

fun View.getBitmapFromView(): Bitmap =
    this.run {
        measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        layout(0, 0, measuredWidth, measuredHeight)
        val bitmap = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        draw(canvas)
        bitmap
    }



fun Int.formatTime(): String {
    val hours = (this / 3600)
    val minutes = (this % 3600 / 60)
    return if (hours > 0) {
        String.format(Locale.getDefault(), "%02d SA %02d DK", hours, minutes)
    } else {
        String.format(Locale.getDefault(), "%02d DK", minutes)

    }

}

fun Double.formatPrice(): String{

    if (this > 1){
        val decimalFormat = DecimalFormat("#,###.00")
        val formattedNumber = decimalFormat.format(this)

        val returnString = "$$formattedNumber"

        return returnString

    }else{

        val formattedNumber = String.format("%.2f", this)
        val returnString = "$$formattedNumber"

        return returnString
    }

}

fun Float.formatChange(coin: Coin) : String{
    val decimalFormat = DecimalFormat("#,###.00")
    if (this >= 0){
        var plusValue = (coin.price * this) / 100
        val formattedPlusValue = decimalFormat.format(plusValue)
        val newString ="+$this%(+$$formattedPlusValue)"

        if (this == 0f){
            val zeroString ="$this%($$formattedPlusValue)"
            return zeroString
        }else{
            return newString
        }


    }else{
        var negativeValue = (coin.price * abs(this)) / 100
        val formattedNegativeValue = decimalFormat.format(negativeValue)
        val newString ="-$this%(-$$formattedNegativeValue)"

        return newString
    }

}


fun ViewModel.observeUserLive(userLiveUseCase: UserLiveUseCase, updateUser : (user : User?) -> Unit) {
    viewModelScope.launch {
      /*  userLiveUseCase.userFlow
            .flowOn(Dispatchers.IO)
            .distinctUntilChanged()
            .collect { user ->
                updateUser(user)

            }*/
    }
}

fun String.splitTime() : String{
    val splitList = this.split(" ")
    return if (splitList.size>1){
        splitList[1]
    }else{
        this
    }
}

fun String.splitDate() : String{
    val splitList = this.split(" ")
    return if (splitList.isNotEmpty()){
        splitList[0]
    }else{
        this
    }
}