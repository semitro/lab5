package vt.smt;

import java.util.ArrayList;

class Home implements Cleanable{
    public void cleanUp() throws AlreadyCleanException{
        if(isClean())
            throw new AlreadyCleanException();
        // Убрать дом - поставить каждую вещь на место
        for(PhysicalObject i : things)
            i.cleanUp();
        isClean = true;
    }
    Home(){
        isClean = false;
        things = new ArrayList<>();
        for(int i = 0; i<10;i++)
            things.add(new Toy("Игрушка" + Integer.toString(i)));
        count++;

    }
    void addThing(PhysicalObject obj) { things.add(obj); }
    public boolean isClean(){return isClean;}
    private ArrayList<PhysicalObject> things;
    private boolean isClean;

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
    @Override
    public String toString(){
        return new String("Home #" + this.hashCode());
    }
    @Override
    public int hashCode(){
        return count;
    }


    private static int count = 0;
}