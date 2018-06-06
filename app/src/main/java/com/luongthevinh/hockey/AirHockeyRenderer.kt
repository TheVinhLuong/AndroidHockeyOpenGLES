package com.luongthevinh.hockey

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import android.util.Log
import com.luongthevinh.hockey.util.LoggerConfig
import com.luongthevinh.hockey.util.ShaderHelper
import com.luongthevinh.hockey.util.TextResourceReader
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class AirHockeyRenderer// Triangle 1
// Triangle 2
// Line 1
// Mallets
(val context: Context) : GLSurfaceView.Renderer {


    var vertexData: FloatBuffer
    var program = 0
    var uColorLocation = 0
    var aPositionLocation = 0

    companion object {
        val POSITION_COMPONENT_COUNT = 2
        val BYTES_PER_FLOAT = 4
        val U_COLOR = "u_Color"
        val A_POSITION = "a_Position"
    }

    override fun onDrawFrame(gl: GL10?) {
        Log.d("wtf", "onDrawFrame")
        GLES20.glClear(GL_COLOR_BUFFER_BIT)
        glUniform4f(uColorLocation, 0.0f, 1.0f, 0.0f, 1.0f)
        glDrawArrays(GL_TRIANGLES, 0, 6)
        glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 1.0f)
        glDrawArrays(GL_TRIANGLES, 6, 6)
        glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f)
        glDrawArrays(GL_LINES, 12, 2)
        //Draw the first mallet blue
        glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f)
        glDrawArrays(GL_POINTS, 14, 1)
        //Draw the second mallet red
        glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f)
        glDrawArrays(GL_POINTS, 15, 1)
        //Draw the puck
        glUniform4f(uColorLocation, 0f, 0f, 0f, 1.0f)
        glDrawArrays(GL_POINTS, 16, 1)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        Log.d("wtf", "onSurfceChanged")
        glViewport(0, 0, width, height)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        Log.d("wtf", "onSurfaceCreated")
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f)
        var vertexShaderSource = TextResourceReader.readTextFileFromResource(context, R.raw
                .simple_vertex_shader)
        var fragmentShaderSource = TextResourceReader.readTextFileFromResource(context, R.raw.simple_fragment_shader)
        var vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource)
        var fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource)
        program = ShaderHelper.linkProgram(vertexShader, fragmentShader)
        if (LoggerConfig.ON) {
            ShaderHelper.validateProgram(program)
        }
        glUseProgram(program)
        uColorLocation = glGetUniformLocation(program, U_COLOR)
        aPositionLocation = glGetAttribLocation(program, A_POSITION)
        vertexData.position(0)
        glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT, false, 0, vertexData)
        glEnableVertexAttribArray(aPositionLocation)
    }

    init {
        var tableVerticesWithTriangles = floatArrayOf(
                // Triangle 1
                -0.55f, -0.55f,
                0.55f, 0.55f,
                -0.55f, 0.55f,
                // Triangle 2
                -0.55f, -0.55f,
                0.55f, -0.55f,
                0.55f, 0.55f,
                // Triangle 1
                -0.5f, -0.5f,
                0.5f, 0.5f,
                -0.5f, 0.5f,
                // Triangle 2
                -0.5f, -0.5f,
                0.5f, -0.5f,
                0.5f, 0.5f,
                // Line 1
                -0.5f, 0f,
                0.5f, 0f,
                // Mallets
                0f, -0.25f,
                0f, 0.25f,
                //Puck
                0f, 0f
        )
        vertexData = ByteBuffer.allocateDirect(tableVerticesWithTriangles.size * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
        vertexData.put(tableVerticesWithTriangles)
    }


}