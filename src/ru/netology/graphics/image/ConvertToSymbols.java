package ru.netology.graphics.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.net.URL;


public class ConvertToSymbols implements TextGraphicsConverter {

    protected int newWidth;
    protected int newHeight;
    protected double newRatio;
    protected TextColorSchema textColorSchema;

    @Override
    public String convert(String url) throws IOException, BadImageSizeException {
        ChangeColor changeColor = new ChangeColor();
        BufferedImage img = ImageIO.read(new URL(url));
        int width = img.getWidth();
        int height = img.getHeight();

        double aspectRatio = Math.max((double) img.getWidth() / newWidth, (double) img.getHeight() / newHeight);

        aspectRatio = Math.floor(aspectRatio);
        if (aspectRatio < 1) {
            aspectRatio = 1;
        }

        if (aspectRatio > newRatio) {
            throw new BadImageSizeException(aspectRatio, newRatio);
        }


        newHeight = (int) (height / aspectRatio);
        newWidth = (int) (width / aspectRatio);

        Image scaledImage = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);

        BufferedImage bwImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);

        Graphics graphics = bwImage.createGraphics();
        graphics.drawImage(scaledImage, 0, 0, null);

        ImageIO.write(bwImage, "png", new File("out.png"));

        WritableRaster bwRaster = bwImage.getRaster();

        StringBuilder imageOfSymbolsBuilder = new StringBuilder();

        char[][] imageOfChars = new char[newHeight][newWidth];

        int color;

        for (int i = 0; i < newHeight; i++) {
            for (int j = 0; j < newWidth; j++) {
                color = bwRaster.getPixel(j, i, new int[3])[0];
                imageOfChars[i][j] = changeColor.convert(color);
                imageOfSymbolsBuilder.append(String.valueOf(imageOfChars[i][j]).repeat(2));
            }
            imageOfSymbolsBuilder.append('\n');
        }
        return imageOfSymbolsBuilder.toString();
    }

    @Override
    public void setMaxWidth(int width) {
        this.newWidth = width;
    }

    @Override
    public void setMaxHeight(int height) {
        this.newHeight = height;
    }

    @Override
    public void setMaxRatio(double maxRatio) {
        this.newRatio = maxRatio;
    }

    @Override
    public void setTextColorSchema(TextColorSchema schema) {
        this.textColorSchema = schema;
    }
}
