package com.amosh.pulse.core.ui.extension

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Locale

fun showToastMessage(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

const val SERVER_DATE_PATTERN: String = "yyyy-MM-dd'T'HH:mm:ssX"
const val DEFAULT_DATE_FORMAT: String = "dd/MM/yyyy"

fun String?.formatDate(
    fromPattern: String = SERVER_DATE_PATTERN,
    toPattern: String = DEFAULT_DATE_FORMAT,
): String? {
    return if (this.isNullOrEmpty()) null else {
        val format: DateFormat = SimpleDateFormat(fromPattern, Locale.ENGLISH)
        val time = format.parse(this)
        return if (time != null) SimpleDateFormat(toPattern, Locale.ENGLISH).format(time) else null
    }
}

fun saveBitmapToFile(context: Context, bitmap: Bitmap): Uri {
    val file = File(context.cacheDir, "captured_image_${System.currentTimeMillis()}.jpg")
    val outputStream = FileOutputStream(file)
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
    outputStream.flush()
    outputStream.close()
    return FileProvider.getUriForFile(context, "${context.packageName}.fileProvider", file)
}

fun getFilePathFromContentUri(context: Context, contentUri: Uri): String? {
    if (contentUri.scheme == "content") {
        context.contentResolver.query(contentUri, null, null, null, null)?.use { cursor ->
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (cursor.moveToFirst() && nameIndex >= 0) {
                val fileName = cursor.getString(nameIndex)
                val tempFile = File(context.cacheDir, fileName)

                context.contentResolver.openInputStream(contentUri)?.use { inputStream ->
                    tempFile.outputStream().use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }
                return tempFile.absolutePath
            }
        }
    }

    if (contentUri.scheme == "file") {
        return contentUri.path
    }

    return null
}