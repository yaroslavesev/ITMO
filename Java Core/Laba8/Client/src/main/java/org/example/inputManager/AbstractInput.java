package org.example.inputManager;

import java.util.Scanner;
public interface AbstractInput {
    void printOnThatLine(Object o);
    void printOnNextLine(Object o);
    String readln();
    void errorMessage(Object o);
    void scriptMod(Scanner scanner);
}
