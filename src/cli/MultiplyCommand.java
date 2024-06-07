package cli;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import main.App;

public class MultiplyCommand extends Command{

    public MultiplyCommand() {
        this.name = "multiply";
        this.requiredNumArgs = 2;
        this.supportedOptionalArgs = new ArrayList<>();
        this.supportedOptionalArgs.add("-async");
        this.supportedOptionalArgs.add("-name");
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
        String[] reformat = args.split(" ");
        for (String r : reformat) {
            argsForBrain += r + ",";
        }
        reformat = optionalArgs.split(" ");
        for (String r : reformat) {
            argsForBrain += r + ",";
        }
        try {
            brain.resolve(name, argsForBrain);
        } catch (InterruptedException | ExecutionException ex) {
            App.printErr("Oops, something went wrong");
        }
        System.out.println("MULTIPLY");
    }

}
