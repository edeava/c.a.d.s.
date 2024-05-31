package cli;

import main.App;

public class SaveCommand extends Command{

    public SaveCommand() {
        this.name = "save";
        this.requiredNumArgs = 0;
    }

    

    @Override
    public void execute(String args, String optionalArgs) {
        if(checkArgs(args)){
            App.printErr("Wrong number of command arguments");
            return;
        }
        System.out.println("SAVE");
    }

}
