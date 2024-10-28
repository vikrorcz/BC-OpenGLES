package com.bura.common.model

import com.bura.common.engine.Engine
import com.bura.common.shapes.Rectangle
import com.bura.common.shapes.Texture
import com.bura.common.shapes.Triangle


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
}