package main;

import cli.CommandListener;

public class App {
    public static void main(String[] args) throws Exception {
        CommandListener console = new CommandListener();
        new Thread(console).start();
        System.out.println(Config.EXPLORER_TIMEOUT);
    }

    public static void printMsg(String msg){
        System.out.println(msg);
    }

    public static void printErr(String err){
        System.err.println(err);
    }
}
