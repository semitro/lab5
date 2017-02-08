package vt.smt;

/**
 * Created by semitro on 03.12.16.
 */
// То, что может убираться/очищать
public interface Cleaner {
    // Спросить у препода - почему нужно указывать не только тип, но и имя параметра в сигнатуре интерфейса?
   void cleanUp(Cleanable thing);
}
