package vt.smt;

/**
 * Created by semitro on 08.02.17.
 */
public interface CollectionHandler {
    void insert(int index, Object obj);
    void reorder(); // Задом-наперёд
    void clear(); // Очистить
    void addIfMax();
}
