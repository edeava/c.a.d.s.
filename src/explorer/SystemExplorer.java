package explorer;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import main.App;
import main.Config;
import task.CreateTask;
import task.TaskQueue;

public class SystemExplorer {
    private static final String EXTENSION = ".rix";

    private ArrayList<String> directories = new ArrayList<>();
    private Map<String, Long> rixFiles;
    private Timer timer;
    private TaskQueue taskQueue;

    public SystemExplorer(String rootDirectory, TaskQueue taskQueue) {
        this.directories.add(rootDirectory);
        this.taskQueue = taskQueue;
        this.rixFiles = new HashMap<>();
        this.timer = new Timer();
    }

    public void start() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                for(String path : directories){
                    explore(new File(path));
                }
            }
        }, 0, Config.EXPLORER_TIMEOUT);
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
                    System.out.println("Changed:" + filePath);
                    rixFiles.put(filePath, lastModified);
                    try {
                        taskQueue.push(new CreateTask(filePath));
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

    public void addDirectory(String path){
        System.out.println(path);
        Path absolutePath = Paths.get(path);
        if(!absolutePath.isAbsolute()){
            absolutePath = absolutePath.toAbsolutePath();
        }
        System.out.println(absolutePath.toString());
        directories.add(absolutePath.toString());
    }

    public void stop() {
        timer.cancel();
    }
}