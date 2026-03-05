package com.vachel.editor.bean;

import android.text.TextUtils;

/**
 * 粘贴文本
 */

public class StickerText {

    private String text;

    private int color;

    private int strokeColor;//描边颜色
    private int fontState;//字体状态

    public StickerText(String text, int color, int strokeColor,int fontState) {
        this.text = text;
        this.color = color;
        this.strokeColor = strokeColor;
        this.fontState = fontState;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isEmpty() {
        return TextUtils.isEmpty(text);
    }

    public int length() {
        return isEmpty() ? 0 : text.length();
    }

    public int getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
    }

    public int getFontState() {
        return fontState;
    }

    public void setFontState(int fontState) {
        this.fontState = fontState;
    }
}
