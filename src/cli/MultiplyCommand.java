package cli;

import java.util.ArrayList;
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
        if(checkArgs(args)){
            App.printErr("Wrong number of command arguments");
            return;
        }
        System.out.println("MULTIPLY");
    }

}
