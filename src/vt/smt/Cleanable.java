package vt.smt;

/**
 * Created by semitro on 03.12.16.
 */
// Нечто, которое можно убрать на место / очистить
public interface Cleanable {
    void cleanUp() throws AlreadyCleanException;
    boolean isClean();
}
