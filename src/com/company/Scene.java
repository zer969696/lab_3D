package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.HashMap;

/**
 * Created by benz on 17.11.2016.
 * LAB_2D
 */

class Scene extends JPanel {

    private JFrame parentFrame;
    private Camera camera;
    private Model3D model3D;

    private double L = -10;
    private double B = -10;
    private double R =  10;
    private double T =  10;

    private int W;
    private int H;

    private Matrix projectorVertices;
    private Matrix projectorAxesVertices;

    private HashMap<Object, Object> verticesAndEdgesMap;
    private HashMap<Object, Object> axesVerticesAndEdgesMap;

    private int anchorId = 0;
    private int anchorId2 = 0;

    Scene(JFrame frame, Camera cam, Model3D mdl) {

        parentFrame = frame;
        camera = cam;
        model3D = mdl;


        //инициализация W и H, а так же одинаковый масштаб
        setResolution();
    }

    private int worldToScreenX(double X) {
        return (int) (W * ((X - L) / (R - L)));
    }

    private int worldToScreenY(double Y) {
        return (int) (H * (1 - ((Y - B) / (T - B))));
    }

    int getAnchorId() {
        return anchorId;
    }

    int getAnchorId2() {
        return anchorId2;
    }

    //метод в котором происходит рисование (любое, и только в этом методе. Его можно вызвать только вызвав repaint();
    //в любом месте кода)
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //проекционные коодинаты модели и координатных осей
        projectorVertices = camera.worldToProjectorMatrix().multiply(model3D.vertices);
        projectorAxesVertices = camera.worldToProjectorMatrix().multiply(model3D.axesVertices);

        verticesAndEdgesMap = new HashMap<>();
        for (int j = 0; j < projectorVertices.getCol(); j++) {

            verticesAndEdgesMap.put(j + 1, new double[] {
                    projectorVertices.getMatrix()[0][j] / projectorVertices.getMatrix()[2][j],
                    projectorVertices.getMatrix()[1][j] / projectorVertices.getMatrix()[2][j]
            });

            if (model3D.vertices.getMatrix()[0][j] == 1 &&
                    model3D.vertices.getMatrix()[1][j] == 0 &&
                    model3D.vertices.getMatrix()[2][j] == 1 &&
                    anchorId == 0) {
                anchorId = j + 1;
            }

            if (model3D.vertices.getMatrix()[0][j] == 1 &&
                    model3D.vertices.getMatrix()[1][j] == 4 &&
                    model3D.vertices.getMatrix()[2][j] == 5 &&
                    anchorId2 == 0) {
                anchorId2 = j + 1;
            }
        }

        axesVerticesAndEdgesMap = new HashMap<>();
        for (int j = 0; j < projectorAxesVertices.getCol(); j++) {

            axesVerticesAndEdgesMap.put(j + 1, new double[] {
                    projectorAxesVertices.getMatrix()[0][j] / projectorAxesVertices.getMatrix()[2][j],
                    projectorAxesVertices.getMatrix()[1][j] / projectorAxesVertices.getMatrix()[2][j]
            });
        }

        // { x, y, z }
        Color[] axesColors = new Color[] { Color.BLUE, Color.RED, Color.GREEN };
        int colorSwitcher = 0;

        MapHelper axesVerticesAndEdgesMapHelper = new MapHelper(axesVerticesAndEdgesMap);
        //рисуем координатные оси таким же способом
        for (int i = 0; i < model3D.axesEdges.getRow(); i++) {
            g.setColor(axesColors[colorSwitcher++]);

            g.drawLine(
                    worldToScreenX(axesVerticesAndEdgesMapHelper.getValueByKeyAndIndex(
                            (int) model3D.axesEdges.getMatrix()[i][0], 0)),
                    worldToScreenY(axesVerticesAndEdgesMapHelper.getValueByKeyAndIndex(
                            (int) model3D.axesEdges.getMatrix()[i][0], 1)),
                    worldToScreenX(axesVerticesAndEdgesMapHelper.getValueByKeyAndIndex(
                            (int) model3D.axesEdges.getMatrix()[i][1], 0)),
                    worldToScreenY(axesVerticesAndEdgesMapHelper.getValueByKeyAndIndex(
                            (int) model3D.axesEdges.getMatrix()[i][1], 1)));
        }

        g.setColor(Color.BLACK);
        MapHelper verticesAndEdgesMapHelper = new MapHelper(verticesAndEdgesMap);
        //рисуем фигуру по карте вершин и карте ребер
        for (int i = 0; i < model3D.edges.getRow(); i++) {
            g.drawLine(
                    worldToScreenX(verticesAndEdgesMapHelper.getValueByKeyAndIndex(
                            (int) model3D.edges.getMatrix()[i][0], 0)),
                    worldToScreenY(verticesAndEdgesMapHelper.getValueByKeyAndIndex(
                            (int) model3D.edges.getMatrix()[i][0], 1)),
                    worldToScreenX(verticesAndEdgesMapHelper.getValueByKeyAndIndex(
                            (int) model3D.edges.getMatrix()[i][1], 0)),
                    worldToScreenY(verticesAndEdgesMapHelper.getValueByKeyAndIndex(
                            (int) model3D.edges.getMatrix()[i][1], 1)));
        }
    }

    //вызывается при изменении размеров окна. Делает одинаковый мастштаб по осям
    private void setResolution() {
        W = parentFrame.getContentPane().getWidth();
        H = parentFrame.getContentPane().getHeight();

        //одинаковый масштаб по осям
        T = (((R - L) * H / W) / 2);
        B = ((-(R - L) * H / W) / 2);

        // тоже самое что и выше
        this.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                W = parentFrame.getContentPane().getWidth();
                H = parentFrame.getContentPane().getHeight();

                T = (((R - L) * H / W) / 2);
                B = ((-(R - L) * H / W) / 2);
            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });
    }
}
