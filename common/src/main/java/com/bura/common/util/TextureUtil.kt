package com.bura.common.util

import com.bura.common.engine.Engine.Companion.gles20


interface TextureUtil {

    companion object {
        private const val path = "images/"

        private val textureLocation = listOf(
            "${path}grass.jpg",
            "${path}pine.png",
            "${path}rock.jpg",
            "${path}bush.png",
            "${path}skybox1.png",
            "${path}ship.png",
        )

        private val textures: List<Texture> = buildList {
            textureLocation.forEachIndexed { index, location ->
                add(Texture(index, location))
            }
        }
        private data class Texture(
            var id: Int,
            val name: String,
        )
        fun getTextureIdByName(name: String): Int {
            val path = "$path$name"
            return textures.find { it.name == path }?.id ?: 0
        }
    }

    fun createTextures() {
        textures.forEachIndexed { index, texture ->
            if (index > 32) throw Exception("Cannot bind more than 32 textures")

            gles20.glActiveTexture(GLES20.GL_TEXTURE0 + index)
            gles20.glBindTexture(GLES20.GL_TEXTURE_2D, loadTexture(texture.name))
        }
    }

    fun loadTexture(resourceLocation: String): Int
}