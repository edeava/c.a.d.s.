package task;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import main.App;

public class TaskQueue {
    private final BlockingQueue<Task> taskQueue;

    public TaskQueue() {
        this.taskQueue = new ArrayBlockingQueue<>(100);
    }

    public void push(Task t) throws InterruptedException{
        App.printMsg("Added Task to queue");
        taskQueue.put(t);
    }

    public Task pop() throws InterruptedException{
        App.printMsg("Task removed");
        return taskQueue.take();
    }

}
