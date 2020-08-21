package model;

// represents the matrix for the playing field
public class Matrix {
    public static final int width = 10;
    public static final int height = 20;
    protected String matrixData = "";
    protected int[][] matrix = new int[width][height];

    // MODIFIES: this
    public Matrix() {
        for (int k = 0; k < 200; k++) {
            matrixData = matrixData + "0";
        }
        fillMatrix();
    }

    public int[][] getMatrix() {
        return matrix;
    }

    // MODIFIES: this
    // EFFECTS: fills matrix with the values of matrixData
    public void fillMatrix() {
        for (int k = 0; k < height; k++) {
            for (int j = 0; j < width; j++) {
                int index = (10 * k) + j;
                String data = Character.toString(matrixData.charAt(index));
                matrix[j][k] = Integer.parseInt(data);
            }
        }
    }

    public String getMatrixData() {
        return matrixData;
    }

    public int getHeight() {
        return (height);
    }

    public int getWidth() {
        return (width);
    }

}
