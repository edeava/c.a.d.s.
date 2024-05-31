package task;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class TaskQueue {
    private final BlockingQueue<Task> taskQueue;

    public TaskQueue() {
        this.taskQueue = new ArrayBlockingQueue<>(100);
    }

    public void push(Task t) throws InterruptedException{
        taskQueue.put(t);
    }

    public void pop() throws InterruptedException{
        taskQueue.take();
    }

}
