package com.company;

/**
 * Created by benzoback on 24.11.16.
 * LAB_3D
 */

class Camera {

    private double x0V, y0V, z0V;
    private double xN, yN, zN;
    private double xT, yT, zT;

    private double d;

    Camera(double x0V, double y0V, double z0V, double xN, double yN, double zN, double xT, double yT, double zT,
                  double d) {

        this.x0V = x0V;
        this.y0V = y0V;
        this.z0V = z0V;

        this.xN = xN;
        this.yN = yN;
        this.zN = zN;

        this.xT = xT;
        this.yT = yT;
        this.zT = zT;

        this.d = d;
    }

    void setView(double x, double y, double z) {
        xN = x;
        yN = y;
        zN = z;
    }

    void setDistance(double distance) {
        d = distance;
    }

    double getxN() {
        return xN;
    }

    double getyN() {
        return yN;
    }

    double getzN() {
        return zN;
    }

    double getDistance() {
        return d;
    }

    Matrix worldToViewMatrix() {

        double iVx, iVy, iVz;
        double jVx, jVy, jVz;
        double kVx, kVy, kVz;

        double xTxN = yT * zN - zT * yN;
        double yTxN = zT * xN - xT * zN;
        double zTxN = xT * yN - yT * xN;

        double vectorNSize = Math.sqrt(Math.pow(xN, 2) + Math.pow(yN, 2) + Math.pow(zN, 2));
        double vectorTxNSize = Math.sqrt(Math.pow(xTxN, 2)
                + Math.pow(yTxN, 2)
                + Math.pow(zTxN, 2));

        kVx = xN / vectorNSize;
        kVy = yN / vectorNSize;
        kVz = zN / vectorNSize;

        iVx = xTxN / vectorTxNSize;
        iVy = yTxN / vectorTxNSize;
        iVz = zTxN / vectorTxNSize;

        jVx = kVy * iVz - kVz * iVy;
        jVy = kVz * iVx - kVx * iVz;
        jVz = kVx * iVy - kVy * iVx;

        double[] vector = new double[] {
                iVx, iVy, iVz, -(x0V * iVx + y0V * iVy + z0V * iVz),
                jVx, jVy, jVz, -(x0V * jVx + y0V * jVy + z0V * jVz),
                kVx, kVy, kVz, -(x0V * kVx + y0V * kVy + z0V * kVz),
                0, 0, 0, 1
        };

        return new Matrix(4, 4, vector);
    }

    Matrix viewToProjectorMatrix() {

        return new Matrix(3, 4, new double[] {
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, -1 / d, 1
        });
    }

    Matrix worldToProjectorMatrix() {
        return viewToProjectorMatrix().multiply(worldToViewMatrix());
    }
}
