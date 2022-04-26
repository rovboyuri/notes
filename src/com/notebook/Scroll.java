package com.notebook;

import javax.swing.*;

//Наследуемся от класса JScrollPane и добавляем поля
//text позволяет нам достать текст из приложения
//isOpen флаг открытия существующего файла
//path путь до заметки
public class Scroll extends JScrollPane {
    private final JTextArea text;
    private final boolean isOpen;
    private final String path;

    public Scroll(JTextArea text, boolean isOpen, String path) {
        super(text);
        this.text = text;
        this.isOpen = isOpen;
        this.path = path;
    }

    public boolean isOpened() {return isOpen;}

    public String getText() {
        return text.getText();
    }

    public String getPath() { return path; }

}
