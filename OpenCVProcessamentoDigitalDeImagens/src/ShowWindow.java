/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.*;
import org.opencv.core.Mat;

/**
 *
 * @author Eliezer
 */
public class ShowWindow extends JFrame {
    public static JFrame frame;
    public static Mat picture;
    public static BufferedImage image;
    public static int larg;
    public static int alt;
    public static int modo;
    
    public static void showWindow(String nomeJanela, Mat picture) {
        frame = new JFrame(nomeJanela);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setSize(800, 800);
        image = matToBufferedImage(picture);
        JPanel panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                g.drawImage(image, 0, 0, frame.getWidth(), frame.getHeight(), this);
                super.paintComponents(g);
            }
        };
        frame.setContentPane(panel);
        frame.setVisible(true);
    }
    
    public static void showWindow(String nomeJanela, Mat picture, int l, int a) {
        frame = new JFrame(nomeJanela);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setSize(800, 800);
        image = matToBufferedImage(picture);
        
        if (l == 0) {alt = image.getHeight(); larg = image.getWidth(); }
        
        larg = l;
        alt = a;
        
        JPanel panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                g.drawImage(image, 0, 0, larg, alt, this);
                super.paintComponents(g);
            }
        };
        frame.setContentPane(panel);
        frame.setVisible(true);
    }
    
    public static BufferedImage matToBufferedImage(Mat matrix) {
        int cols = matrix.cols();
        int rows = matrix.rows();
        int elemSize = (int)matrix.elemSize();
        byte[] data = new byte[cols * rows * elemSize];
        int type;
        
        matrix.get(0, 0, data);
        
        switch (matrix.channels()) {
            case 1:
                type = BufferedImage.TYPE_BYTE_GRAY;
                break;
            case 3:
                type = BufferedImage.TYPE_3BYTE_BGR;
                byte b;
                
                for (int i = 0; i < data.length; i = i + 3) {
                    b = data[i];
                    data[i] = data[i+2];
                    data[i + 2] = b;
                }
                break;
            default:
                return null;
        }
        BufferedImage image = new BufferedImage(cols, rows, type);
        image.getRaster().setDataElements(0, 0, cols, rows, data);
        return image;
    }
}