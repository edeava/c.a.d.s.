package task;

import java.math.BigInteger;
import java.util.concurrent.RecursiveTask;
import main.App;
import main.Config;
import matrix.Matrix;

public class MultiplyTask extends RecursiveTask<Matrix> implements Task{
    private final TaskType taskType = TaskType.MULTIPLY;
    private Matrix mat1;
    private Matrix mat2;
    private String resultName;
    private int startMat1;
    private int startMat2;
    private int endMat1;
    private int endMat2;

    public MultiplyTask(Matrix mat1, Matrix mat2, String resultName) {
        if(mat1.getRows() != mat2.getCols()){
            App.printErr("Matrices incompatible for multiplication!");
            return;
        }
        this.mat1 = mat1;
        this.mat2 = mat2;
        this.resultName = resultName;
        this.startMat1 = 0;
        this.startMat2 = 0;
        this.endMat1 = mat1.getCols();
        this.endMat2 = mat2.getRows();
    }

    public MultiplyTask(Matrix mat1, Matrix mat2, String resultName, int startMat1, int startMat2, int endMat1, int endMat2) {
        this.mat1 = mat1;
        this.mat2 = mat2;
        this.resultName = resultName;
        this.startMat1 = startMat1;
        this.startMat2 = startMat2;
        this.endMat1 = endMat1;
        this.endMat2 = endMat2;
    }

    @Override
    public TaskType getType() {
        return taskType;
    }

    @Override
    protected Matrix compute() {
        int len1 = (endMat1 - startMat1);
        int len2 = (endMat2 - startMat2);
        Matrix result = new Matrix(resultName, mat1.getCols(), mat2.getRows(), "");
        if(len1 <= Config.MAX_ROW_SIZE && len2 <= Config.MAX_ROW_SIZE){
            return multiply(startMat1, endMat1, startMat2, endMat2, mat1.getRows());
        } else if(len1 > Config.MAX_ROW_SIZE){
            int mid =  len1 / 2 + startMat1;
            MultiplyTask left = new MultiplyTask(mat1, mat2, resultName, startMat1, startMat2, mid, endMat2);
            MultiplyTask right = new MultiplyTask(mat1, mat2, resultName, mid, startMat2, endMat1, endMat2);

            left.fork();
            Matrix rightMat = right.compute();
            Matrix leftMat = left.join();

            result = leftMat.combine(rightMat);
            
        } else if(len2 > Config.MAX_ROW_SIZE){
            int mid =  len2 / 2 + startMat2;
            MultiplyTask left = new MultiplyTask(mat1, mat2, resultName, startMat1, startMat2, endMat1, mid);
            MultiplyTask right = new MultiplyTask(mat1, mat2, resultName, startMat1, mid, endMat1, endMat2);

            left.fork();
            Matrix rightMat = right.compute();
            Matrix leftMat = left.join();

            result = leftMat.combine(rightMat);
        }

        return result;
    }

    private Matrix multiply(int mat1Start, int mat1End, int mat2Start, int mat2End, int length){
        Matrix result = new Matrix(this.resultName, this.mat1.getCols(), this.mat2.getRows(), "");
        for(int i = mat1Start; i < mat1End; i++){
            for(int j = mat2Start; j < mat2End; j++){
                BigInteger mulResult = BigInteger.ZERO;
                for(int k = 0; k < length; k++){
                    mulResult = mulResult.add(mat1.getValue(k, i).multiply(mat2.getValue(j, k)));
                }
                result.insertValue(Integer.toString(i), Integer.toString(j), mulResult.toString());
            }
        }

        return result;
    }
    public String getRestultName(){
        return this.resultName;
    }

}
