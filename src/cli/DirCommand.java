package cli;

import main.App;
import main.Config;

public class DirCommand extends Command {

    public DirCommand() {
        this.name = "dir";
        this.requiredNumArgs = 1;
    }

    

    @Override
    public void execute(String args, String optionalArgs) {
        if(!checkArgs(args)){
            App.printErr("Wrong number of command arguments");
            return;
        }
        System.out.println(Config.MAX_ROW_SIZE);
    }

}
