package com.luongthevinh.hockey

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLES20.GL_COLOR_BUFFER_BIT
import android.opengl.GLES20.glViewport
import android.opengl.GLSurfaceView
import android.util.Log
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
(context: Context) : GLSurfaceView.Renderer {


    var vertexData: FloatBuffer
    lateinit var context: Context

    companion object {
        val POSITION_COMPONENT_COUNT = 2
        val BYTES_PER_FLOAT = 4
    }

    override fun onDrawFrame(gl: GL10?) {
        Log.d("wtf", "onDrawFrame")
        GLES20.glClear(GL_COLOR_BUFFER_BIT)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        Log.d("wtf", "onSurfceChanged")
        glViewport(0, 0, width, height)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        Log.d("wtf", "onSurfaceCreated")
        GLES20.glClearColor(1.0f, 0.0f, 0.0f, 0.0f)
        var vertexShaderSource = TextResourceReader.readTextFileFromResource(context, R.raw
                .simple_vertex_shader)
        var fragmentShaderSource = TextResourceReader.readTextFileFromResource(context, R.raw.simple_fragment_shader)
        var vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource)
        var fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource)
    }

    init {
        var tableVerticesWithTriangles = floatArrayOf(
                // Triangle 1
                0f, 0f,
                9f, 14f,
                0f, 14f,
                // Triangle 2
                0f, 0f,
                9f, 0f,
                9f, 14f,
                // Line 1
                0f, 7f,
                9f, 7f,
                // Mallets
                4.5f, 2f,
                4.5f, 12f
        )
        vertexData = ByteBuffer.allocateDirect(tableVerticesWithTriangles.size * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
        vertexData.put(tableVerticesWithTriangles)
    }


}