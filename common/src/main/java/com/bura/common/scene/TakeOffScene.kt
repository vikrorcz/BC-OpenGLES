package com.bura.common.scene

import com.bura.common.engine.Engine
import com.bura.common.shapes.Shape

class TakeOffScene(val engine: Engine): Scene() {

    private val terrain = engine.instance.terrain.clone().apply { scale = 80f; y = -10.0f }
    private val misc = createMiscShapes()

    init {
        shapeArray = mutableListOf(terrain)
        shapeArray.addAll(misc)
    }

    override fun draw() {
        shapeArray.forEach { shape ->
            engine.matrixUtil.updateMatrix(shape)
            shape.draw()
            engine.matrixUtil.restoreMatrix()
        }
    }

    override fun updateCamera() {

    }

    override fun updateLogic() {

    }

    private fun createMiscShapes(): MutableList<Shape> = mutableListOf(
        engine.instance.pine.clone().apply { x = -173.95451f; y = 15.0f; z = -1411.4839f; scale = 281.0f; rotationX = 0.0f; rotationY = 340.0f; rotationZ = 0.0f },
        engine.instance.pine.clone().apply { x = 182.22299f; y = 66.0f; z = -1696.5493f; scale = 199.0f; rotationX = 0.0f; rotationY = 54.0f; rotationZ = 0.0f },
        engine.instance.pine.clone().apply { x = 338.9205f; y = 107.0f; z = -1582.5955f; scale = 269.0f; rotationX = 0.0f; rotationY = 39.0f; rotationZ = 0.0f },
        engine.instance.pine.clone().apply { x = 530.5096f; y = 52.0f; z = -1288.8083f; scale = 232.0f; rotationX = 0.0f; rotationY = 169.0f; rotationZ = 0.0f },
        engine.instance.pine.clone().apply { x = 200.15883f; y = -33.0f; z = -1169.7777f; scale = 141.0f; rotationX = 0.0f; rotationY = 336.0f; rotationZ = 0.0f },
        engine.instance.pine.clone().apply { x = -718.9526f; y = 44.0f; z = -1313.9432f; scale = 300.0f; rotationX = 0.0f; rotationY = 211.0f; rotationZ = 0.0f },
        engine.instance.pine.clone().apply { x = -489.15704f; y = -4.0f; z = -1624.3853f; scale = 215.0f; rotationX = 0.0f; rotationY = 32.0f; rotationZ = 0.0f },
        engine.instance.pine.clone().apply { x = -436.67664f; y = -70.0f; z = -1258.7468f; scale = 128.0f; rotationX = 0.0f; rotationY = 48.0f; rotationZ = 0.0f },
        engine.instance.pine.clone().apply { x = 390.48978f; y = 45.0f; z = -847.3506f; scale = 392.0f; rotationX = 0.0f; rotationY = 5.0f; rotationZ = 0.0f },
        engine.instance.pine.clone().apply { x = 679.5029f; y = 138.0f; z = -1538.7968f; scale = 246.0f; rotationX = 0.0f; rotationY = 64.0f; rotationZ = 0.0f },
        engine.instance.pine.clone().apply { x = 501.2902f; y = 22.0f; z = -351.81857f; scale = 300.0f; rotationX = 0.0f; rotationY = 129.0f; rotationZ = 0.0f },
        engine.instance.pine.clone().apply { x = 903.9864f; y = 42.0f; z = -621.3665f; scale = 470.0f; rotationX = 0.0f; rotationY = 351.0f; rotationZ = 0.0f },
        engine.instance.pine.clone().apply { x = 886.261f; y = 122.0f; z = -1039.3875f; scale = 504.0f; rotationX = 0.0f; rotationY = 144.0f; rotationZ = 0.0f },
        engine.instance.pine.clone().apply { x = 369.33798f; y = -39.0f; z = -552.3141f; scale = 164.0f; rotationX = 0.0f; rotationY = 77.0f; rotationZ = 0.0f },
        engine.instance.pine.clone().apply { x = 215.79208f; y = 192.0f; z = 501.7593f; scale = 259.0f; rotationX = 0.0f; rotationY = 89.0f; rotationZ = 0.0f },
        engine.instance.pine.clone().apply { x = 294.50763f; y = 96.0f; z = 295.08142f; scale = 155.0f; rotationX = 0.0f; rotationY = 156.0f; rotationZ = 0.0f },
        engine.instance.pine.clone().apply { x = 8.414143f; y = 148.0f; z = 455.38007f; scale = 184.0f; rotationX = 0.0f; rotationY = 220.0f; rotationZ = 0.0f },
        engine.instance.pine.clone().apply { x = 631.82446f; y = 128.0f; z = 7.9077044f; scale = 477.0f; rotationX = 0.0f; rotationY = 354.0f; rotationZ = 0.0f },
        engine.instance.pine.clone().apply { x = 777.9851f; y = 229.0f; z = 449.59198f; scale = 610.0f; rotationX = 0.0f; rotationY = 312.0f; rotationZ = 0.0f },
        engine.instance.pine.clone().apply { x = -131.01999f; y = 275.0f; z = 779.92566f; scale = 420.0f; rotationX = 0.0f; rotationY = 244.0f; rotationZ = 0.0f },
        engine.instance.pine.clone().apply { x = -259.3806f; y = 181.0f; z = 531.72876f; scale = 325.0f; rotationX = 0.0f; rotationY = 256.0f; rotationZ = 0.0f },
        engine.instance.pine.clone().apply { x = -616.15173f; y = 145.0f; z = 502.7574f; scale = 240.0f; rotationX = 0.0f; rotationY = 47.0f; rotationZ = 0.0f },
        engine.instance.pine.clone().apply { x = -905.1302f; y = 125.0f; z = 115.38151f; scale = 326.0f; rotationX = 0.0f; rotationY = 136.0f; rotationZ = 0.0f },
        engine.instance.pine.clone().apply { x = -1210.7806f; y = 78.0f; z = 308.50967f; scale = 245.0f; rotationX = 0.0f; rotationY = 99.0f; rotationZ = 0.0f },
        engine.instance.pine.clone().apply { x = -1000.84406f; y = 166.0f; z = 733.25183f; scale = 324.0f; rotationX = 0.0f; rotationY = 156.0f; rotationZ = 0.0f },
        engine.instance.pine.clone().apply { x = -576.04266f; y = 270.0f; z = 1027.1989f; scale = 366.0f; rotationX = 0.0f; rotationY = 88.0f; rotationZ = 0.0f },
        engine.instance.pine.clone().apply { x = -251.95604f; y = 264.0f; z = 1165.5463f; scale = 270.0f; rotationX = 0.0f; rotationY = 44.0f; rotationZ = 0.0f },
        engine.instance.pine.clone().apply { x = 262.15656f; y = 291.0f; z = 942.15173f; scale = 278.0f; rotationX = 0.0f; rotationY = 129.0f; rotationZ = 0.0f },
        engine.instance.rock.clone().apply { x = -192.35999f; y = 7.0f; z = 132.63f; scale = 273.0f; rotationX = 0.0f; rotationY = 86.0f; rotationZ = 0.0f },
        engine.instance.rock.clone().apply { x = -783.73236f; y = 2.0f; z = 155.57175f; scale = 189.0f; rotationX = 0.0f; rotationY = 50.0f; rotationZ = 0.0f },
        engine.instance.rock.clone().apply { x = -135.49516f; y = 25.0f; z = 252.65904f; scale = 211.0f; rotationX = 0.0f; rotationY = 16.0f; rotationZ = 0.0f },
        engine.instance.rock.clone().apply { x = -60.042213f; y = 15.0f; z = 188.34572f; scale = 166.0f; rotationX = 0.0f; rotationY = 242.0f; rotationZ = 0.0f },
        engine.instance.rock.clone().apply { x = -359.0974f; y = -119.0f; z = -1174.3695f; scale = 282.0f; rotationX = 0.0f; rotationY = 188.0f; rotationZ = 0.0f },
        engine.instance.rock.clone().apply { x = -243.47064f; y = -131.0f; z = -1159.4114f; scale = 191.0f; rotationX = 0.0f; rotationY = 44.0f; rotationZ = 0.0f },
        engine.instance.rock.clone().apply { x = 226.30841f; y = -80.0f; z = -1192.6766f; scale = 133.0f; rotationX = 0.0f; rotationY = 360.0f; rotationZ = 0.0f },
        engine.instance.rock.clone().apply { x = 408.5929f; y = -98.0f; z = -918.37787f; scale = 219.0f; rotationX = 0.0f; rotationY = 158.0f; rotationZ = 0.0f },
        engine.instance.rock.clone().apply { x = 335.71265f; y = -109.0f; z = -785.7368f; scale = 219.0f; rotationX = 0.0f; rotationY = 18.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -645.1647f; y = -82.98365f; z = -571.5717f; scale = 9.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -640.0295f; y = -83.44315f; z = -577.32074f; scale = 9.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -645.25885f; y = -82.98235f; z = -571.8771f; scale = 9.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -629.59595f; y = -83.79799f; z = -555.84717f; scale = 9.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -671.67596f; y = -81.580345f; z = -597.3986f; scale = 9.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -643.0537f; y = -83.10021f; z = -569.78894f; scale = 9.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -632.2959f; y = -84.125656f; z = -585.433f; scale = 9.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -641.9349f; y = -83.050476f; z = -562.458f; scale = 9.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -632.34265f; y = -83.750175f; z = -564.117f; scale = 9.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -676.76056f; y = -81.1861f; z = -595.19073f; scale = 9.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -645.7993f; y = -86.500374f; z = -621.4155f; scale = 11.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -679.7457f; y = -85.31495f; z = -653.09753f; scale = 11.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -669.51056f; y = -85.67237f; z = -635.9267f; scale = 11.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -647.7842f; y = -86.43108f; z = -638.6452f; scale = 11.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -623.7212f; y = -87.271355f; z = -632.2214f; scale = 11.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -652.5521f; y = -86.26458f; z = -630.9315f; scale = 11.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -676.04663f; y = -85.44414f; z = -639.8317f; scale = 11.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -666.8101f; y = -85.766655f; z = -638.1455f; scale = 11.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -654.36426f; y = -86.20127f; z = -608.94354f; scale = 11.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -684.9505f; y = -85.13317f; z = -680.41296f; scale = 11.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -610.6739f; y = -98.39906f; z = -698.0613f; scale = 17.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -567.15735f; y = -106.07214f; z = -728.60266f; scale = 17.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -647.8067f; y = -91.85153f; z = -696.4108f; scale = 17.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -611.42566f; y = -98.266495f; z = -664.7182f; scale = 17.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -566.9173f; y = -106.1145f; z = -696.7894f; scale = 17.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -605.54395f; y = -99.30358f; z = -677.0027f; scale = 17.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -599.70734f; y = -100.33272f; z = -706.2163f; scale = 17.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -627.5426f; y = -95.42463f; z = -711.00653f; scale = 17.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -608.83356f; y = -98.72357f; z = -690.19116f; scale = 17.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -627.0481f; y = -95.51179f; z = -731.2997f; scale = 17.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -578.05396f; y = -96.31f; z = -648.0891f; scale = 18.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -571.65845f; y = -96.9822f; z = -661.8178f; scale = 18.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -577.6296f; y = -96.35456f; z = -623.7387f; scale = 18.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -586.37866f; y = -95.435f; z = -619.13745f; scale = 18.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -598.344f; y = -94.17742f; z = -632.42017f; scale = 18.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -577.4762f; y = -96.37071f; z = -649.01337f; scale = 18.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -553.40546f; y = -98.90066f; z = -637.85333f; scale = 18.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -575.5746f; y = -96.570564f; z = -618.0618f; scale = 18.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -611.59564f; y = -92.78461f; z = -627.40027f; scale = 18.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -579.9153f; y = -96.11431f; z = -628.1433f; scale = 18.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -820.26935f; y = -55.194027f; z = -515.2468f; scale = 15.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -846.1281f; y = -52.47619f; z = -494.5731f; scale = 15.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -835.28174f; y = -53.61615f; z = -469.81f; scale = 15.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -865.44794f; y = -50.445553f; z = -526.9201f; scale = 15.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -847.00275f; y = -52.38423f; z = -476.44128f; scale = 15.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -828.1579f; y = -54.36489f; z = -501.3067f; scale = 15.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -855.97327f; y = -51.44139f; z = -477.9475f; scale = 15.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -845.7914f; y = -52.511547f; z = -497.59564f; scale = 15.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -840.18195f; y = -53.10113f; z = -495.01898f; scale = 15.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -827.20624f; y = -54.464924f; z = -489.73282f; scale = 15.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -828.1409f; y = -54.20047f; z = -555.3929f; scale = 15.0f; rotationX = 7.0f; rotationY = 0.0f; rotationZ = -10.0f },
        engine.instance.bush.clone().apply { x = -833.46643f; y = -53.261436f; z = -533.3408f; scale = 15.0f; rotationX = 7.0f; rotationY = 0.0f; rotationZ = -10.0f },
        engine.instance.bush.clone().apply { x = -847.0654f; y = -50.86355f; z = -574.59186f; scale = 15.0f; rotationX = 7.0f; rotationY = 0.0f; rotationZ = -10.0f },
        engine.instance.bush.clone().apply { x = -841.52185f; y = -51.841072f; z = -538.4031f; scale = 15.0f; rotationX = 7.0f; rotationY = 0.0f; rotationZ = -10.0f },
        engine.instance.bush.clone().apply { x = -850.28937f; y = -50.295105f; z = -554.56866f; scale = 15.0f; rotationX = 7.0f; rotationY = 0.0f; rotationZ = -10.0f },
        engine.instance.bush.clone().apply { x = -828.5592f; y = -54.126724f; z = -553.1996f; scale = 15.0f; rotationX = 7.0f; rotationY = 0.0f; rotationZ = -10.0f },
        engine.instance.bush.clone().apply { x = -842.17017f; y = -51.726715f; z = -553.6773f; scale = 15.0f; rotationX = 7.0f; rotationY = 0.0f; rotationZ = -10.0f },
        engine.instance.bush.clone().apply { x = -798.7045f; y = -59.390884f; z = -566.3994f; scale = 15.0f; rotationX = 7.0f; rotationY = 0.0f; rotationZ = -10.0f },
        engine.instance.bush.clone().apply { x = -809.42786f; y = -57.500084f; z = -574.83f; scale = 15.0f; rotationX = 7.0f; rotationY = 0.0f; rotationZ = -10.0f },
        engine.instance.bush.clone().apply { x = -870.1454f; y = -46.79394f; z = -590.40894f; scale = 15.0f; rotationX = 7.0f; rotationY = 0.0f; rotationZ = -10.0f },
        engine.instance.bush.clone().apply { x = -798.4298f; y = -59.112114f; z = -535.6199f; scale = 18.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = -8.0f },
        engine.instance.bush.clone().apply { x = -771.12463f; y = -61.982002f; z = -487.00586f; scale = 18.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = -8.0f },
        engine.instance.bush.clone().apply { x = -773.71106f; y = -61.710155f; z = -490.7527f; scale = 18.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = -8.0f },
        engine.instance.bush.clone().apply { x = -769.23206f; y = -62.180916f; z = -471.9091f; scale = 18.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = -8.0f },
        engine.instance.bush.clone().apply { x = -789.10406f; y = -60.092274f; z = -499.81393f; scale = 18.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = -8.0f },
        engine.instance.bush.clone().apply { x = -794.4452f; y = -59.5309f; z = -495.27332f; scale = 18.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = -8.0f },
        engine.instance.bush.clone().apply { x = -780.2398f; y = -61.023968f; z = -506.53677f; scale = 18.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = -8.0f },
        engine.instance.bush.clone().apply { x = -767.64154f; y = -62.34809f; z = -487.91223f; scale = 18.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = -8.0f },
        engine.instance.bush.clone().apply { x = -787.7653f; y = -60.23299f; z = -499.05652f; scale = 18.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = -8.0f },
        engine.instance.bush.clone().apply { x = -772.98364f; y = -61.786602f; z = -499.72513f; scale = 18.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = -8.0f },
        engine.instance.bush.clone().apply { x = -799.95306f; y = -50.52015f; z = -411.7061f; scale = 24.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -857.57275f; y = -45.355133f; z = -422.28995f; scale = 24.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -780.8358f; y = -53.5455f; z = -427.43103f; scale = 24.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -832.29596f; y = -48.850414f; z = -435.67645f; scale = 24.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -832.5291f; y = -49.146004f; z = -440.362f; scale = 24.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -826.4124f; y = -49.75456f; z = -440.12167f; scale = 24.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -829.20917f; y = -48.768497f; z = -429.85034f; scale = 24.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -820.2892f; y = -49.79584f; z = -431.55222f; scale = 24.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -868.6401f; y = -44.821163f; z = -431.04123f; scale = 24.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -849.37195f; y = -49.442688f; z = -469.94724f; scale = 24.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -745.7597f; y = -62.01104f; z = -424.02185f; scale = 15.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -728.6158f; y = -66.5748f; z = -460.88208f; scale = 15.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -724.9506f; y = -67.14887f; z = -463.48334f; scale = 15.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -728.9392f; y = -67.63025f; z = -475.193f; scale = 15.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -708.04895f; y = -65.98881f; z = -425.42776f; scale = 15.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -722.26184f; y = -64.854675f; z = -429.69714f; scale = 15.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -737.7966f; y = -68.71486f; z = -501.40292f; scale = 15.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -731.1264f; y = -67.4141f; z = -475.3004f; scale = 15.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -730.07196f; y = -69.44668f; z = -500.5987f; scale = 15.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        engine.instance.bush.clone().apply { x = -703.573f; y = -70.21599f; z = -474.95682f; scale = 15.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
    )
}