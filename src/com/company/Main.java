package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main {

    private static Scene scene;
    private static Camera camera;
    private static Model3D model3D;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MyJFrame window = new MyJFrame();
            window.setTitle("Lab_3");
            window.setSize(600, 600);
            window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            window.setVisible(true);

            camera = new Camera(5, 5, 5, 1, 1, 1, 0, 1, 0, 25);
            model3D = new Model3D();
            scene = new Scene(window, camera, model3D);

            window.add(scene);
        });
    }

    private static class MyJFrame extends JFrame {

        Thread compositeThread;

        MyJFrame() {

            compositeThread = new Thread(() -> {
                while(true) {
                    try {
                        Thread.sleep(10);
                        model3D.compositeAffine(scene.getAnchorId(), scene.getAnchorId2());
                        scene.repaint();
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            });

            addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {

                }

                @Override
                public void keyPressed(KeyEvent e) {
                    System.out.println(KeyEvent.getKeyText(e.getKeyCode()));

                    switch (KeyEvent.getKeyText(e.getKeyCode())) {
                        case "W" : {
                            camera.setView(camera.getxN(), camera.getyN() + 0.5, camera.getzN());
                            break;
                        }

                        case "S" : {
                            camera.setView(camera.getxN(), camera.getyN() - 0.5, camera.getzN());
                            break;
                        }

                        case "A" : {
                            camera.setView(camera.getxN() - 0.5, camera.getyN(), camera.getzN() + 0.5);
                            break;
                        }

                        case "D" : {
                            camera.setView(camera.getxN() + 0.5, camera.getyN(), camera.getzN() - 0.5);
                            break;
                        }

                        case "-" : {
                            camera.setDistance(camera.getDistance() - 5);
                            break;
                        }

                        case "=" : {
                            camera.setDistance(camera.getDistance() + 5);
                            break;
                        }

                        case "Minus" : {
                            camera.setDistance(camera.getDistance() - 5);
                            break;
                        }

                        case "Equals" : {
                            camera.setDistance(camera.getDistance() + 5);
                            break;
                        }

                        case "1" : {
                            model3D.makeAffineTranslation(1);
                            break;
                        }

                        case "2" : {
                            model3D.makeAffineTranslation(2);
                            break;
                        }

                        case "3" : {
                            model3D.makeAffineTranslation(3);
                            break;
                        }

                        case "4" : {
                            model3D.makeAffineTranslation(4);
                            break;
                        }

                        case "5" : {
                            model3D.makeAffineTranslation(5);
                            break;
                        }

                        case "6" : {
                            model3D.makeAffineTranslation(6);
                            break;
                        }

                        case "7" : {
                            model3D.makeAffineTranslation(7);
                            break;
                        }

                        case "8" : {
                            model3D.makeAffineTranslation(8);
                            break;
                        }

                        case "9" : {
                            model3D.makeAffineTranslation(9);
                            break;
                        }

                        case "Z" : {
                            model3D.makeAffineTranslation(10);
                            break;
                        }

                        case "X" : {
                            model3D.makeAffineTranslation(11);
                            break;
                        }

                        case "C" : {
                            model3D.makeAffineTranslation(12);
                            break;
                        }

                        case "G" : {

//                            if (!compositeThread.isAlive()) {
//                                compositeThread.start();
//                            } else {
//                                compositeThread.stop();
//
//                                compositeThread = new Thread(() -> {
//                                    while(true) {
//                                        try {
//                                            Thread.sleep(10);
//                                            model3D.compositeAffine(scene.getAnchorId(), scene.getAnchorId2());
//                                            scene.repaint();
//                                        } catch (InterruptedException e1) {
//                                            e1.printStackTrace();
//                                        }
//                                    }
//                                });
//                            }

                            model3D.compositeAffine(scene.getAnchorId(), scene.getAnchorId2());
                        }
                    }

                    scene.repaint();
                }

                @Override
                public void keyReleased(KeyEvent e) {

                }
            });
        }

    }
}
