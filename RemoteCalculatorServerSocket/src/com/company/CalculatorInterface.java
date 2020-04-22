package com.company;

// Interface usada para comunicação remota
public interface CalculatorInterface {
    double calc(int operation, double oper1, double oper2) throws IllegalArgumentException;
}
