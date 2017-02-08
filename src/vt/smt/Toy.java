package vt.smt;


class Toy extends PhysicalObject{
    private String name;
    Toy(String name){
        super();
        this.name = name;
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