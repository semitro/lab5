package vt.smt;

/**hj
 * Created by semitro on 03.12.16.
 */
abstract class Human implements Cleaner{
    protected Home home;
    protected String name;
    private Human(){
      // Потребовалось для старины Джэксона
    }
    protected Mood mood;
    public Human(Home home){
        this.home = home;
        mood = Mood.NORM;
    };
    public Home getHome() throws ThereIsNotHomeException{
        if(home == null)
            throw new ThereIsNotHomeException();
        else
            return home;
    }
    public String getName(){
        return name;
    }
    @Override
    abstract public void cleanUp(Cleanable cleanable);
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Human)
            if (this.hashCode() == obj.hashCode())
                return true;
        return false;
    }
    @Override public String toString(){
        return new String("Human. Name: " + name);
    }
    public static enum Mood {
        GOOD,
        NORM,
        BAD;
        static Mood getRand(){
            java.util.Random rand = new java.util.Random();
            return Mood.values()[rand.nextInt(3)];
        }
    }
}
