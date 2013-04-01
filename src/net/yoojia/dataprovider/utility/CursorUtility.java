package net.yoojia.dataprovider.utility;

public class CursorUtility {

	public static String[] toArgs(Object...values){
        String[] args = new String[values.length];
        for(int i=0;i<values.length;i++){
            args[i] = String.valueOf(values[i]);
        }
        return args;
    }
}
