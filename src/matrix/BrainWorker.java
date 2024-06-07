package matrix;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import main.App;
import main.Config;

public class BrainWorker implements Callable{
    private MatrixBrain brain;
    private String workName;
    private String args;

    public BrainWorker(MatrixBrain brain, String workName, String args) {
        this.brain = brain;
        this.workName = workName;
        this.args = args;
    }

    @Override
    public Object call() throws Exception {
        if(workName.equals("info")){
            Set<Entry<String, Future<Matrix>>> entrySet = brain.getAll();
            ArrayList<Matrix> allMatrices = new ArrayList<>();
            for(Entry<String, Future<Matrix>> singleEntry : entrySet){
                if(singleEntry.getValue().isDone()){
                    System.out.println("it's info");
                    System.out.println(singleEntry.getValue());
                    allMatrices.add(singleEntry.getValue().get());
                }
            }
            allMatrices.sort(Comparator.naturalOrder());
            return allMatrices.toString();
        }else if(workName.equals("save")){
            int namePlace = args.indexOf("-name") + 5;
            int filePlace = args.indexOf("-file") + 5;
            String nameArg = args.substring(namePlace, filePlace - 5).replace(",", "");
            String fileArg = args.substring(filePlace).replace(",", "");
            System.out.println(nameArg + "---->" + fileArg);
            if(brain.getSingle(nameArg) instanceof Matrix){
                Matrix toPrint = (Matrix) brain.getSingle(nameArg);
                String pathToSave = Config.START_DIR + "/" + fileArg + ".rix";
                toPrint.setFilePath(pathToSave);
                PrintWriter out = new PrintWriter(pathToSave);
                out.print(toPrint.toFile());
                out.close();
            }else{
                App.printErr((String)brain.getSingle(nameArg));
            }
        }else{
            
        }
        return null;
    }
}
