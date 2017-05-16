package vt.smt.Client;

import vt.smt.Data.Toy;

import java.util.LinkedList;
/**
 * Не тот Экзекутор из Concurrent'а,
 * которым Письмак завалил меня на защите, нет
 * Просто мой класс, выполняющий то, что он должен выполнять
 */
public interface Executor {
    void insertElemtnt(int index, Toy element);
    void removeElement(int index);
    void changeElement(int index, Toy element);
    void setCollection(LinkedList<Toy> list);
}
