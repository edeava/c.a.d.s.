package matrix;

import java.math.BigInteger;

public class Matrix implements Comparable<Matrix>{
    String name;
    String fileName;
    int rows, cols;
    BigInteger[][] values;

    public Matrix() {
        name = "";
        rows = 0;
        cols = 0;
        fileName = "";
    }

    public Matrix(String name, int rows, int cols, String fileName) {
        this.name = name;
        this.rows = rows;
        this.cols = cols;
        this.fileName = fileName;
        values = new BigInteger[rows][cols];
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                values[i][j] = BigInteger.ZERO;
            }
        }
    }

    public static Matrix transpose(Matrix toTranspose){
        Matrix transposedMatrix = new Matrix(toTranspose.getName(), toTranspose.getCols(), toTranspose.getRows(), toTranspose.getFileName());
        for (int i = 0; i < toTranspose.getRows(); i++) {
            for (int j = 0; j < toTranspose.getCols(); j++) {
                transposedMatrix.insertValue(j, i, toTranspose.getValue(i, j).toString());
            }
        }
        return transposedMatrix;
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

    public String getFileName(){
        return fileName;
    }

    public BigInteger getValue(int i, int j){
        return values[i][j];
    }

    public Matrix combine(Matrix mat){
        if(this.getRows() != mat.getRows() || this.getCols() != mat.getCols())
            return null;
        Matrix result = new Matrix(this.getName(), this.getRows(), this.getCols(), this.fileName);
        for(int i = 0; i < this.getRows(); i++){
            for(int j = 0; j < this.getCols(); j++){
                String toInsert = this.getValue(i, j) != BigInteger.ZERO ? this.getValue(i, j).toString() : mat.getValue(i, j).toString();
                result.insertValue(i, j, toInsert);
            }
        }

        return result;
    }

    @Override
    public int compareTo(Matrix o) {
        Integer myRows = this.rows;
        Integer thierRows = o.getRows();
        Integer myCols = this.cols;
        Integer thierCols = o.getCols();
        if(myRows.compareTo(thierRows) == 0){
            return myCols.compareTo(thierCols);
        }
        return myRows.compareTo(thierRows);
    }

    @Override
    public boolean equals(Object obj) {
        return this.name.equals(((Matrix) obj).getName());
    }

    @Override
    public String toString() {
        return name + '|' + "rows=" + rows + ',' + "cols=" + cols;
    }

    public String toFile(){
        String toPrint = "matrix_name=" + this.name + ", rows=" + this.rows + ", cols=" + this.cols + "\n";
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++){
                if(this.values[i][j].equals(BigInteger.ZERO)) continue;
                toPrint += i + "," + j + " = " + this.values[i][j].toString() + " \n";
            }
        }
        return toPrint;
    }

    public void setFilePath(String path){
        this.fileName = path;
    }
}
