package vt.smt;

//import java.util.ArrayList;
import sun.security.ssl.Debug;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

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
        //things = new ArrayList<>();
        things = new Vector<>();
        for(int i = 0; i<10;i++)
            things.add(new Toy("Игрушка" + Integer.toString(i)));
        count++;

    }
    void addThing(PhysicalObject obj) { things.add(obj); }
 //////////////////////////////////////////////////////////////////

    public void reorder(){
        Collections.reverse(things);
    }
    public void addIfMax(PhysicalObject obj){
        for(PhysicalObject i : things){
            if(obj.compareTo(i) < 0)
                return;
            addThing(obj);
        }
    }

    public void insert(int index, PhysicalObject obj) {
        things.insertElementAt(obj,index);
    }

    public void clear() {
        things.clear();
    }
    private void sortThings(){
        Collections.sort(things, new Comparator<PhysicalObject>() {
            @Override
            public int compare(PhysicalObject physicalObject, PhysicalObject t1) {
                return physicalObject.compareTo(t1);
            }
        });
    }
    //////////////////////////////////////////////
    public void saveThingsToFile(String pathToFile){

    }
    public void loadThingsFromFile(String pathToFile){
        things.clear();
        try {
            XmlParser parser = new XmlParser(pathToFile);
            while (parser.hasNext()) things.add(parser.getNext());
        }
        catch (FileNotFoundException e){
            Debug.println("Home.loadThingsFromFile","File not found" + pathToFile);
        }
    }
    ////////////////////////////////////////////////
    public boolean isClean(){return isClean;}
   // private ArrayList<PhysicalObject> things;
    private Vector<PhysicalObject> things;
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