package vt.smt.Server.Commands;
import vt.smt.Server.Server;

import java.util.List;

/**
 * Created by semitro on 18.04.17.
 */

public class GetBearsFromServer implements ServerCommand {
    private List l;
    @Override
    public void execute(Server server) {
        System.out.println("This message is from Get bears from");
    }
}
