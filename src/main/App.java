package main;

import cli.CommandListener;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println(Config.EXPLORER_TIMEOUT);
        CommandListener console = new CommandListener();
        Thread commandThread = new Thread(console);
        commandThread.start();
    }

    public static void printMsg(String msg){
        System.out.println(msg);
    }

    public static void printErr(String err){
        System.err.println(err);
    }
}
