package vt.smt.Client;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.util.Duration;
import vt.smt.Data.Toy;

import java.util.LinkedList;

/**
 * Created by semitro on 27.04.17.
 */
public interface Executor {
    void insertElemtnt(int index, Toy element);
    void removeElement(int index);
    void changeElement(int index, Toy element);
    void setCollection(LinkedList<Toy> list);

}
