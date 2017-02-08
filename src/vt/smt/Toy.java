package vt.smt;


class Toy extends PhysicalObject{
    private String name;
    Toy(String name){
        super();
        this.name = name;
        weight = 8;
    }
    Toy(String name,double weight){
        super();
        this.name  = name;
        this.weight = weight;
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