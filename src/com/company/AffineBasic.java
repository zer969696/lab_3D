package com.company;

/**
 * Created by benz on 17.11.2016.
 * LAB_2D
 */

class AffineBasic {

    private AffineBasic() {

    }

    static Matrix mirroringOX() {
        return new Matrix(4, 4, new double[] {
                1, 0, 0, 0,
                0, -1, 0, 0,
                0, 0, -1, 0,
                0, 0, 0, 1,
        });
    }

    static Matrix mirroringOY() {
        return new Matrix(4, 4, new double[] {
                -1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, -1, 0,
                0, 0, 0, 1,
        });
    }

    static Matrix mirroringOZ() {
        return new Matrix(4, 4, new double[] {
                -1, 0, 0, 0,
                0, -1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1,
        });
    }

    static Matrix mirroringYZ() {
        return new Matrix(4, 4, new double[] {
                -1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1,
        });
    }

    static Matrix mirroringZX() {
        return new Matrix(4, 4, new double[] {
                1, 0, 0, 0,
                0, -1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1,
        });
    }

    static Matrix mirroringXY() {
        return new Matrix(4, 4, new double[] {
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, -1, 0,
                0, 0, 0, 1,
        });
    }

    static Matrix rotatingX(double phi, boolean reverse) {

        Matrix result;
        if (reverse) {
            result = new Matrix(4, 4, new double[] {
                    1, 0, 0, 0,
                    0, Math.cos(Math.toRadians(phi)), Math.sin(Math.toRadians(phi)), 0,
                    0, -Math.sin(Math.toRadians(phi)), Math.cos(Math.toRadians(phi)), 0,
                    0, 0, 0, 1,
            });
        } else {
            result = new Matrix(4, 4, new double[] {
                    1, 0, 0, 0,
                    0, Math.cos(Math.toRadians(phi)), -Math.sin(Math.toRadians(phi)), 0,
                    0, Math.sin(Math.toRadians(phi)), Math.cos(Math.toRadians(phi)), 0,
                    0, 0, 0, 1,
            });
        }

        return result;
    }

    static Matrix rotatingY(double phi, boolean reverse) {

        Matrix result;
        if (reverse) {
            result = new Matrix(4, 4, new double[] {
                    Math.cos(Math.toRadians(phi)), 0, -Math.sin(Math.toRadians(phi)), 0,
                    0, 1, 0, 0,
                    Math.sin(Math.toRadians(phi)), 0, Math.cos(Math.toRadians(phi)), 0,
                    0, 0, 0, 1,
            });
        } else {
            result = new Matrix(4, 4, new double[] {
                    Math.cos(Math.toRadians(phi)), 0, Math.sin(Math.toRadians(phi)), 0,
                    0, 1, 0, 0,
                    -Math.sin(Math.toRadians(phi)), 0, Math.cos(Math.toRadians(phi)), 0,
                    0, 0, 0, 1,
            });
        }

        return result;
    }

    static Matrix rotatingZ(double phi, boolean reverse) {

        Matrix result;
        if (reverse) {
            result = new Matrix(4, 4, new double[] {
                    Math.cos(Math.toRadians(phi)), Math.sin(Math.toRadians(phi)), 0, 0,
                    -Math.sin(Math.toRadians(phi)), Math.cos(Math.toRadians(phi)), 0, 0,
                    0, 0, 1, 0,
                    0, 0, 0, 1,
            });
        } else {
            result = new Matrix(4, 4, new double[] {
                    Math.cos(Math.toRadians(phi)), -Math.sin(Math.toRadians(phi)), 0, 0,
                    Math.sin(Math.toRadians(phi)), Math.cos(Math.toRadians(phi)), 0, 0,
                    0, 0, 1, 0,
                    0, 0, 0, 1,
            });
        }

        return result;
    }

    static Matrix rotatingX(double sinValue, double cosValue, boolean reverse) {

        Matrix result;
        if (reverse) {
            result = new Matrix(4, 4, new double[] {
                    1, 0, 0, 0,
                    0, cosValue, sinValue, 0,
                    0, -sinValue, cosValue, 0,
                    0, 0, 0, 1,
            });
        } else {
            result = new Matrix(4, 4, new double[] {
                    1, 0, 0, 0,
                    0, cosValue, -sinValue, 0,
                    0, sinValue, cosValue, 0,
                    0, 0, 0, 1,
            });
        }

        return result;
    }

    static Matrix rotatingY(double sinValue, double cosValue, boolean reverse) {

        Matrix result;
        if (reverse) {
            result = new Matrix(4, 4, new double[] {
                    cosValue, 0, -sinValue, 0,
                    0, 1, 0, 0,
                    sinValue, 0, cosValue, 0,
                    0, 0, 0, 1,
            });
        } else {
            result = new Matrix(4, 4, new double[] {
                    cosValue, 0, sinValue, 0,
                    0, 1, 0, 0,
                    -sinValue, 0, cosValue, 0,
                    0, 0, 0, 1,
            });
        }

        return result;
    }

    static Matrix rotatingZ(double sinValue, double cosValue, boolean reverse) {

        Matrix result;
        if (reverse) {
            result = new Matrix(4, 4, new double[] {
                    cosValue, sinValue, 0, 0,
                    -sinValue, cosValue, 0, 0,
                    0, 0, 1, 0,
                    0, 0, 0, 1,
            });
        } else {
            result = new Matrix(4, 4, new double[] {
                    cosValue, -sinValue, 0, 0,
                    sinValue, cosValue, 0, 0,
                    0, 0, 1, 0,
                    0, 0, 0, 1,
            });
        }

        return result;
    }

    static Matrix scaling(double kx, double ky, double kz) {
        return new Matrix(4, 4, new double[] {
                kx, 0, 0, 0,
                0, ky, 0, 0,
                0, 0, kz, 0,
                0, 0, 0, 1
        });
    }

    static Matrix mirroringCenter() {
        return new Matrix(4, 4, new double[] {
                -1, 0, 0, 0,
                0, -1, 0, 0,
                0, 0, -1, 0,
                0, 0, 0, 1
        });
    }

    static Matrix vector(double ax, double ay, double az) {
        return new Matrix(4, 4, new double[] {
                1, 0, 0, ax,
                0, 1, 0, ay,
                0, 0, 1, az,
                0, 0, 0, 1
        });
    }
}
