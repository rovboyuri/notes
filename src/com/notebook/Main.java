package com.notebook;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    private JTabbedPane tabs = new JTabbedPane();
    private final JFileChooser f = new JFileChooser("./Notes/");
    private static final String NEW_NOTE = "Новая заметка";

    public static void main(String[] args){
        new Main();
    }
    public Main() {
        //Добавление расширения для заметок
        FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("Файлы заметок", "nt");
        f.setFileFilter(fileFilter);

        //создаем окно
        JFrame window = new JFrame("Notebook");
        window.setSize(800, 600);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setVisible(true);

        //создаем меню бар

        JMenuBar menuBar = new JMenuBar();
        //добавляем вкладку "заметки"

        JMenu notes = new JMenu("Заметки");
        //добавляем вкладки подменю

        JMenuItem newNote = new JMenuItem("Создать заметку");
        JMenuItem openNote = new JMenuItem("Открыть заметку");
        JMenuItem saveNote = new JMenuItem("Сохранить заметку");
        JMenuItem deleteNote = new JMenuItem("Удалить заметку");

        //собираем меню бар
        notes.add(newNote);
        notes.add(openNote);
        notes.add(saveNote);
        notes.add(deleteNote);
        menuBar.add(notes);

        //добавляем меню в окно
        window.setJMenuBar(menuBar);
        window.add(tabs);

        //Добавление новой заметки с текстом  при открытии приложения
        JTextArea textArea = new JTextArea("Добро пожаловать в приложение для заметок!\n" +
                "\n" +
                "Приложение работает с фалами формата .nt\n" +
                "Функционал:\n" +
                "1. Добавление новых заметок\n" +
                "2. Открытие заметок формата .nt\n" +
                "3. Удаление заметок");
        Scroll scroll = new Scroll(textArea, false, "");
        tabs.addTab("readme", scroll);

        //Добавление новой заметки
        newNote.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JTextArea textArea = new JTextArea();
                Scroll scroll = new Scroll(textArea, false, "");
                tabs.addTab(NEW_NOTE, scroll);
            }
        });

        //Сохранение заметки
        saveNote.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                Scroll text = (Scroll) tabs.getSelectedComponent();
                String output = text.getText();

                if(tabs.getComponentCount() != 0) {

                    if(text.isOpened()) {
                        //Если заметка открыта существующая заметка
                        try {
                            FileOutputStream writer = new FileOutputStream(text.getPath());
                            writer.write(output.getBytes());
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        //Если заметки еще не существует, то спрашиваем имя заметки
                        String name = (String)JOptionPane.showInputDialog(
                                window,
                                "Название",
                                "Сохранение",
                                JOptionPane.PLAIN_MESSAGE,
                                null,
                                null,
                                "Новая заметка");
                        try {
                            FileOutputStream writer = new FileOutputStream("./Notes/" + name + ".nt");
                            writer.write(output.getBytes());
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });

        //Открытие заметки
        openNote.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    f.showOpenDialog(null);
                    File file = f.getSelectedFile();

                    String input = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));

                    JTextArea text = new JTextArea(input);

                    Scroll scroll = new Scroll(text, true, file.getAbsolutePath());

                    tabs.addTab(file.getName(), scroll);
                } catch(IOException exception) {exception.printStackTrace();}
            }
        });

        //Удаление заметки
        deleteNote.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                f.showDialog(null, "Удаление");
                File file = f.getSelectedFile();
                file.delete();
            }
        });
    }
}
