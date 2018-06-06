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
        val programObjectId = GLES20.glCreateProgram()

        if (programObjectId == 0) {
            if (LoggerConfig.ON) {
                Log.w(TAG, "Could not create new program")
            }
            return 0
        }
        Log.d(TAG, "vertexShaderId: " + vertexShaderId)
        Log.d(TAG, "fragmentShaderId: " + fragmentShaderId)
        Log.d(TAG, "programObjectId: " + programObjectId)
        GLES20.glAttachShader(programObjectId, vertexShaderId)
        GLES20.glAttachShader(programObjectId, fragmentShaderId)
        GLES20.glLinkProgram(programObjectId)
        val linkStatus = IntArray(1)
        GLES20.glGetProgramiv(programObjectId, GLES20.GL_LINK_STATUS, linkStatus, 0)
        if (LoggerConfig.ON) {
            Log.v(TAG, "Result of linking program: \n" + GLES20.glGetProgramInfoLog(programObjectId))
        }
        if (linkStatus[0] == 0) {
            GLES20.glDeleteProgram(programObjectId)
            if (LoggerConfig.ON) {
                Log.w(TAG, "Linking of program failed")
            }
            return 0
        }
        return programObjectId
    }

    fun validateProgram(programObjectId: Int): Boolean {
        GLES20.glValidateProgram(programObjectId)
        Log.d(TAG, "program object id: $programObjectId")
        var validateStatus = IntArray(1)
        GLES20.glGetProgramiv(programObjectId, GLES20.GL_VALIDATE_STATUS, validateStatus, 0)
        Log.v(TAG, "Results of validating program: " + validateStatus[0] + "\nLog:" + GLES20
                .glGetProgramInfoLog(programObjectId))
        return validateStatus[0] != 0
    }
}