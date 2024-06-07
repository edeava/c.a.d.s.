package cli;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import main.App;
import matrix.MatrixBrain;

public class InfoCommand extends Command {

    public InfoCommand(MatrixBrain brain) {
        this.name = "info";
        this.brain = brain;
        this.requiredNumArgs = 0;
        this.supportedOptionalArgs = new ArrayList<>();
        this.supportedOptionalArgs.add("-all");
        this.supportedOptionalArgs.add("-asc");
        this.supportedOptionalArgs.add("-desc");
        this.supportedOptionalArgs.add("-s");
        this.supportedOptionalArgs.add("-e");
    }

    

    @Override
    public void execute(String args, String optionalArgs) {
        if(!checkArgs(args)){
            App.printErr("Wrong number of command arguments");
            return;
        }
        // System.out.println(args);
        // System.out.println(optionalArgs);
        String argsForBrain = "";
        if(args.equals("")){
            String[] reformat = optionalArgs.split(" ");
            for (String r : reformat) {
                argsForBrain += r + ",";
            }
        }else{
            argsForBrain = args;
        }
        try {
            String[] response = brain.resolve(name, argsForBrain).split(", ");
            for (String r : response) {
                App.printMsg(r.replace("[", "").replace("]", ""));
            }
        } catch (InterruptedException | ExecutionException ex) {
            App.printErr("Oops, something went wrong");
            ex.printStackTrace();
        }
        System.out.println("INFO");
    }

}
