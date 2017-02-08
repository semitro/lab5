package vt.smt;

class Mother extends Human{
    public Mother(Home home){
        super(home);
        name = "Mother";
        howMuchRemind = 0;
        System.out.println(name + " was born");
        // У мамы разное настроение
        mood = Mood.getRand();
    }
    public void remind(Babyk babyk){
        String howRemind = null;
        switch (mood){
            case BAD:
                howRemind = "УБИРАТЬСЯ! ЖИВО!";
                break;
            case NORM:
                howRemind = "Малыш, уберись! ";
                break;
            case GOOD:
                howRemind = "Малыш, сегодня можешь не убираться, я сама :) ";
                break;

        }

        if(howMuchRemind == 0)
            System.out.println("Mother: " + howRemind);
        else
            System.out.printf("Mother: напоминаю тебе уже %d раз: " + howRemind + '\n', howMuchRemind);
        if(mood == Mood.GOOD){
            this.cleanUp(this.getHome());
            return;
        }
        howMuchRemind++;
            babyk.cleanUp(babyk.getHome());
    }
    @Override
    public void cleanUp(Cleanable toClean){
        try {
            toClean.cleanUp();
        }catch(AlreadyCleanException e){
            System.out.println("Mother: Исключительно не желаю убирать чистый дом!");
        }

    }
    // Сколько раз мама напоминала про уборку
    private int howMuchRemind;
}
