package com.bura.common.util

import com.bura.common.engine.Engine
import com.bura.common.shapes.Shape

class MatrixUtil(private val engine: Engine) {
    fun updateMatrix(shape: Shape) {
        Matrix4f.setIdentityM(shape.mModelMatrix, 0)
        Matrix4f.translateM(shape.mModelMatrix, 0, shape.x, shape.y, shape.z)

        Matrix4f.scaleM(shape.mModelMatrix, 0, shape.scale, shape.scale, shape.scale) // Check if this didn't break anything

        Matrix4f.setIdentityM(shape.rotationMatrix, 0)

        if (shape.rotationZ != 0f) {
            Matrix4f.setRotateM(
                shape.mTempRotationMatrix, 0,
                shape.rotationZ,
                0f, 0f, 1f,
            )
            Matrix4f.multiplyMM(shape.rotationMatrix, shape.mTempRotationMatrix, shape.rotationMatrix)
        }

        if (shape.rotationX != 0f) {
            Matrix4f.setRotateM(
                shape.mTempRotationMatrix, 0,
                shape.rotationX,
                1f, 0f, 0f,
            )
            Matrix4f.multiplyMM(shape.rotationMatrix, shape.mTempRotationMatrix, shape.rotationMatrix)
        }

        if (shape.rotationY != 0f) {
            Matrix4f.setRotateM(
                shape.mTempRotationMatrix, 0,
                shape.rotationY,
                0f, 1f, 0f,
            )
            Matrix4f.multiplyMM(shape.rotationMatrix, shape.mTempRotationMatrix, shape.rotationMatrix)
        }

        Matrix4f.translateM(shape.rotationMatrix, 0, -shape.initialX + shape.pivotX, -shape.initialY + shape.pivotY, -shape.initialZ + shape.pivotZ)

        shape.mTempMatrix = shape.mModelMatrix.clone()
        Matrix4f.multiplyMM(shape.mModelMatrix, shape.mTempMatrix, shape.rotationMatrix)

        shape.mTempMatrix = engine.vPMatrix.clone()
        Matrix4f.multiplyMM(engine.vPMatrix, shape.mTempMatrix, shape.mModelMatrix)
    }

    fun restoreMatrix() {
        Matrix4f.multiplyMM(engine.vPMatrix, engine.projectionMatrix, engine.viewMatrix)
    }
}