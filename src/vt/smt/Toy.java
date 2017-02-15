package vt.smt;


class Toy extends PhysicalObject{
    private String name;
    private Toy(){
        super();
    }
    Toy(String name){
        super();
        this.name = name;
        weight = 8;
    }
    Toy(String name,double weight, boolean isClean){
        super();
        this.name  = name;
        this.weight = weight;
        this.isCleaning = isClean;

    }
    public String getName(){
        return name;
    }
    @Override
    public void cleanUp(){
        isCleaning = true;
        System.out.println(name + " - Убрано");
    }
    @Override
    public String toString(){
        return name;
    }
}