package com.company;

// Interface utilizada para o RMI
// Deve estar em um pacote com o mesmo nome que está no servidor remoto
public interface CalculatorInterface {
    double calc(int operation, double oper1, double oper2) throws IllegalArgumentException;
}
