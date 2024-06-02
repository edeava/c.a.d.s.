package task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.RecursiveTask;
import main.App;
import main.Config;
import matrix.Matrix;

public class CreateTask extends RecursiveTask<Matrix> implements Task {

    private final TaskType taskType = TaskType.CREATE;
    private String path;
    private long offset;
    private long length;
    private Matrix result;

    public CreateTask(String path) {
        this.path = path;
        this.offset = 0;
        this.length = (new File(path)).length();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String firstLine = reader.readLine();
            this.offset = firstLine.getBytes().length;
            String[] matParams = firstLine.split(",");
            String matName = matParams[0].trim().split("=")[1];
            int matRows = Integer.parseInt(matParams[1].trim().split("=")[1]);
            int matCols = Integer.parseInt(matParams[2].trim().split("=")[1]);
            this.result = new Matrix(matName, matRows, matCols);
        } catch (FileNotFoundException ex) {
            App.printErr("File " + path +" not fount!");
        } catch (IOException ex) {
            App.printErr("File " + path +" error!");
        } catch (Exception ex){
            App.printErr("File " + path +" error!");
        }
    }

    public CreateTask(String path, long offset, long length, Matrix resultMatrix) {
        this.path = path;
        this.offset = offset;
        this.length = length;
        this.result = new Matrix(resultMatrix.getName(), resultMatrix.getRows(), resultMatrix.getCols());
    }
    
    @Override
    public TaskType getType() {
        return this.taskType;
    }

    @Override
    protected Matrix compute() {
        if(length < Config.MAX_FILE_CHUNK){
            RandomAccessFile file;
            try {
                file = new RandomAccessFile(this.path, "r");
                file.seek(offset);
                while(offset > 0 && file.read() != '\n'){
                    offset--;
                    file.seek(offset);
                }
                offset++;
                file.seek(offset + length);
                
                while(offset + length < file.length() && file.read() != '\n'){
                    length++;
                    file.seek(offset + length);
                }
                length--;

                byte[] buffer = new byte[(int) length];
            
                int lengthOfRead = file.read(buffer);
                
                String content = new String(buffer, 0, lengthOfRead);
                insertResult(content);
                return result;
            } catch (FileNotFoundException ex) {
            } catch (IOException ex) {
                App.printErr("File " + path +" error!");
            }
        } 

        long mid = length / 2;
        CreateTask left = new CreateTask(path, offset, mid, this.result);
        CreateTask right = new CreateTask(path, offset + mid, length, this.result);

        left.fork();

        Matrix rightMatrix = right.compute();
        Matrix leftMatrix = left.join();

        Matrix resulMatrix = leftMatrix.combine(rightMatrix);


        return resulMatrix;

    }

    private void insertResult(String fromFile){
        String[] lines = fromFile.split("\n");
        for(String line : lines){
            String[] parts = line.trim().split("=");
            String[] indexes = parts[0].trim().split(",");
            String value = parts[1].trim();
            this.result.insertValue(indexes[0], indexes[1], value);
        }
    }

    public Matrix getResult(){
        return result;
    }
}
