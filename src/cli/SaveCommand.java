package cli;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import main.App;
import matrix.MatrixBrain;

public class SaveCommand extends Command{

    public SaveCommand(MatrixBrain brain) {
        this.name = "save";
        this.requiredNumArgs = 0;
        this.brain = brain;
        this.supportedOptionalArgs = new ArrayList<>();
        this.supportedOptionalArgs.add("-file");
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
        String[] reformat = optionalArgs.split(" ");
        for (String r : reformat) {
            argsForBrain += r + ",";
        }
        try {
            brain.resolve(name, argsForBrain);
        } catch (InterruptedException | ExecutionException ex) {
            App.printErr("Oops, something went wrong");
            App.printErr(ex.toString());
        }
        System.out.println("SAVE");
    }

}
