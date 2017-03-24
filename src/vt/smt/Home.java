package vt.smt;

import sun.security.ssl.Debug;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class Home implements Cleanable{
    public void cleanUp() throws AlreadyCleanException{
        if(isClean())
            throw new AlreadyCleanException();
        // Убрать дом - поставить каждую вещь на место
        for(PhysicalObject i : things)
            i.cleanUp();
        isClean = true;
    }
    public Home(){
        isClean = false;
        things = new Vector<>();
    }
    void addThing(PhysicalObject obj) { things.add(obj); }
 //////////////////////////////////////////////////////////////////
    /**
     * <p>Задом-наперёдит коллекцию things</p>
     */
    public void reorder(){
        Collections.reverse(things);
    }
    /**
     * Добавляет элемент в коллекцию, если его вес превосходит вес остальных элеметов коллекции
     * @param obj Элемент, потенциально добавляемый в коллекцию
     */
    public void addIfMax(PhysicalObject obj){
        for(PhysicalObject i : things){
            if(obj.compareTo(i) < 0)
                return;
        }
        addThing(obj);
    }
    /**
     * <p> Вставляет элемент в коллекцию на заданную позицию</p>
     * @param index позиция элемента (счёт от нуля)
     * @param obj сам элемент
     */
    public void insert(int index, PhysicalObject obj) {
        things.insertElementAt(obj,index);
    }
    /**
     * <p>Очищает коллекцию things</p>
     */
    public void clear() {
        things.clear();
    }
    public void sortThings(){
        Collections.sort(things);
    }
    public void showCollection(){
        if(things.size() == 0)
            System.out.println("Коллекция пуста");
        for(PhysicalObject i : things)
            System.out.println(i.toString());
    }
    public List<PhysicalObject> getThings(){
        return this.things;
    }
    //////////////////////////////////////////////
    public void saveThingsToFile(String pathToFile, List<PhysicalObject> collection){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(pathToFile));
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            writer.newLine();
            writer.write("<PhysicalObjects>");
            writer.newLine();
            for (PhysicalObject i : collection){
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
    public void saveThingsToFile(String pathToFile){
       saveThingsToFile(pathToFile,this.things);
    }
    public void loadThingsFromFile(String pathToFile){
            things.clear();
            XmlParser parser = new XmlParser(pathToFile);
            while (parser.hasNext()) things.add(parser.getNext());
    }
    ////////////////////////////////////////////////
    public boolean isClean(){return isClean;}

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
    @Override
    public String toString() {
        return Integer.toString(things.size());
    }
    @Override
    public int hashCode(){
        return things.size();
    }
    private Vector<PhysicalObject> things;
    private boolean isClean;

}