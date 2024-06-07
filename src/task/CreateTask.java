package task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.RecursiveTask;
import main.App;
import main.Config;
import matrix.Matrix;

public class CreateTask extends RecursiveTask<Matrix> implements Task {

    private final TaskType taskType = TaskType.CREATE;
    private String path;
    private long start;
    private long end;
    private Matrix result;

    public CreateTask(String path) {
        this.path = path;
        this.start = 0;
        this.end = (new File(path)).length();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String firstLine = reader.readLine();
            this.start = firstLine.getBytes().length;
            String[] matParams = firstLine.split(",");
            String matName = matParams[0].trim().split("=")[1];
            int matRows = Integer.parseInt(matParams[1].trim().split("=")[1]);
            int matCols = Integer.parseInt(matParams[2].trim().split("=")[1]);
            this.result = new Matrix(matName, matRows, matCols, path);
            reader.close();
        } catch (FileNotFoundException ex) {
            App.printErr("File " + path +" not fount!");
        } catch (IOException | NumberFormatException ex) {
            App.printErr("File " + path +" error!");
        }
    }

    public CreateTask(String path, long start, long end, Matrix resultMatrix) {
        this.path = path;
        this.start = start;
        this.end = end;
        this.result = new Matrix(resultMatrix.getName(), resultMatrix.getRows(), resultMatrix.getCols(), resultMatrix.getFileName());
    }
    
    @Override
    public TaskType getType() {
        return this.taskType;
    }

    @Override
    protected Matrix compute() {
        // System.out.println(offset + " ------> " + length + "======" + (length - offset));
        long offset = this.start;
        long length = this.end;
        if(length - offset < Config.MAX_FILE_CHUNK){
            RandomAccessFile file;
            try {
                file = new RandomAccessFile(this.path, "r");
                // offset++;
                file.seek(offset);

                while (offset > 0) {
                    int ch = file.read();
                    if (ch == '\n' || ch == '\r') {
                        offset++;
                        break;
                    }
                    offset--;
                    file.seek(offset);
                }
                
                file.seek(length);
                
                while (length < file.length()) {
                    int ch = file.read();
                    if (ch == '\n' || ch == '\r') {
                        length--;
                        break;
                    }
                    length--;
                    file.seek(length);
                }
                file.seek(offset);
                // System.out.println(length - offset);

                byte[] buffer = new byte[(int) (length - offset)];
            
                int lengthOfRead = file.read(buffer);
                file.close();
                
                String content = new String(buffer, StandardCharsets.UTF_8);
                // System.out.println(content);
                if(content.length() == 0){
                    System.out.println("prazan");
                }
                insertResult(content);
                return result;
            } catch (FileNotFoundException ex) {
            } catch (IOException ex) {
                App.printErr("File " + path +" error!");
            }
        } 

        long mid = (length - offset) / 2 + offset;
        CreateTask left = new CreateTask(path, offset, mid, this.result);
        CreateTask right = new CreateTask(path, mid, length, this.result);

        left.fork();

        Matrix rightMatrix = right.compute();
        Matrix leftMatrix = left.join();

        Matrix resulMatrix = leftMatrix.combine(rightMatrix);


        return resulMatrix;

    }

    private void insertResult(String fromFile){
        String[] lines = fromFile.split("\n");
        for(String line : lines){
            if(line.isEmpty() || line.isBlank()) continue;
            // App.printMsg(line);
            String[] parts = line.trim().split("=");
            String[] indexes = parts[0].trim().split(",");
            if(parts.length == 1 || indexes.length == 1){
                System.out.println(fromFile + fromFile.length());
            }
            String value = parts[1].trim();
            if(indexes[0].equals("") || indexes[1].equals("")){
                System.out.println(fromFile + fromFile.length());
            }
            this.result.insertValue(indexes[0], indexes[1], value);
        }
    }

    public Matrix getResult(){
        return result;
    }
}
