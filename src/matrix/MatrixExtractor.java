package matrix;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import task.CreateTask;
import task.Task;
import task.TaskType;

public class MatrixExtractor {
    private ForkJoinPool executorPool;
    private MatrixBrain brain;

    public MatrixExtractor(MatrixBrain brain) {
        this.brain = brain;
        executorPool = new ForkJoinPool(2);
    }

    public void recieveTask(Task t){
        if(t.getType() != TaskType.CREATE){
            return;
        }
        CreateTask recievedTask = (CreateTask) t;
        String matrixName = recievedTask.getResult().getName();

        Future<Matrix> futureToSend = executorPool.submit(recievedTask);

        brain.recieveMatrix(matrixName, futureToSend, true);
    }
    
}
