package cli;

import main.App;

public class StopCommand extends Command{

    public StopCommand() {
        this.name = "stop";
        this.requiredNumArgs = 0;
    }

    @Override
    public void execute(String args, String optionalArgs) {
        if(checkArgs(args)){
            App.printErr("Wrong number of command arguments");
            return;
        }
        System.out.println("STOP");
    }

}
