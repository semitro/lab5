package vt.smt;

public class Main {

    public static void main(String[] args) throws Exception{
        Plot plot = new Plot();

        if(args.length  >= 2)
            try {
                vt.smt.Client.Sender.initAddr(args[0], Integer.parseInt(args[1]));
            }catch (Exception e){
                e.getMessage();
                e.printStackTrace();
                System.out.println("Лови экзепшены, уважаемый пользователь!!");
                System.out.println("Вводите сначала адрес, а потом порт. И без шуток, а то позже отхватите");
                System.out.println("Да, всё крашнется ВНЕЗАПНО И ПОТОМ при некорректном номере порта");
                System.out.println("До встречи.");
                System.exit(0);
            }
            else
        if(args.length > 0 ) {
                if(args[0].contains("?"))
                    System.out.println("usage: addr, port");
                else
                if(args[0].contains("console"))
                    plot.startConsoleMode();
            System.exit(0);
        }

            plot.startGUI();
    }
}
