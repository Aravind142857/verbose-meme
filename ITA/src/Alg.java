import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class Alg {
    public BufferedImage img = null;
    public String encode(String filename) throws IOException {
        img = ImageIO.read(new File(filename));
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ImageIO.write(img, "png", stream);
        stream.flush();
        byte[] imageInByteArray = stream.toByteArray();
        stream.close();
        String imageString = Base64.getEncoder().encodeToString(imageInByteArray);
        return imageString;
        /*String encrypted = "";
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                int color = img.getRGB(x, y);
                int blue = color & 0xff;
                int green = (color & 0xff00) >> 8;
                int red = (color & 0xff0000) >> 16;
                encrypted += String.format("#%02x%02x%02x", red, green, blue);
            }
        }
        encrypted = String.format("%04x%04x", img.getWidth(), img.getHeight()) + encrypted;
        return encrypted;*/
    }
    public File decodeToFile(String encrypted) throws IOException {
        BufferedImage img = decodeHelper(encrypted);
        File f = new File("decoded.png");
        ImageIO.write(img, "png", f);
        return f;
    }
    private BufferedImage decodeHelper(String encrypted) throws IOException {
        byte[] imageInByteArray = Base64.getDecoder().decode(encrypted);
        ByteArrayInputStream bais = new ByteArrayInputStream(imageInByteArray);
        BufferedImage img = ImageIO.read(bais);
        return img;
    }
    public void decodeToScreen(String encrypted) throws IOException {
        BufferedImage img = decodeHelper(encrypted);
        JFrame frame = new JFrame();
        frame.setSize(1000, 800);
        frame.getContentPane().add(new JLabel(new ImageIcon(img)));
        frame.paint(frame.getGraphics());
        frame.setVisible(true);
        frame.repaint();
    }
    public static void main(String[] args) throws IOException {
        Alg alg = new Alg();
        String img = alg.encode("/Users/aravind/Desktop/Code for Fun/ITA/Screen Shot 2022-11-18 at 10.12.43 PM.png");
        alg.decodeToFile(img);
    }
}
