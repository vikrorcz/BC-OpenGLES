package com.bura.common.util

import com.bura.common.engine.Engine
import com.bura.common.model.Shader
import com.bura.common.shapes.Obj
import java.io.IOException
import java.io.InputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder


class ObjLoader(val engine: Engine) {

    fun loadModel(
        objPath: String,
        texture: Int,
        shaderType: Shader.Type,
        x: Float = 0f,
        y: Float = 0f,
        z: Float = 0f,
        scale: Float = 1f,
    ): Obj {
        val inputStream: InputStream = this.javaClass.classLoader.getResourceAsStream(objPath) ?: throw IOException("Classpath resource not found")
        return this.loadModel(inputStream, shaderType, texture, x, y, z,scale)
    }

    private fun loadModel(inputStream: InputStream, shaderType: Shader.Type, texture: Int, x: Float, y: Float, z: Float, scale: Float): Obj {
        val verticesTemp: MutableList<Float> = mutableListOf()
        val normalsTemp: MutableList<Float> = mutableListOf()
        val textureCoordsTemp: MutableList<Float> = mutableListOf()
        val facesTemp: MutableList<String> = mutableListOf()
        val bufferedReader = inputStream.bufferedReader()
        val iterator = bufferedReader.lineSequence().iterator()

        while (iterator.hasNext()) {
            val ln = iterator.next()
            val split = ln.split(" ")
            when (split[0]) {
                "v" -> {
                    verticesTemp.add(split[1].toFloat())
                    verticesTemp.add(split[2].toFloat())
                    verticesTemp.add(split[3].toFloat())
                }
                "vn" -> {
                    normalsTemp.add(split[1].toFloat())
                    normalsTemp.add(split[2].toFloat())
                    normalsTemp.add(split[3].toFloat())
                }
                "vt" -> {
                    textureCoordsTemp.add(split[1].toFloat())
                    textureCoordsTemp.add(-split[2].toFloat()) // Flip the texture if needed
                }
                "f" -> {
                    when (split.size) {
                        5 -> {
                            // triangle 1
                            facesTemp.add(split[1])
                            facesTemp.add(split[2])
                            facesTemp.add(split[3])
                            // triangle 2
                            facesTemp.add(split[1])
                            facesTemp.add(split[3])
                            facesTemp.add(split[4])
                        }
                        4 -> {
                            // triangle
                            facesTemp.add(split[1])
                            facesTemp.add(split[2])
                            facesTemp.add(split[3])
                        }
                        else -> {
                            println("[OBJ] Unknown face format: $ln")
                        }
                    }
                } else -> {
                    println("[OBJ] Unknown Line: $ln")
                }
            }
        }
        bufferedReader.close()

        val vertices = mutableListOf<Float>()
        val normals = mutableListOf<Float>()
        val textureCoords = mutableListOf<Float>()
        val indices = mutableListOf<Short>()

        for (face in facesTemp) {
            val indicesArray = face.split(" ")
            for (indexInfo in indicesArray) {
                val index = indexInfo.split("/")

                val vertexIndex = index[0].toInt() - 1
                vertices.add(verticesTemp[vertexIndex * 3])
                vertices.add(verticesTemp[vertexIndex * 3 + 1])
                vertices.add(verticesTemp[vertexIndex * 3 + 2])

                if (index.size > 1 && index[1].isNotEmpty()) {
                    val textureIndex = index[1].toInt() - 1
                    textureCoords.add(textureCoordsTemp[textureIndex * 2])
                    textureCoords.add(textureCoordsTemp[textureIndex * 2 + 1])
                } else {
                    // Add default or zeroed texture coordinates if missing
                    textureCoords.add(0f)
                    textureCoords.add(0f)
                }

                if (index.size > 2 && index[2].isNotEmpty()) {
                    val normalIndex = index[2].toInt() - 1
                    normals.add(normalsTemp[normalIndex * 3])
                    normals.add(normalsTemp[normalIndex * 3 + 1])
                    normals.add(normalsTemp[normalIndex * 3 + 2])
                } else {
                    // Add default or zeroed normal coordinates if missing
                    normals.add(0f)
                    normals.add(0f)
                    normals.add(0f)
                }

                indices.add(indices.size.toShort())
            }
        }

        val verticesArray = vertices.toFloatArray()
        val normalsArray = normals.toFloatArray()
        val textureCoordsArray = textureCoords.toFloatArray()
        val indicesArray = indices.toShortArray()

        val model = Obj(
            engine = engine,
            texture = texture,
            shaderType = shaderType,
            x = x,
            y = y,
            z = z,
            scale = scale,
            indices = indicesArray,
            vertexData = ByteBuffer
                .allocateDirect(verticesArray.size * Constants.BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer().apply {
                    put(verticesArray)
                    position(0)
                },
            drawListData = ByteBuffer
                .allocateDirect(indicesArray.size * 2)
                .order(ByteOrder.nativeOrder())
                .asShortBuffer().apply {
                    put(indicesArray)
                    position(0)
                },
            textureData = ByteBuffer
                .allocateDirect(textureCoordsArray.size * Constants.BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer().apply {
                    put(textureCoordsArray)
                    position(0)
                },
            normalData = ByteBuffer
                .allocateDirect(normalsArray.size * Constants.BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer().apply {
                    put(normalsArray)
                    position(0)
                }
        )
        return model
    }
}