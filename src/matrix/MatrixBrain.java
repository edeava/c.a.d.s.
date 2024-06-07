package matrix;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import main.App;
import task.MultiplyTask;
import task.TaskQueue;

public class MatrixBrain {
    private ConcurrentHashMap<String, Future<Matrix>> cache;
    private ExecutorService threadPool;
    private TaskQueue taskQueue;
        

    public MatrixBrain(TaskQueue taskQueue) {
        this.cache = new ConcurrentHashMap<>();
        this.threadPool = Executors.newCachedThreadPool();
        this.taskQueue = taskQueue;
    }

    public void recieveMatrix(String name, Future<Matrix> matrix, boolean extracted){
        System.out.println("recieved:" + name + " : " + matrix.isDone());
        if(!cache.contains(name)){
            cache.put(name, matrix);
            if(extracted){
                CompletableFuture.supplyAsync(() ->{
                    try {
                        return matrix.get();
                    } catch (InterruptedException | ExecutionException ex) {
                        throw new RuntimeException(ex);
                    }
                }).thenAcceptAsync(m -> {
                    try {
                        taskQueue.push(new MultiplyTask(m, Matrix.transpose(m), m.getName() + "^2"));
                    } catch (InterruptedException ex) {
                    }
                }, threadPool);
            } 
        }
    }

    public String resolve(String workName, String args) throws InterruptedException, ExecutionException{
        String response = (String) threadPool.submit(new BrainWorker(this, workName, args)).get();

        return response;
    }

    public Object getSingle(String name){
        if(cache.containsKey(name)){
            if(cache.get(name).isDone()){
                try {
                    return cache.get(name).get();
                } catch (InterruptedException | ExecutionException e) {
                    App.printErr(e.getMessage());
                }
            }
            return "Task not done yet!";
        }
        return "Unknown matrix";
    }

    public Set<Map.Entry<String, Future<Matrix>>> getAll(){
        return cache.entrySet();
    }

}
