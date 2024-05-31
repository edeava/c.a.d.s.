package cli;

import java.util.ArrayList;

public abstract class Command {
    ArrayList<String> supportedOptionalArgs;

    Integer requiredNumArgs;

    String name;

    
    abstract  void execute(String args, String optionalArgs);
    
    boolean checkArgs(String args){
        String[] commandArgs = args.split(",");
        System.out.println("Command arg num:" + commandArgs.length);
        return commandArgs.length == requiredNumArgs;
    }
    
    public String getName(){
        return name;
    }
}
