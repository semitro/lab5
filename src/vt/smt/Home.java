package vt.smt;

//import java.util.ArrayList;
import sun.security.ssl.Debug;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
       try {
           BufferedWriter writer = new BufferedWriter(new FileWriter(pathToFile));
           writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
           writer.newLine();
           writer.write("<PhysicalObjects>");
           writer.newLine();
           for (PhysicalObject i : things){
               writer.write("<Thing>"); writer.newLine();
               if(i instanceof Toy) {
                   writer.write("<Toy>"); writer.newLine();
                   writer.write("<Name>" + ((Toy) i).getName() + "</Name>"); writer.newLine();
                   writer.write("<Weight>" + ((Toy)i).getWeight() + "</Weight>"); writer.newLine();
                   writer.write("<IsClean>" + i.isClean() + "</IsClean>"); writer.newLine();
                   writer.write("</Toy>"); writer.newLine();
               }
               writer.write("</Thing>"); writer.newLine();
           }
           writer.write("</PhysicalObjects>"); writer.newLine();
           writer.flush();
           writer.close();
        }
        catch (FileNotFoundException e){
            Debug.println("Home.SaveThingsToFile", "File not found exception" + pathToFile);
        }
        catch (IOException e){
            Debug.println("Home.SaveThingsToFile()", e.getMessage());
        }
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
        return things.size();
    }
    private static int count = 0;
}