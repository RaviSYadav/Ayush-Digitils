package com.payment.ayushdigitils.ex

import android.app.DatePickerDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import com.google.android.material.textfield.TextInputEditText
import com.payment.ayushdigitils.ui.widget.CircleImageView
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.random.Random


fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInputFromWindow(windowToken, 1, 0)
}

fun View.manageClick(isClick: Boolean){
    if (isClick){
        isEnabled = false
        alpha = 0.5f
    }else{
        isEnabled = true
        alpha = 1f
    }
}




fun View.captureAndShareScreenshot() {
    val screenshot = captureScreenshot(this)
    shareScreenshot(this.context, screenshot)
}
fun View.captureAndSharePdfScreenshot(){
    val screenshot = captureScreenshot(this)
    convertBitmapToPdf(screenshot,this.context)
}

private fun captureScreenshot(view: View): Bitmap {
    view.isDrawingCacheEnabled = true
    val screenshot = Bitmap.createBitmap(view.drawingCache)
    view.isDrawingCacheEnabled = false
    return screenshot
}
private fun shareScreenshot(context: Context, screenshot: Bitmap) {
    val shareIntent = Intent(Intent.ACTION_SEND)
    shareIntent.type = "image/jpeg"
    val imagePath = MediaStore.Images.Media.insertImage(
        context.contentResolver, screenshot, "screenshot", null
    )
    val imageUri = Uri.parse(imagePath)

    shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri)
    context.startActivity(Intent.createChooser(shareIntent, "Share Screenshot"))
}


private fun convertBitmapToPdf(bitmap: Bitmap, context: Context) {
    val pdfDocument = PdfDocument()
    val pageInfo = PdfDocument.PageInfo.Builder(bitmap.width, bitmap.height, 1).create()
    val page = pdfDocument.startPage(pageInfo)
    page.canvas.drawBitmap(bitmap, 0F, 0F, null)
    pdfDocument.finishPage(page)
    val filePath = File(context.getExternalFilesDir(null), context.applicationContext.packageName+ getCurrentDate() +"Pdf.pdf")
    pdfDocument.writeTo(FileOutputStream(filePath))
    pdfDocument.close()

    renderSharePdf(context, filePath)
}

@RequiresApi(Build.VERSION_CODES.O)
fun getCurrentDate(): String {
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd")
    return currentDate.format(formatter)
}

private fun generateRandomThreeDigitNumber(): Int {
    val random = Random.Default
    return random.nextInt(100, 1000)  // Generates a random number between 100 and 999 (inclusive)
}

private fun renderSharePdf(context: Context, filePath: File) {
    val shareIntent = Intent(Intent.ACTION_SEND)
    shareIntent.type = "application/pdf"



    val imageUri = Uri.parse(filePath.path)

    shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri)
    context.startActivity(Intent.createChooser(shareIntent, "Share Screenshot"))



}
private fun renderOpenPdf(context: Context, filePath: File) {
    val uri = FileProvider.getUriForFile(
        context,
        context.applicationContext.packageName + ".provider",
        filePath
    )
    val intent = Intent(Intent.ACTION_VIEW)
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    intent.setDataAndType(uri, "application/pdf")

    try {
        context.startActivity(intent)
    } catch (e: ActivityNotFoundException) {

    }
}




fun TextInputEditText.transformIntoDatePicker(
    context: Context,
    format: String,
    minDate: Long,
    maxDate: Long,
    initialCalendar: Calendar
) {
    isFocusableInTouchMode = false
    isClickable = true
    isFocusable = false

    val myCalendar = Calendar.getInstance()
    val datePickerOnDataSetListener =
        DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val sdf = SimpleDateFormat(format, Locale.getDefault())
            setText(sdf.format(myCalendar.time))
        }

    setOnClickListener {
        DatePickerDialog(
            context,
            datePickerOnDataSetListener,
            initialCalendar.get(Calendar.YEAR),
            initialCalendar.get(Calendar.MONTH),
            initialCalendar.get(Calendar.DAY_OF_MONTH)
        ).run {
            datePicker.minDate = minDate
            datePicker.maxDate = maxDate

            show()
        }
    }


}


fun CircleImageView.setFirstAndLastName(fullName: String?) {
    if (fullName == null) return

    val parts = fullName.split(" ")
    if (parts.size >= 2) {
        val formattedText = "${parts[0]} ${parts[parts.size - 1]}"
        setName(formattedText.toUpperCase())
    } else {
        setName(fullName.substring(0,1).toUpperCase())
    }
}

fun drawableToBitmap(drawable: Drawable): Bitmap? {
    var bitmap: Bitmap? = null
    if (drawable is BitmapDrawable) {
        val bitmapDrawable = drawable
        if (bitmapDrawable.bitmap != null) {
            return bitmapDrawable.bitmap

        }
    }

    return bitmap
}
fun getScreenWidth(): Int {
    return Resources.getSystem().displayMetrics.widthPixels
}

fun getScreenHeight(): Int {
    return Resources.getSystem().displayMetrics.heightPixels
}