package com.luongthevinh.hockey.util

import android.content.Context
import android.content.res.Resources
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.RuntimeException

object TextResourceReader {

    fun readTextFileFromResource(context: Context, resourceId: Int): String {
        var body = StringBuilder()
        try {
            var inputStream = context.resources.openRawResource(resourceId)
            var inputStreamReader = InputStreamReader(inputStream)
            var bufferedReader = BufferedReader(inputStreamReader)
            var nextLine = bufferedReader.readLine()
            while (nextLine != null) {
                body.append(nextLine)
                body.append('\n')
                nextLine = bufferedReader.readLine()
            }
        } catch (exception: IOException) {
            throw RuntimeException("Could not open resource: $resourceId", exception)
        } catch (nfe: Resources.NotFoundException) {
            throw RuntimeException("Resource not found: $resourceId", nfe)
        }
        return body.toString()
    }

}
