package matrix;

import java.util.concurrent.ForkJoinPool;
import task.CreateTask;
import task.Task;
import task.TaskType;

public class MatrixExtractor {
    private ForkJoinPool executorPool;
    private MatrixBrain brain;

    public MatrixExtractor() {
        executorPool = new ForkJoinPool();
    }

    public void recieveTask(Task t){
        if(t.getType() != TaskType.CREATE){
            return;
        }

        executorPool.submit((CreateTask)t);
    }
    
}
