package com.sige.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.border.AbstractBorder;

public class ShadowBorder extends AbstractBorder {
    private static final long serialVersionUID = 1L;
    private static final int RADIUS = 5;

    @Override
    public boolean isBorderOpaque() {
        return false;
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(RADIUS, RADIUS, RADIUS, RADIUS);
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.top = RADIUS;
        insets.left = RADIUS;
        insets.bottom = RADIUS;
        insets.right = RADIUS;
        return insets;
    }

    @Override
    public void paintBorder(ParameterShadowBorder parameterShadowBorder){
        parameterShadowBorder.setGraphics(setColor(Color.RED));
    }
}