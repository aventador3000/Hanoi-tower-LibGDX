package ru.kow.honoigame.screen;

import java.util.ArrayList;

public class BaseColona {
static BaseColona base;
    public ArrayList<Integer> colon1;
    public ArrayList<Integer> colon2;
    public ArrayList<Integer> colon3;
    public int level=0;
    public int ColonaActive=1;
    public static synchronized BaseColona getBase() {
        if(base==null){
            base=new BaseColona();
        }
        return base;
    }
}
