package com.company;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BinaryExpressionTree implements Serializable {
    private char operator;
    private double value;
    private BinaryExpressionTree left;
    private BinaryExpressionTree right;

    public double evaluate() {
        return switch (this.operator) {
            case '+' -> left.evaluate() + right.evaluate();
            case '-' -> left.evaluate() - right.evaluate();
            case '*' -> left.evaluate() * right.evaluate();
            case '/' -> left.evaluate() / right.evaluate();
            default -> value;
        };
    }

    @XmlElement
    public char getOperator() {
        return operator;
    }

    public void setOperator(char operator) {
        this.operator = operator;
    }

    @XmlElement
    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @XmlElement
    public BinaryExpressionTree getLeft() {
        return left;
    }

    public void setLeft(BinaryExpressionTree left) {
        this.left = left;
    }

    @XmlElement
    public BinaryExpressionTree getRight() {
        return right;
    }

    public void setRight(BinaryExpressionTree right) {
        this.right = right;
    }

    @Override
    public String toString() {
        if (operator == 0) {
            return String.valueOf(value);
        } else {
            return "(" + left + operator + right + ")";
        }
    }
}
