package vt.smt.GUI;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by semitro on 13.05.17.
 */
public  class Language {
    private static ResourceBundle resourceBundle;
    public static void setLanguage(Locale locale){
        resourceBundle = ResourceBundle.getBundle("vt/smt/GUI/languages",locale);
    }
    public String getString(String key){
        return resourceBundle.getString(key);
    }
}
