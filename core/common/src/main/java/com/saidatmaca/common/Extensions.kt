package com.saidatmaca.common

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import android.view.View
import java.io.ByteArrayOutputStream
import java.text.DecimalFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale


fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}


fun View.gone() {
    visibility = View.GONE
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



fun Long.toFormattedDate(pattern: String = "yyyy-MM-dd", zoneId: ZoneId = ZoneId.systemDefault()): String {
    val dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(this), zoneId)
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return dateTime.format(formatter)
}


fun String.splitTime() : String{
    val splitList = this.split(" ")
    return if (splitList.size>1){
        splitList[1]
    }else{
        this
    }
}

fun String.splitDate() : String {
    val splitList = this.split(" ")
    return if (splitList.isNotEmpty()) {
        splitList[0]
    } else {
        this
    }
}





