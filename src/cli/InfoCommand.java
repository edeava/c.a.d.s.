package cli;

import java.util.ArrayList;
import main.App;

public class InfoCommand extends Command {

    public InfoCommand() {
        this.name = "clear";
        this.requiredNumArgs = 1;
        this.supportedOptionalArgs = new ArrayList<>();
        this.supportedOptionalArgs.add("-all");
        this.supportedOptionalArgs.add("-asc");
        this.supportedOptionalArgs.add("-desc");
        this.supportedOptionalArgs.add("-s");
        this.supportedOptionalArgs.add("-e");
    }

    

    @Override
    public void execute(String args, String optionalArgs) {
        if(checkArgs(args)){
            App.printErr("Wrong number of command arguments");
            return;
        }
        System.out.println("INFO");
    }

}
