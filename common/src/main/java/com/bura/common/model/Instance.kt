package com.bura.common.model

import com.bura.common.engine.Engine
import com.bura.common.shapes.Rectangle
import com.bura.common.shapes.Texture
import com.bura.common.shapes.Triangle
import com.bura.common.util.TextureUtil


class Instance(
    engine: Engine,
) {
    val triangle = Triangle(
        engine = engine,
        scale = 1.0f,
        x = 0.0f,
        y = 0.0f,
        z = 0.0f,
    )

    val rectangle = Rectangle(
        engine = engine,
        scale = 1.0f,
        x = 0.0f,
        y = 0.0f,
        z = 0.0f,
    )

    val texture = Texture(
        engine = engine,
        x = 0.0f,
        y = 0.0f,
        z = 0.0f,
        resourceId = 0,
    )

    val terrain = engine.objLoader.loadModel(
        objPath = "models/terrain.obj",
        texture = TextureUtil.getTextureIdByName("grass.jpg"),
        shaderType = engine.shader.texture,
    )

    val pine = engine.objLoader.loadModel(
        objPath = "models/pine.obj",
        texture = TextureUtil.getTextureIdByName("pine.png"),
        shaderType = engine.shader.texture,
    )

    val rock = engine.objLoader.loadModel(
        objPath =  "models/rock.obj",
        texture = TextureUtil.getTextureIdByName("rock.jpg"),
        shaderType = engine.shader.texture,
    )

    val bush = engine.objLoader.loadModel(
        objPath =  "models/bush.obj",
        texture = TextureUtil.getTextureIdByName("bush.png"),
        shaderType = engine.shader.texture,
    )

    val takeOffSkyBox = engine.objLoader.loadModel(
        objPath = "models/cube.obj",
        texture = TextureUtil.getTextureIdByName("skybox1.png"),
        shaderType = engine.shader.skybox,
    )

    val spaceSkyBox = engine.objLoader.loadModel(
        objPath = "models/cube.obj",
        texture = TextureUtil.getTextureIdByName("skybox2.png"),
        shaderType = engine.shader.skybox,
    )

    val landingSkyBox = engine.objLoader.loadModel(
        objPath = "models/cube.obj",
        texture = TextureUtil.getTextureIdByName("skybox3.jpg"),
        shaderType = engine.shader.skybox,
    )

    val ship = engine.objLoader.loadModel(
        objPath = "models/ship.obj",
        texture = TextureUtil.getTextureIdByName("ship.png"),
        shaderType = engine.shader.texture,
    )

    val saturn = engine.objLoader.loadModel(
        objPath = "models/saturn.obj",
        texture = TextureUtil.getTextureIdByName("saturn.jpg"),
        shaderType = engine.shader.texture,
    )

    val asteroid = engine.objLoader.loadModel(
        objPath =  "models/asteroid.obj",
        texture = TextureUtil.getTextureIdByName("asteroid.jpg"),
        shaderType = engine.shader.texture,
    )

    val water = engine.objLoader.loadModel(
        objPath =  "models/water.obj",
        texture = TextureUtil.getTextureIdByName("water.jpg"),
        shaderType = engine.shader.water,
    )

    val island = engine.objLoader.loadModel(
        objPath = "models/island.obj",
        texture = TextureUtil.getTextureIdByName("sand.jpg"),
        shaderType = engine.shader.texture,
    )

    val palm = engine.objLoader.loadModel(
        objPath = "models/palm.obj",
        texture = TextureUtil.getTextureIdByName("palm.png"),
        shaderType = engine.shader.texture,
    )
}