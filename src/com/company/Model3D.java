package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by benzoback on 24.11.16.
 * LAB_3D
 */

class Model3D {

    //коды операций с моделью
    static final int CODE_MIRROR_OX         = 1;
    static final int CODE_MIRROR_OY         = 2;
    static final int CODE_MIRROR_OZ         = 3;
    static final int CODE_MIRROR_YZ         = 4;
    static final int CODE_MIRROR_ZX         = 5;
    static final int CODE_MIRROR_XY         = 6;
    static final int CODE_MIRROR_OXYZ       = 7;
    static final int CODE_SCALE_UP          = 8;
    static final int CODE_SCALE_DOWN        = 9;
    static final int CODE_ROTATE_X          = 10;
    static final int CODE_ROTATE_Y          = 11;
    static final int CODE_ROTATE_Z          = 12;
    static final int CODE_COMPOSITE_AFFINE  = 123;

    int X = 0;
    int Y = 1;
    int Z = 2;

    Matrix vertices;
    Matrix edges;

    Matrix axesVertices;
    Matrix axesEdges;

    Model3D() {
        loadVertices();
        createEdges();
        createAxes();
    }

    private void loadVertices() {

        File file = new File("VERTICES.txt");

        BufferedReader bFileReader;
        String lineX = null;
        String lineY = null;
        String lineZ = null;
        String lineD = null;

        try {
            String currentString;
            bFileReader = new BufferedReader(new FileReader(file));

            while ((currentString = bFileReader.readLine()) != null) {
                if (lineX == null) {
                    lineX = currentString;
                } else if (lineY == null) {
                    lineY = currentString;
                } else if (lineZ == null) {
                    lineZ = currentString;
                } else if (lineD == null) {
                    lineD = currentString;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] completeStringSplit = lineX != null
                ? (lineX + " " + lineY + " " + lineZ + " " +lineD).split(" ")
                : null;

        assert completeStringSplit != null;
        double[] v = new double[completeStringSplit.length];
        int i = 0;

        for (String value : completeStringSplit) {
            v[i] = Integer.parseInt(value);
            i++;
        }

        vertices = new Matrix(4, lineX.split(" ").length, v);
    }

    private void createEdges() {

        File file = new File("VERGES.txt");

        BufferedReader bFileReader;
        StringBuilder line = new StringBuilder();
        int lineCount = 0;

        try {
            String currentString;
            bFileReader = new BufferedReader(new FileReader(file));

            currentString = bFileReader.readLine();
            do {
                line.append(currentString).append(" ");
                lineCount++;
            } while ((currentString = bFileReader.readLine()) != null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] completeStringSplit = line.toString().split(" ");

        double[] v = new double[completeStringSplit.length * 2];
        double prev;

        for (int i = 0; i < completeStringSplit.length; i++) {
            v[2 * i] = Integer.parseInt(completeStringSplit[i]);
            v[2 * i + 1] = Integer.parseInt(completeStringSplit[i]);
        }

        for (int i = 1; i < v.length - 1; i++) {
            prev = v[i + 1];
            v[i] = prev;
        }

        double[] vNew = new double[v.length / 3 * 2];
        int failEdgeCount = 0; int test = 0;
        for (int i = 0; i < v.length; i++) {
            if (failEdgeCount == 4) {
                failEdgeCount += 1;
            } else if (failEdgeCount == 5) {
                failEdgeCount = 0;
            } else {
                vNew[test] = v[i];
                test += 1;
                failEdgeCount += 1;
            }
        }

        edges = new Matrix(vNew.length / 2, 2, vNew);
    }

    void makeAffineTranslation(int code) {
        Matrix verticesMatrix = new Matrix(vertices);

        switch (code) {
            case CODE_MIRROR_OX : {
                vertices = new Matrix(AffineBasic.mirroringOX().multiply(verticesMatrix));
                break;
            }

            case CODE_MIRROR_OY : {
                vertices = new Matrix(AffineBasic.mirroringOY().multiply(verticesMatrix));
                break;
            }

            case CODE_MIRROR_OZ : {
                vertices = new Matrix(AffineBasic.mirroringOZ().multiply(verticesMatrix));
                break;
            }

            case CODE_MIRROR_OXYZ : {
                vertices = new Matrix(AffineBasic.mirroringCenter().multiply(verticesMatrix));
                break;
            }

            case CODE_MIRROR_YZ: {
                vertices = new Matrix(AffineBasic.mirroringYZ().multiply(verticesMatrix));
                break;
            }

            case CODE_MIRROR_ZX : {
                vertices = new Matrix(AffineBasic.mirroringZX().multiply(verticesMatrix));
                break;
            }

            case CODE_MIRROR_XY: {
                vertices = new Matrix(AffineBasic.mirroringXY().multiply(verticesMatrix));
                break;
            }

            case CODE_SCALE_UP : {
                vertices = new Matrix(AffineBasic.scaling(2, 2, 2).multiply(verticesMatrix));
                break;
            }

            case CODE_SCALE_DOWN : {
                vertices = new Matrix(AffineBasic.scaling(0.5, 0.5, 0.5).multiply(verticesMatrix));
                break;
            }

            case CODE_ROTATE_X : {
                vertices = new Matrix(AffineBasic.rotatingX(30.0, true).multiply(verticesMatrix));
                break;
            }

            case CODE_ROTATE_Y : {
                vertices = new Matrix(AffineBasic.rotatingY(30.0, false).multiply(verticesMatrix));
                break;
            }

            case CODE_ROTATE_Z : {
                vertices = new Matrix(AffineBasic.rotatingZ(30.0, false).multiply(verticesMatrix));
                break;
            }
        }
    }

    private void createAxes() {
        axesVertices = new Matrix(4, 4, new double[] {
                0, 20, 0, 0,
                0, 0, 20, 0,
                0, 0, 0, 20,
                1, 1, 1, 1
        });

        axesEdges = new Matrix(3, 2, new double[] {
                1, 2,
                1, 3,
                1, 4
        });
    }

    //получаем координаты вершины по ее номеру
    private double[] getVertexById(int id) {
        return new double[] {
                vertices.getMatrix()[X][id - 1],
                vertices.getMatrix()[Y][id - 1],
                vertices.getMatrix()[Z][id - 1]
        };
    }

    //получаем координаты вершины по ее номеру из произвольной матрицы вершин
    private double[] getVertexByIdAndMap(int id, Matrix vertices) {
        return new double[] {
                vertices.getMatrix()[X][id - 1],
                vertices.getMatrix()[Y][id - 1],
                vertices.getMatrix()[Z][id - 1]
        };
    }


    void compositeAffine(int vertexId, int vertexId2) {

        //vertexId и vertexId2 - номера вершин ребра, проходящего через грань (на рисунке видно)

        //копия матрицы вершин
        Matrix verticesTemp = new Matrix(vertices);

        //болванка аффинного преобр
        Matrix affine = new Matrix(4, 4, new double[] {
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1
        });

        //еще болванка аффинного преобр
        Matrix affineTemp = new Matrix(4, 4, new double[] {
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1
        });

        //флаги для определения, в какой координатной четверти находится вектор
        boolean flagXY = false;
        boolean flagYZ = false;

        //вершины вектора (ребро относительно которого отражаем) в плоскости 0xy (2D)
        double[] vertexXY = new double[] { getVertexById(vertexId)[X], getVertexById(vertexId)[Y] };
        double[] vertexXY2 = new double[] { getVertexById(vertexId2)[X], getVertexById(vertexId2)[Y] };

        //базис, который направлен вдоль оси Y
        double[] basis1 = new double[] { 0.0, 0.0 };
        double[] basis2 = new double[] { 0.0, 1.0 };

        //определяем в какой координатной четверти находится вектор
        //I and IV
        if (((vertexXY2[X] - vertexXY[X]) >= 0 && (vertexXY2[Y] - vertexXY[Y]) >= 0) ||
                ((vertexXY2[X] - vertexXY[X]) >= 0 && (vertexXY2[Y] - vertexXY[Y]) <= 0)) {
            flagXY = true;
        }

        //наш вектор (ребро относительно которого отражаем)
        double[] vectorXY = new double[] { vertexXY2[X] - vertexXY[X], vertexXY2[Y] - vertexXY[Y] };
        //базисный вектор, который направлен вдоль оси Y
        double[] vectorXY2 = new double[] { basis2[X] - basis1[X], basis2[Y] - basis1[Y] };

        //скалярное умножение
        double scalarMultiplyXY = (vectorXY[X] * vectorXY2[X]) + (vectorXY[Y] * vectorXY2[Y]);

        //длины векторов
        double vector1SizeXY = Math.sqrt(Math.pow(vectorXY[X], 2) + Math.pow(vectorXY[Y], 2));
        double vector2SizeXY = Math.sqrt(Math.pow(vectorXY2[X], 2) + Math.pow(vectorXY2[Y], 2));

        //ищем синус и косинус угла между векторами: наше ребро относительно которого отражаем и вектор который направлен
        //вдоль оси Y (получается как бы 2D, Z образно == 0)
        double cosPhiValueXY = scalarMultiplyXY / (vector1SizeXY * vector2SizeXY);
        double sinPhiValueXY = Math.sqrt(1.0 - Math.pow(cosPhiValueXY, 2));

        //временное аффинное преобразование
        affineTemp = affineTemp.
                multiply(flagXY
                        ? AffineBasic.rotatingZ(sinPhiValueXY, cosPhiValueXY, false)
                        : AffineBasic.rotatingZ(sinPhiValueXY, cosPhiValueXY, true));

        //считаем временные вершины модели, как будто мы уже сделали первый поворот (по оси Z)
        verticesTemp = affineTemp.multiply(verticesTemp);

        //далее делаем тоже самое, только при X == 0, то есть получается 2D плоскость 0zy (что тоже самое, что 0-xy)
        double[] vertexYZ = new double[] { -getVertexByIdAndMap(vertexId, verticesTemp)[Z],
                getVertexByIdAndMap(vertexId, verticesTemp)[Y] };
        double[] vertexYZ2 = new double[] { -getVertexByIdAndMap(vertexId2, verticesTemp)[Z],
                getVertexByIdAndMap(vertexId2, verticesTemp)[Y] };

        //I and IV
        if (((vertexYZ2[X] - vertexYZ[X]) >= 0 && (vertexYZ2[Y] - vertexYZ[Y]) >= 0) ||
                ((vertexYZ2[X] - vertexYZ[X]) >= 0 && (vertexYZ2[Y] - vertexYZ[Y]) <= 0)) {
            flagYZ = true;
        }

        double[] vectorYZ = new double[] { vertexYZ2[X] - vertexYZ[X], vertexYZ2[Y] - vertexYZ[Y] };
        double[] vectorYZ2 = new double[] { basis2[X] - basis1[X], basis2[Y] - basis1[Y] };

        double scalarMultiplyYZ = (vectorYZ[X] * vectorYZ2[X]) + (vectorYZ[Y] * vectorYZ2[Y]);
        double vector1SizeYZ = Math.sqrt(Math.pow(vectorYZ[X], 2) + Math.pow(vectorYZ[Y], 2));
        double vector2SizeYZ = Math.sqrt(Math.pow(vectorYZ2[X], 2) + Math.pow(vectorYZ2[Y], 2));

        double cosPhiValueYZ = scalarMultiplyYZ / (vector1SizeYZ * vector2SizeYZ);
        double sinPhiValueYZ = Math.sqrt(1.0 - Math.pow(cosPhiValueYZ, 2));

        affine = affine.
                multiply(AffineBasic.vector(getVertexById(vertexId)[X],
                        getVertexById(vertexId)[Y],
                        getVertexById(vertexId)[Z])).
                multiply(flagXY
                        ? AffineBasic.rotatingZ(sinPhiValueXY, cosPhiValueXY, true)
                        : AffineBasic.rotatingZ(sinPhiValueXY, cosPhiValueXY, false)).
                multiply(flagYZ
                        ? AffineBasic.rotatingX(sinPhiValueYZ, cosPhiValueYZ, true)
                        : AffineBasic.rotatingX(sinPhiValueYZ, cosPhiValueYZ, false)).
                multiply(AffineBasic.mirroringOY()).
                multiply(flagYZ
                        ? AffineBasic.rotatingX(sinPhiValueYZ, cosPhiValueYZ, false)
                        : AffineBasic.rotatingX(sinPhiValueYZ, cosPhiValueYZ, true)).
                multiply(flagXY
                        ? AffineBasic.rotatingZ(sinPhiValueXY, cosPhiValueXY, false)
                        : AffineBasic.rotatingZ(sinPhiValueXY, cosPhiValueXY, true)).
                multiply(AffineBasic.vector(-getVertexById(vertexId)[X],
                        -getVertexById(vertexId)[Y],
                        -getVertexById(vertexId)[Z]));
                //multiply(AffineBasic.rotatingY(0.5, true));


        vertices = affine.multiply(vertices);
    }
}
