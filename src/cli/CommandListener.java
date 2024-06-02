package cli;

import explorer.SystemExplorer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import main.App;
import main.Config;
import task.TaskQueue;

public class CommandListener implements Runnable {

    private final TaskQueue taskQueue = new TaskQueue();
    private final SystemExplorer systemExplorer = new SystemExplorer(Config.START_DIR, taskQueue);

    private final List<Command> commandList;
    private volatile boolean shouldRun = true;

    public CommandListener() {
        systemExplorer.start();
        this.commandList = new ArrayList<>();
        this.commandList.add(new ClearCommand());
        this.commandList.add(new DirCommand(systemExplorer));
        this.commandList.add(new InfoCommand());
        this.commandList.add(new MultiplyCommand());
        this.commandList.add(new SaveCommand());
        this.commandList.add(new StopCommand());
    }

    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);

        while (shouldRun && sc.hasNextLine()) {
            String line = sc.nextLine();
            line = line.trim();
            int nameFrom = line.contains(" ") ? line.indexOf(" ") : line.length() - 1;
            int optionalArgsFrom = line.indexOf("-");
            String name = line.substring(0, nameFrom);

            String commandName = name;
            String args = "";
            String optionalArgs = "";
            if(optionalArgsFrom != -1){
                args = line.substring(nameFrom, optionalArgsFrom);
                optionalArgs = line.substring(optionalArgsFrom);
            } else if(nameFrom != line.length()){
                args = line.substring(nameFrom);
            }
            args = args.trim();
            optionalArgs = optionalArgs.trim();

            Boolean commandExists = false;
            for(Command cmd : commandList){
                if(cmd.getName().equals(commandName)){
                    cmd.execute(args, optionalArgs);
                    commandExists = true;
                    break;
                }
            }
            if(!commandExists){
                App.printErr("Unknown command");
            }
        }

        sc.close();
    }

    public void stop(){
        shouldRun = false;
    }

}
