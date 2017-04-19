package vt.smt.Server.Commands;

import vt.smt.Server.Server;

import java.util.Collections;
import java.util.List;

/**
 * Created by semitro on 18.04.17.
 */
public class SaveOnServer implements ServerCommand {
    List<?> data;
    public SaveOnServer(List objects){
       // Collections.copy(data,objects);
    }
    public void execute(Server server){
        System.out.println("Save on server done");
    }

}
