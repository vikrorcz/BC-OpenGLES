package com.bura.common.util;

/**
 * Identical implementation of Android Matrix class
 * Class works in both desktop and android projects.
 * source: <a href="https://arm-software.github.io/opengl-es-sdk-for-android/simple_cube.html">...</a>
 * source: <a href="https://android.googlesource.com/platform/frameworks/base/+/6d2c0e5/opengl/java/android/opengl/Matrix.java">...</a>
 */

public class Matrix4f {

     public static void setIdentityM(float[] sm, int smOffset) {
         for (int i=0 ; i<16 ; i++) {
             sm[smOffset + i] = 0;
         }
         for(int i = 0; i < 16; i += 5) {
             sm[smOffset + i] = 1.0f;
         }
     }

     public static void copy(float[] destination, float[] copy) {
         System.arraycopy(copy, 0, destination, 0, 16);
     }

    public static float length(float x, float y, float z) {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    public static void setLookAt(float[] matrix, float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ, float upX, float upY, float upZ) {
        float fx = centerX - eyeX;
        float fy = centerY - eyeY;
        float fz = centerZ - eyeZ;
        // Normalize f
        float rlf = 1.0f / length(fx, fy, fz);
        fx *= rlf;
        fy *= rlf;
        fz *= rlf;
        // compute s = f x up (x means "cross product")
        float sx = fy * upZ - fz * upY;
        float sy = fz * upX - fx * upZ;
        float sz = fx * upY - fy * upX;
        // and normalize s
        float rls = 1.0f / length(sx, sy, sz);
        sx *= rls;
        sy *= rls;
        sz *= rls;
        // compute u = s x f
        float ux = sy * fz - sz * fy;
        float uy = sz * fx - sx * fz;
        float uz = sx * fy - sy * fx;

        //float[] result = matrix4f.toArray();

        matrix[0] = sx;
        matrix[1] = ux;
        matrix[2] = -fx;
        matrix[3] = 0.0f;

        matrix[4] = sy;
        matrix[5] = uy;
        matrix[6] = -fy;
        matrix[7] = 0.0f;

        matrix[8] = sz;
        matrix[9] = uz;
        matrix[10] = -fz;
        matrix[11] = 0.0f;

        matrix[12] = 0.0f;
        matrix[13] = 0.0f;
        matrix[14] = 0.0f;
        matrix[15] = 1.0f;
        translateM(matrix, 0, -eyeX, -eyeY, -eyeZ);

    }

    public static void setRotateM(float[] rm, int rmOffset, float a, float x, float y, float z) {
        rm[rmOffset + 3] = 0;
        rm[rmOffset + 7] = 0;
        rm[rmOffset + 11]= 0;
        rm[rmOffset + 12]= 0;
        rm[rmOffset + 13]= 0;
        rm[rmOffset + 14]= 0;
        rm[rmOffset + 15]= 1;
        a *= (float) (Math.PI / 180.0f);
        float s = (float) Math.sin(a);
        float c = (float) Math.cos(a);
        if (1.0f == x && 0.0f == y && 0.0f == z) {
            rm[rmOffset + 5] = c;   rm[rmOffset + 10]= c;
            rm[rmOffset + 6] = s;   rm[rmOffset + 9] = -s;
            rm[rmOffset + 1] = 0;   rm[rmOffset + 2] = 0;
            rm[rmOffset + 4] = 0;   rm[rmOffset + 8] = 0;
            rm[rmOffset + 0] = 1;
        } else if (0.0f == x && 1.0f == y && 0.0f == z) {
            rm[rmOffset + 0] = c;   rm[rmOffset + 10]= c;
            rm[rmOffset + 8] = s;   rm[rmOffset + 2] = -s;
            rm[rmOffset + 1] = 0;   rm[rmOffset + 4] = 0;
            rm[rmOffset + 6] = 0;   rm[rmOffset + 9] = 0;
            rm[rmOffset + 5] = 1;
        } else if (0.0f == x && 0.0f == y && 1.0f == z) {
            rm[rmOffset + 0] = c;   rm[rmOffset + 5] = c;
            rm[rmOffset + 1] = s;   rm[rmOffset + 4] = -s;
            rm[rmOffset + 2] = 0;   rm[rmOffset + 6] = 0;
            rm[rmOffset + 8] = 0;   rm[rmOffset + 9] = 0;
            rm[rmOffset + 10]= 1;
        } else {
            float len = length(x, y, z);
            if (1.0f != len) {
                float recipLen = 1.0f / len;
                x *= recipLen;
                y *= recipLen;
                z *= recipLen;
            }
            float nc = 1.0f - c;
            float xy = x * y;
            float yz = y * z;
            float zx = z * x;
            float xs = x * s;
            float ys = y * s;
            float zs = z * s;
            rm[rmOffset +  0] = x*x*nc +  c;
            rm[rmOffset +  4] =  xy*nc - zs;
            rm[rmOffset +  8] =  zx*nc + ys;
            rm[rmOffset +  1] =  xy*nc + zs;
            rm[rmOffset +  5] = y*y*nc +  c;
            rm[rmOffset +  9] =  yz*nc - xs;
            rm[rmOffset +  2] =  zx*nc - ys;
            rm[rmOffset +  6] =  yz*nc + xs;
            rm[rmOffset + 10] = z*z*nc +  c;
        }
    }

    public static void translateM(float[] m, int mOffset, float x, float y, float z) {
        for (int i=0 ; i<4 ; i++) {
            int mi = mOffset + i;
            m[12 + mi] += m[mi] * x + m[4 + mi] * y + m[8 + mi] * z;
        }
    }

    public static void transposeM(float[] mTrans, int mTransOffset, float[] m, int mOffset) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                mTrans[mTransOffset + j * 4 + i] = m[mOffset + i * 4 + j];
            }
        }
    }

    public static void invertM(float[] mInv, int mInvOffset, float[] m, int mOffset) {
        float det = m[mOffset] * (m[mOffset + 5] * m[mOffset + 10] - m[mOffset + 9] * m[mOffset + 6])
                - m[mOffset + 1] * (m[mOffset + 4] * m[mOffset + 10] - m[mOffset + 8] * m[mOffset + 6])
                + m[mOffset + 2] * (m[mOffset + 4] * m[mOffset + 9] - m[mOffset + 8] * m[mOffset + 5]);

        if (det == 0.0f) {
            throw new IllegalArgumentException("Matrix is not invertible");
        }

        float invDet = 1.0f / det;

        mInv[mInvOffset] = (m[mOffset + 5] * m[mOffset + 10] - m[mOffset + 9] * m[mOffset + 6]) * invDet;
        mInv[mInvOffset + 1] = (m[mOffset + 2] * m[mOffset + 9] - m[mOffset + 1] * m[mOffset + 10]) * invDet;
        mInv[mInvOffset + 2] = (m[mOffset + 1] * m[mOffset + 6] - m[mOffset + 2] * m[mOffset + 5]) * invDet;
        mInv[mInvOffset + 3] = 0.0f;

        mInv[mInvOffset + 4] = (m[mOffset + 6] * m[mOffset + 8] - m[mOffset + 4] * m[mOffset + 10]) * invDet;
        mInv[mInvOffset + 5] = (m[mOffset] * m[mOffset + 10] - m[mOffset + 2] * m[mOffset + 8]) * invDet;
        mInv[mInvOffset + 6] = (m[mOffset + 2] * m[mOffset + 4] - m[mOffset] * m[mOffset + 6]) * invDet;
        mInv[mInvOffset + 7] = 0.0f;

        mInv[mInvOffset + 8] = (m[mOffset + 4] * m[mOffset + 9] - m[mOffset + 5] * m[mOffset + 8]) * invDet;
        mInv[mInvOffset + 9] = (m[mOffset + 1] * m[mOffset + 8] - m[mOffset] * m[mOffset + 9]) * invDet;
        mInv[mInvOffset + 10] = (m[mOffset] * m[mOffset + 5] - m[mOffset + 1] * m[mOffset + 4]) * invDet;
        mInv[mInvOffset + 11] = 0.0f;

        mInv[mInvOffset + 12] = 0.0f;
        mInv[mInvOffset + 13] = 0.0f;
        mInv[mInvOffset + 14] = 0.0f;
        mInv[mInvOffset + 15] = 1.0f;
    }

    public static void scaleM(float[] sm, int smOffset,
                              float[] m, int mOffset,
                              float x, float y, float z) {
        for (int i=0 ; i<4 ; i++) {
            int smi = smOffset + i;
            int mi = mOffset + i;
            sm[     smi] = m[     mi] * x;
            sm[ 4 + smi] = m[ 4 + mi] * y;
            sm[ 8 + smi] = m[ 8 + mi] * z;
            sm[12 + smi] = m[12 + mi];
        }
    }

    public static void scaleM(float[] m, int mOffset,
                              float x, float y, float z) {
        for (int i=0 ; i<4 ; i++) {
            int mi = mOffset + i;
            m[     mi] *= x;
            m[ 4 + mi] *= y;
            m[ 8 + mi] *= z;
        }
    }

    public static void multiplyMM(float[] destination, float[] operand1, float[] operand2) {
        float[] theResult = new float[16];
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                theResult[4 * i + j] = operand1[j] * operand2[4 * i] + operand1[4 + j] * operand2[4 * i + 1] +
                        operand1[8 + j] * operand2[4 * i + 2] + operand1[12 + j] * operand2[4 * i + 3];
            }
        }
        System.arraycopy(theResult, 0, destination, 0, 16);
    }

    public static void multiplyMV(float[] destination, float[] matrix, float[] vector) {
        float[] result = new float[4];
        for (int i = 0; i < 4; i++) {
            result[i] = matrix[i] * vector[0] +
                    matrix[4 + i] * vector[1] +
                    matrix[8 + i] * vector[2] +
                    matrix[12 + i] * vector[3];
        }
        System.arraycopy(result, 0, destination, 0, 4);
    }

    public static float[] frustumM(float[] matrix4f , float left, float right, float bottom, float top, float near, float far) {
        float a = (right + left) / (right - left);
        float b = (top + bottom) / (top - bottom);
        float c = -(far + near) / (far - near);
        float d = -(2f * far * near) / (far - near);

        matrix4f[0] = (2f * near) / (right - left);
        matrix4f[5] = (2f * near) / (top - bottom);
        matrix4f[8] = a;
        matrix4f[9] = b;
        matrix4f[10] = -c - 1f;
        matrix4f[11] = -1f;
        matrix4f[14] = d;
        matrix4f[15] = 0f;

        return matrix4f;
    }
}
