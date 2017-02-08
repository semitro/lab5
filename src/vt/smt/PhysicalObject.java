package vt.smt;

/**
 * Created by semitro on 03.12.16.
 */
abstract class PhysicalObject implements Cleanable, Comparable<PhysicalObject>{
    // Вещи, которые могут быть в доме
    protected boolean isCleaning;
    protected double weight; // Вес
    public double getWeight(){
        return weight;
    }
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
    public int compareTo(PhysicalObject obj){
        if(this.getWeight() > obj.getWeight())
            return 1;
        if(this.getWeight() < obj.getWeight())
            return -1;

        return 0;
    }

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
