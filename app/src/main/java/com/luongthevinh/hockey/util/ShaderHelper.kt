package com.luongthevinh.hockey.util

import android.opengl.GLES20
import android.util.Log

object ShaderHelper {
    const val TAG = "ShaderHelper"

    fun compileVertexShader(shaderCode: String): Int {
        return compileShader(GLES20.GL_VERTEX_SHADER, shaderCode)
    }

    fun compileFragmentShader(shaderCode: String): Int {
        return compileShader(GLES20.GL_FRAGMENT_SHADER, shaderCode)
    }

    fun compileShader(type: Int, shaderCode: String): Int {
        val shaderObjectId = GLES20.glCreateShader(type)
        if (shaderObjectId == 0) {
            if (LoggerConfig.ON) {
                Log.w(TAG, "Could not create new shader.")
            }
            return 0
        }
        GLES20.glShaderSource(shaderObjectId, shaderCode)
        GLES20.glCompileShader(shaderObjectId)
        val compileStatus = IntArray(1)
        GLES20.glGetShaderiv(shaderObjectId, GLES20.GL_COMPILE_STATUS, compileStatus, 0)
        if (LoggerConfig.ON) {
            Log.v(TAG, "Results of compiling source:" + "/n" + shaderCode + "/n" + GLES20
                    .glGetShaderInfoLog(shaderObjectId))
        }
        if (compileStatus[0] == 0) {
            GLES20.glDeleteShader(shaderObjectId)
            if (LoggerConfig.ON) {
                Log.w(TAG, "Compilation of shader failed.")
            }
            return 0
        }
        return shaderObjectId
    }

    fun linkProgram(vertexShaderId: Int, fragmentShaderId: Int): Int {

    }
}