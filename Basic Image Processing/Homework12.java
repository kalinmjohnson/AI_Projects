import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Homework12 {
    public static void main(String[] args) {
		try {
            File f1 = new File("HW12CVImage1.jpg");
			BufferedImage image1 = ImageIO.read(f1);
			System.out.println("Image 1 Read Successfully");
			System.out.println("Height: " + image1.getHeight() + " Width: " + image1.getWidth());

            File f2 = new File("HW12CVImage2.jpg");
			BufferedImage image2 = ImageIO.read(f2);
			System.out.println("Image 2 Read Successfully");
			System.out.println("Height: " + image2.getHeight() + " Width: " + image2.getWidth());

            int [][] data1 = convert2DArray(image1);
            int [][] data2 = convert2DArray(image2);

            int [][] binaryArray1 = convert2Binary(data1, 220, 40);
            int [][] binaryArray2 = convert2Binary(data2, 220, 40);

            writeBinaryArray(binaryArray1, "Binary1.png");
            writeBinaryArray(binaryArray2, "Binary2.png");

            int [][] outputArray = backgroundSubtraction(binaryArray1, binaryArray2, 50);
            writeBinaryArray(outputArray, "Background.png");

        } catch (Exception e) {
			System.out.println("Error: " + e.getMessage());	
		}
    }

    public static int [][] convert2Binary(int[][] array, int threshold, int thresholdW) {
		int[][] binaryArray = new int[array.length][array[0].length];
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[0].length; j++) {
				if (array[i][j] >= threshold || array[i][j] <= thresholdW) {
					binaryArray[i][j] = 255;
				} else {
					binaryArray[i][j] = 0;
				}
			}
		}
		return binaryArray;
	}

    public static int[][] convert2DArray(BufferedImage image) throws IOException {
		int[][] array = new int[image.getHeight(null)][image.getWidth(null)];
		for (int i = 0; i < image.getHeight(); i++) {
			for (int j = 0; j < image.getWidth(); j++) {
				Color c = new Color(image.getRGB(j, i));
				int pixelValue = (c.getRed() + c.getBlue() + c.getGreen())/3;
				array[i][j] = pixelValue;
			}
		}

		return array;
	}

    public static void writeBinaryArray(int [][] array, String name) throws IOException
	{
		BufferedImage binaryImage = new BufferedImage(array[0].length, array.length, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < array.length; i++)
		{
			for (int j = 0; j < array[i].length; j++)
			{
				Color c = new Color(array[i][j], array[i][j], array[i][j]);
				binaryImage.setRGB(j, i, c.getRGB());
			}
		}
		ImageIO.write(binaryImage, "png", new File(name));
	}

	public static void printData(BufferedImage image) {
		for(int i = 20; i < 60; i++) {
			for(int j = 15; j < 45; j++) {
				Color c = new Color(image.getRGB(i, j)); //column then row
				System.out.println(i + " " + j + ": " + c.getRed() + " " + c.getGreen() + " " + c.getBlue());
			}
		}
	}

    public static int[][] backgroundSubtraction(int[][] binaryArray1, int[][] binaryArray2, int threshold) {
        int[][] backgroundArray = new int[binaryArray1.length][binaryArray1[0].length];
        for (int i = 0; i < binaryArray1.length; i++) {
            for (int j = 0; j < binaryArray1[0].length; j++) {
                if (Math.abs(binaryArray1[i][j] - binaryArray2[i][j]) < threshold) {
				    backgroundArray[i][j] = 0;
                } else {
                    backgroundArray[i][j] = 255;
                }
            }
        }

        return backgroundArray;
    }

	public static int[][] convertImage(BufferedImage image, int n, int m) {
		int[][] arrayImage = new int[n][m];

		for (int x = 0; x < image.getHeight(); x++) {
			for (int y = 0; y < image.getWidth(); y++) {
				int red = image[x][y]
			}
		}


		return arrayImage;
	}
}
