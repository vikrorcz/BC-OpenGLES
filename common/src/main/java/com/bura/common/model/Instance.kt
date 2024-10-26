package com.bura.common.model

import com.bura.common.engine.Engine
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
}