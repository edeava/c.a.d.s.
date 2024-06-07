package task;

import matrix.MatrixExtractor;
import matrix.MatrixMultiplier;

public class TaskCoordinator implements Runnable{
    private final TaskQueue taskQueue;
    private boolean running;
    private MatrixExtractor extractor;
    private MatrixMultiplier multiplier;

    public TaskCoordinator(TaskQueue taskQueue, MatrixExtractor extractor, MatrixMultiplier multiplier) {
        this.taskQueue = taskQueue;
        this.extractor = extractor;
        this.multiplier = multiplier;
        this.running = true;
    }
    
    @Override
    public void run() {
        while (running) {
            try {
                Task task = taskQueue.pop();
                processTask(task);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
    
    private void processTask(Task task) {
        if(task.getType() == TaskType.CREATE){
            extractor.recieveTask(task);
        }else if(task.getType() == TaskType.MULTIPLY){
            multiplier.recieveTask(task);
        }
        System.out.println("Processing task: " + task);
    }
    
    public void stop() {
        running = false;
        Thread.currentThread().interrupt();
    }
}
