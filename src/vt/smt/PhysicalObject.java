package vt.smt;

import java.io.Serializable;

/**
 * Created by semitro on 03.12.16.
 */
public abstract class PhysicalObject
        implements Cleanable, Comparable<PhysicalObject>,Serializable {

    public PhysicalObject(PhysicalObject copy){
        this.setWeight(copy.getWeight());
        this.setisCleaning(copy.isClean());
    }
    // Вещи, которые могут быть в доме
    protected boolean isCleaning;
    protected double weight; // Вес
    public void setisCleaning(boolean clean){
        isCleaning = clean;

    }
    public boolean isCleaning() {
        return isCleaning;
    }
    public void setCleaning(boolean cleaning) {
        isCleaning = cleaning;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }
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
        return (int)Math.floor(this.getWeight()-obj.getWeight());
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
        return new String();
    }

}
