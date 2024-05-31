package task;

import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.Future;
import matrix.Matrix;

public class CreateTask extends ForkJoinTask<Object> implements Task {

    private final TaskType taskType = TaskType.CREATE;
    private String path;

    public CreateTask(String path) {
        this.path = path;
    }
    
    @Override
    public Future<Matrix> initiate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public TaskType getType() {
        return this.taskType;
    }

    @Override
    public Object getRawResult() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void setRawResult(Object value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected boolean exec() {
        throw new UnsupportedOperationException("Not supported yet.");
    }



}
