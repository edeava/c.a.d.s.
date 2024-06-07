package matrix;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import task.MultiplyTask;
import task.Task;
import task.TaskType;

public class MatrixMultiplier {
    private ForkJoinPool executorPool;
    private MatrixBrain brain;

    public MatrixMultiplier(MatrixBrain brain) {
        this.brain = brain;
        executorPool = new ForkJoinPool(2);
    }

    public void recieveTask(Task t){
        if(t.getType() != TaskType.MULTIPLY){
            return;
        }
        MultiplyTask recievedTask = (MultiplyTask) t;
        String matrixName = recievedTask.getRestultName();

        Future<Matrix> futureToSend = executorPool.submit(recievedTask);

        brain.recieveMatrix(matrixName, futureToSend, false);
    }



}
