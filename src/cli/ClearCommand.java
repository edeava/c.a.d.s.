package cli;

import explorer.SystemExplorer;
import java.util.concurrent.ExecutionException;
import main.App;
import matrix.MatrixBrain;

public class ClearCommand extends Command{
    private SystemExplorer systemExplorer;

    public ClearCommand(MatrixBrain brain, SystemExplorer systemExplorer) {
        this.brain = brain;
        this.systemExplorer = systemExplorer;
        this.name = "clear";
        this.requiredNumArgs = 1;
    }



    @Override
    public void execute(String args, String optionalArgs) {
        if(!checkArgs(args)){
            App.printErr("Wrong number of command arguments");
            return;
        }
        // System.out.println(args);
        // System.out.println(optionalArgs);

        try {
            brain.resolve(name, args);
            if(args.contains(".rix")){
                systemExplorer.removeFile(args);
            }
        } catch (InterruptedException | ExecutionException ex) {
            App.printErr("Oops, something went wrong");
        }
        
        System.out.println("CLEAR");
    }
}
