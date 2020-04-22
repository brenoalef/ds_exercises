package com.company;

public class CalculatorImpl implements CalculatorInterface {
    public double calc(int operation, double oper1, double oper2) throws IllegalArgumentException {
        switch (operation) {
            case 1:
                // 1 - soma
                return oper1 + oper2;
            case 2:
                // 2 - subtração
                return oper1 - oper2;
            case 3:
                // 3 - multiplicação
                return oper1 * oper2;
            case 4:
                // 4 - divisão
                return oper1 / oper2;
            default:
                throw new IllegalArgumentException("Operação inválida.");
        }
    }
}
