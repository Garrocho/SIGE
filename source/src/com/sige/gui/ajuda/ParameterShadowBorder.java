package com.sige.gui.ajuda;

public class ParameterShadowBorder {
    private Component component;
    private Graphics exp;
    private int j;
    private int k;
    private int l; 
    private int i1;

    public ParameterShadowBorder (Component _component, Graphics _g, int _j, int _k, int _l, int _i1){
        this.component = _component;
        this.g = _g;
        this.j = _j;
        this.k = _k;
        this.l = _l;
        this.i1 = _i1;        
    }
    public void setComponent(Component comp){
        this.component = comp;
    }

    public Component getComponent(Component comp){
        return this.component;
    }
    public void setGraphics(Graphics g ){
        this.g = g;
    }

    public Graphics getGraphics(Graphics g){
        return this.g;
    }

    public void setJ(int j){
        this.j = j;
    }

    public int getJ(int j){
        return this.j;
    }

    public void setK(int k ){
        this.k = k;
    }

    public int getK(int k){
        return this.k;
    }

    public void setL(int l ){
        this.l = l;
    }

    public int getL(int l){
        return this.l;
    }

    public void setI(int i ){
        this.i1 = i;
    }

    public int getI(int i){
        return this.i1;
    }
}