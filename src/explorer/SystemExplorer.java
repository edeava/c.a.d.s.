package explorer;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import main.App;
import task.CreateTask;
import task.TaskQueue;

public class SystemExplorer {
    private static final String EXTENSION = ".rix";
    private static final long TIMEOUT = 60000; // 1 minute in milliseconds

    private String rootDirectory;
    private Map<String, Long> rixFiles;
    private Timer timer;
    private TaskQueue taskQueue;

    public SystemExplorer(String rootDirectory, TaskQueue taskQueue) {
        this.rootDirectory = rootDirectory;
        this.taskQueue = taskQueue;
        this.rixFiles = new HashMap<>();
        this.timer = new Timer();
    }

    public void start() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                explore(new File(rootDirectory));
            }
        }, 0, TIMEOUT);
    }

    private void explore(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    explore(file);
                } else if (file.getName().endsWith(EXTENSION)) {
                    String filePath = file.getAbsolutePath();
                    long lastModified = file.lastModified();
                    if(rixFiles.containsKey(filePath) && (rixFiles.get(filePath) >= lastModified)){
                        continue;
                    }

                    rixFiles.put(filePath, lastModified);
                    try {
                        taskQueue.push(new CreateTask());
                    } catch (InterruptedException ex) {
                        App.printErr(ex.getMessage());
                    }
                }
            }
        }
    }

    public Map<String, Long> getRixFiles() {
        return rixFiles;
    }

    public void stop() {
        timer.cancel();
    }
}