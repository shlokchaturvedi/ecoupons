package com.pub;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import javax.imageio.ImageIO;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageUtils {
    public ImageUtils() {

    }

    /** *//**
     * ��ͼƬӡˢ��ͼƬ��
     *
     * @param pressImg --
     *            ˮӡ�ļ�
     * @param targetImg --
     *            Ŀ���ļ�
     * @param x
     *            --x����
     * @param y
     *            --y����
     *  @param alpha
     *           --͸����     
     */
    public final static void pressImage(String pressImg, String targetImg,
            int x, int y,float alpha) {
        try {
            //Ŀ���ļ�
        	float Alpha=alpha;
            File _file = new File(targetImg);
            Image src = ImageIO.read(_file);
            int wideth = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(wideth, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.drawImage(src, 0, 0, wideth, height, null);
            //ˮӡ�ļ�
            File _filebiao = new File(pressImg);
            Image src_biao = ImageIO.read(_filebiao);
            int wideth_biao = src_biao.getWidth(null);
            int height_biao = src_biao.getHeight(null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
            		Alpha));           
            g.drawImage(src_biao, (wideth - wideth_biao) / 2,
                        (height - height_biao) / 2, wideth_biao, height_biao, null);
            //ˮӡ�ļ�����
            g.dispose();
            FileOutputStream out = new FileOutputStream(targetImg);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(image);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** *//**
     * ��ӡ����ˮӡͼƬ
     *
     * @param pressText
     *            --����
     * @param targetImg --
     *            Ŀ��ͼƬ
     * @param fontName --
     *            ������
     * @param fontStyle --
     *            ������ʽ
     * @param color --
     *            ������ɫ
     * @param fontSize --
     *            �����С
     * @param x --
     *            ƫ����
     * @param y
     */

    public static void pressText(String pressText, String targetImg,
            String fontName, int fontStyle, int color, int fontSize, int x,
            int y,float alpha) {
        try {
        	float Alpha=alpha;
            File _file = new File(targetImg);
            Image src = ImageIO.read(_file);
            int wideth = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(wideth, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            
            g.drawImage(src, 0, 0, wideth, height, null);
            g.setColor(Color.RED);
            g.setFont(new Font(fontName, fontStyle, fontSize));
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
            		Alpha)); 
            g.drawString(pressText, wideth / 2 - x, height / 2 - y);
            g.dispose();
            FileOutputStream out = new FileOutputStream(targetImg);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(image);
            out.close();
            //System.out.print("������ӳɹ�");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}




 
