package vt.smt.Client;

/**
 * Created by semitro on 23.04.17.
 */
import vt.smt.Commands.*;

import java.io.IOException;

public class InputCommandsHandler {
    private static Executor executor;
    private static NoticeSystem noticer;
    private static boolean isOn = false;

    public static void start(){
        if(isOn)
            return;
        isOn = true;
        Thread t = new Thread(()->{handleInputStream();});
        t.setDaemon(true);
        t.start();
    }
    public static void initExecutor(Executor worker){
        executor = worker;
    }
    public static void initNoticeSystem(NoticeSystem noticeSystem){
        noticer = noticeSystem;
    }
    private static void handleInputStream() {
        ServerAnswer command = null;
        while (true) {
            try {
                command = null;
                try {
                    Thread.currentThread().sleep(1000);
                }catch (InterruptedException e){

                }
                command = Sender.getInstance().nextCommand();
                System.out.println("Получена команда" + command.toString());
                if (command instanceof ChangeBear)
                    executor.changeElement(
                            ((ChangeBear) command).getIndex(), ((ChangeBear) command).getBear());
                if (command instanceof SaveAllBears)
                    executor.setCollection(((SaveAllBears) command).getData());
                if(command instanceof RemoveBear) {
                    System.out.println("Собираюсь отправить на выполнение удаление элемента № " +((RemoveBear) command).getIndex() );
                    executor.removeElement(((RemoveBear) command).getIndex());
                }
                if(command instanceof InsertBear){
                    executor.insertElemtnt(((InsertBear)command).getIndex(),
                                           ((InsertBear)command).getBear());
                }
                if(command instanceof  vt.smt.Commands.Message){
                    if(noticer != null)
                        noticer.notice(((vt.smt.Commands.Message)command).getMessage());
                    else
                        System.out.println( ((vt.smt.Commands.Message)command).getMessage());
                }
            } catch (IOException e) {
                System.out.println("Отвалился входной поток (Handler::handleInputStream)");
                e.printStackTrace();
            }
        }
    }

}
