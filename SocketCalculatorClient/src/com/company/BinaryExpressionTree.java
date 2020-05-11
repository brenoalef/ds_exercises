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

    public BinaryExpressionTree() {}

    public BinaryExpressionTree(double value) {
        this.value = value;
    }

    public BinaryExpressionTree(char operator, BinaryExpressionTree left, BinaryExpressionTree right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
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
