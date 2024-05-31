package task;

import java.util.concurrent.Future;
import matrix.Matrix;

public interface Task {
    TaskType getType();
	// M_Matrix matrixA, matrixB;
	// File potentialMatrixFile;
	Future<Matrix> initiate();

}
