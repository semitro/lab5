package vt.smt.Client;

/**
 * Created by semitro on 23.04.17.
 */
import vt.smt.GUI.BearsLine;
import vt.smt.Commands.*;

import java.io.IOException;

public class InputCommandsHandler {
    private BearsLine executor;
    public InputCommandsHandler(BearsLine executor){
        this.executor = executor;
        Thread t = new Thread(this::handleInputStream);
        t.setDaemon(true);
        t.start();
    }
    private void handleInputStream() {
        ServerAnswer command = null;
        while (true) {
            try {
                command = null;
                command = Sender.getInstance().nextCommand();
                System.out.println("Получена команда" + command.toString());
                if (command instanceof ChangeBear)
                    executor.changeElement(
                            ((ChangeBear) command).getIndex(), ((ChangeBear) command).getBear());
                if (command instanceof SaveAllBears)
                    executor.setCollection(((SaveAllBears) command).getData());
            } catch (IOException e) {
                System.out.println("Отвалился входной поток (Handler::hanleUnoutStrean)");
                System.out.println(e.getMessage());
            }
        }
    }

}
