package vt.smt;

/**
 * Created by semitro on 03.12.16.
 */
abstract class PhysicalObject implements Cleanable{
    // Вещи, которые могут быть в доме
    protected boolean isCleaning;
    public PhysicalObject(){
        java.util.Random rand = new java.util.Random();
        isCleaning = rand.nextBoolean();
    }
    public void cleanUp() {
        // Поставить вещь на место - прибрать
        isCleaning = true;
        System.out.println("Я убрана. Я вещь.");
    }
    public boolean isClean(){return isCleaning;}
    @Override
    public boolean equals(Object object){
        if(object instanceof PhysicalObject && this == object)
            return true;
        else
            return false;
    }
    @Override
    public String toString(){
        return new String(this.getClass().toString() + " #" + hashCode());
    }

}
