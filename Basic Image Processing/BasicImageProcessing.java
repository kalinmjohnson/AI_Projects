import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class BasicImageProcessing {

	public static void main(String[] args) {
		try {
			File f = new File("Dice.jpg");
			BufferedImage image = ImageIO.read(f);
			System.out.println("Image Read Successfully");
			System.out.println("Height: " + image.getHeight() + " Width: " + image.getWidth());

			//printData(image);
			//maxOutBlue(image);
			int [][] data = convert2DArray(image);
			//writeBinaryArray(data, "ImageFromArray.png");
			
			int [][] binaryArray = convert2Binary(data, 100);
			writeBinaryArray(binaryArray, "Binary.png");

			int [][] edgeImage = sobelFilter(binaryArray, 50);
			writeBinaryArray(edgeImage, "Edge.png");
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());	
		}

	}

	public static int [][] convert2Binary(int[][] array, int threshold) {
		int[][] binaryArray = new int[array.length][array[0].length];
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[0].length; j++) {
				if (array[i][j] >= threshold) {
					binaryArray[i][j] = 255;
				} else {
					binaryArray[i][j] = 0;
				}
			}
		}
		return binaryArray;
	}

	public static void printData(BufferedImage image) {
		for(int i = 20; i < 60; i++) {
			for(int j = 15; j < 45; j++) {
				Color c = new Color(image.getRGB(i, j)); //column then row
				//System.out.println(i + " " + j + ": " + c.getRed() + " " + c.getGreen() + " " + c.getBlue());
			}
		}
	}

	public static void maxOutBlue(BufferedImage image) throws IOException {
		for (int i = 0; i < image.getHeight(); i++) {
			for (int j = 0; j < image.getWidth(); j++) {
				Color c = new Color(image.getRGB(j, i));
				Color d = new Color(c.getRed(), c.getGreen(), 255);
				image.setRGB(j, i, d.getRGB());
			}
		}
		ImageIO.write(image, "jpg", new File("/Users/kalinjohnson/Downloads/Basic Image Processing/BlueDiceImage.jpg"));
	}

	public static int[][] convert2DArray(BufferedImage image) throws IOException {
		int[][] array = new int[image.getHeight(null)][image.getWidth(null)];
		for (int i = 0; i < image.getHeight(); i++) {
			for (int j = 0; j < image.getWidth(); j++) {
				Color c = new Color(image.getRGB(j, i));
				int pixelValue = (c.getRed() + c.getBlue() + c.getGreen());
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
				//System.out.println(i +  " " + j + " " + array[i][j]);
				Color c = new Color(array[i][j], array[i][j], array[i][j]);
				binaryImage.setRGB(j, i, c.getRGB());
			}
		}
		ImageIO.write(binaryImage, "png", new File(name));
	}


//Calculating the value of a particular pixel
public static int getFilterValue(int [][] image, int row, int column, int threshold) {
	int xFilter = 0, yFilter = 0;
	int [] xWeights = {-1, 0, 1, -2, 0, 2, -1, 0, 1};
	int [] yWeights = {1, 2, 1, 0, 0, 0, -1, -2, -1};
	int weightIndex = 0; 
	for (int i = -1; i < 2; i++) {
		for (int j = -1; j < 2; j++) {
			xFilter += (image[row+i][column+j] * xWeights[weightIndex]);
			yFilter += (image[row+i][column+j] * yWeights[weightIndex]);
			weightIndex++;
		}
	}
	int result = (int)Math.sqrt((Math.pow(xFilter, 2) + Math.pow(yFilter, 2)));
	if (result > threshold) {
		return 255;
	} else {
		return 0;
	}
}

public static int [][] sobelFilter(int [][] binaryArray, int threshold) {
	int [][] edgeImage = new int[binaryArray.length][binaryArray[0].length];
	for (int i = 1; i < binaryArray.length-1; i++) {
		for (int j = 1; j < binaryArray[0].length-1; j++) {
			edgeImage[i][j] = getFilterValue(binaryArray, i, j, threshold);
		}
	}
	return edgeImage;
}
}