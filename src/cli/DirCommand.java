package cli;

import explorer.SystemExplorer;
import main.App;

public class DirCommand extends Command {

    private SystemExplorer sysExplorer;

    public DirCommand(SystemExplorer sysExplorer) {
        this.sysExplorer = sysExplorer;
        this.name = "dir";
        this.requiredNumArgs = 1;
    }

    

    @Override
    public void execute(String args, String optionalArgs) {
        if(!checkArgs(args)){
            App.printErr("Wrong number of command arguments");
            return;
        }
        
        sysExplorer.addDirectory(args);
    }

}
