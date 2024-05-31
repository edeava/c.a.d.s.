package cli;

import main.App;

public class ClearCommand extends Command{

    public ClearCommand() {
        this.name = "clear";
        this.requiredNumArgs = 1;
    }



    @Override
    public void execute(String args, String optionalArgs) {
        if(checkArgs(args)){
            App.printErr("Wrong number of command arguments");
            return;
        }
        
        System.out.println("CLEAR");
    }
}
