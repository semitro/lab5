package vt.smt.Server.Commands;

import vt.smt.Server.Server;

/**
 * Created by semitro on 19.04.17.
 */
public interface ServerCommand extends Command {
    public void execute(Server server);

}
