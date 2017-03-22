package com.company;

/**
 * Created by APEPTAN on 3/22/2017.
 *
 * Allows computation of any math binary operations with integers
 * by passing to operateBinary method the lambda expression describing it
 */
public class Calculator {

    interface IntegerMath {
        int operation(int a, int b);
    }

    public int operateBinary(int a, int b, IntegerMath op){
        return op.operation(a, b);
    }
}
