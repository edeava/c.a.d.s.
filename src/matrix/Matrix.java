package matrix;

import java.math.BigInteger;

public class Matrix {
    String name;
    int rows, cols;
    BigInteger[][] values;

    public Matrix() {
        name = "";
        rows = 0;
        cols = 0;

    }

    public Matrix(String name, int rows, int cols) {
        this.name = name;
        this.rows = rows;
        this.cols = cols;
        values = new BigInteger[rows][cols];
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                values[i][j] = BigInteger.ZERO;
            }
        }
    }

    public void insertValue(int i, int j, String value){
        values[i][j] = new BigInteger(value);
    }

    public void insertValue(String i, String j, String value){
        values[Integer.parseInt(i)][Integer.parseInt(j)] = new BigInteger(value);
    }

    public void setName(String name){
        this.name = name;
    }

    public void setColsNRows(String cols, String rows){
        this.rows = Integer.parseInt(rows);
        this.cols = Integer.parseInt(cols);
    }

    public String getName() {
        return name;
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }

    public BigInteger getValue(int i, int j){
        return values[i][j];
    }

    public Matrix combine(Matrix mat){
        if(this.getRows() != mat.getRows() || this.getCols() != mat.getCols())
            return null;
        Matrix result = new Matrix(this.getName(), this.getRows(), this.getCols());
        for(int i = 0; i < this.getRows(); i++){
            for(int j = 0; j < this.getCols(); j++){
                String toInsert = this.getValue(i, j) != BigInteger.ZERO ? this.getValue(i, j).toString() : mat.getValue(i, j).toString();
                result.insertValue(i, j, toInsert);
            }
        }

        return result;
    }
}
