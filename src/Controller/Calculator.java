package Controller;

import jdk.nashorn.internal.ir.ReturnNode;
import jdk.nashorn.internal.runtime.OptimisticReturnFilters;

import javax.management.OperationsException;
import javax.management.PersistentMBean;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import java.sql.ResultSet;

public class Calculator {


    Operate operate = new Operate();
    String part[] = new String[200];
    String mixingAddress[] = new String[100];
    char[] operator = new char[100];// for now i defined as limited arrays but later do infinity
    int partAdd = 1, operatorAdd = 1, lastRemainedPart = 0, mixingAddressAdd = 1;
    String inputChanged = "", result = "", lastInput = "";
    boolean mode2nd = false;

    // varibales i used in splitToPartsAndSave
    char currentOperator;
    int operatorAddress;


    public String calculate(String input)
    {
        try
        {
            if(!endsWithOperator(input) && braketsEven(input) && isThereAnyOperator(input))
                return splitToPartsAndSave(input);
            else
                return result;
        }
        catch (Exception e)
        {
            return "Sorry there was a problem :(\nIf your phrase is right, this might have been a bug in PaPa.\n Tell us this problem and we'll handle it";
        }
    }


    private String splitToPartsAndSave(String input)
    {
        /*
         * String input is the expression char operator is the operation between these
         * two parts that we split int adress is the part that we are going to split
         * from
         */

        lastRemainedPart = 0;
        part[lastRemainedPart] = input;
        String part1 = "", part2 = "";

        while (lastRemainedPart < partAdd + 1) {
            if (isThereAnyOperator(part[lastRemainedPart])) {
                operatorDeclare(part[lastRemainedPart]);
                input = inputChanged;// the changes may have occurred in operatorDeclare

                // define the values of variables
                part1 = "";
                part2 = "";

                for (int i = 0; i < operatorAddress; i++)
                    part1 += input.charAt(i);

                for (int i = operatorAddress + 1; i < input.length(); i++)
                    part2 += input.charAt(i);



                part[partAdd] = part1;
                part[partAdd + 1] = part2;
                this.operator[operatorAdd] = currentOperator;
                mixingAddress[mixingAddressAdd] = (partAdd + 1) + "&" + partAdd + "&" + lastRemainedPart;

                lastRemainedPart++;

                while (lastRemainedPart <= partAdd + 1 && !isThereAnyOperator(part[lastRemainedPart])) {
                    if (lastRemainedPart <= partAdd)
                        lastRemainedPart++;
                    else
                        break;
                }

                if (isThereAnyOperator(part[lastRemainedPart])) {
                    partAdd += 2;
                    operatorAdd++;
                    mixingAddressAdd++;
                }
            } else {
                lastRemainedPart++;
            }
        }//end while


        declarValuesOfParts();// for example turns sin(30) to .5
        calculateParts();

        partAdd = 1;
        operatorAdd = 1;
        lastRemainedPart = 0;
        mixingAddressAdd = 1;

        result = part[0];
        return result;// for real time answer

    }// end splitToPartsAndSave


    private void calculateParts()
    {
        String[] addressParts;// firstPart is the first passage, second is the second passage, third is the
        // target
        double firstPart, secondPart;

        for (int i = mixingAddressAdd; i >= 1; i--) {
            addressParts = mixingAddress[i].split("&");

            // but my parts may start and end with Brackets so i do below thing
            if (part[Integer.parseInt(addressParts[0])].startsWith("(")
                    && part[Integer.parseInt(addressParts[0])].endsWith(")")) {
                part[Integer.parseInt(addressParts[0])] = takeOutFromBraket(part[Integer.parseInt(addressParts[0])]);
            }
            if (part[Integer.parseInt(addressParts[1])].startsWith("(")
                    && part[Integer.parseInt(addressParts[1])].endsWith(")")) {
                part[Integer.parseInt(addressParts[1])] = takeOutFromBraket(part[Integer.parseInt(addressParts[1])]);
            }

            firstPart = Double.parseDouble(part[Integer.parseInt(addressParts[0])]); // this is one of parts
            secondPart = Double.parseDouble(part[Integer.parseInt(addressParts[1])]); // this is the other part
            // part[Integer.parseInt(addressParts[2])] this one is the targeted one

            String partone = String.valueOf((int)firstPart);
            String parttwo = String.valueOf((int)secondPart);
            //here i change the operators to ours :DDDDD
            switch (operator[i]) {
                case '+':
                    part[Integer.parseInt(addressParts[2])] = operate.sum(partone, parttwo);
                    break;
                case '-':
                    part[Integer.parseInt(addressParts[2])] = operate.substraction(parttwo, partone);
                    break;
                case '*':
                    part[Integer.parseInt(addressParts[2])] = operate.multiply(partone, parttwo);
                    break;
                case '/':
                    part[Integer.parseInt(addressParts[2])] = operate.division(parttwo, partone);
                    break;
                case '^':
                    part[Integer.parseInt(addressParts[2])] = String.valueOf(Math.pow(secondPart, firstPart));
                    break;
                default:
                    break;
            }
        }
    }//end calculateParts


    private void operatorDeclare(String input)
    {
        /*
         * this function sets the currentOperator value and operatorAddress value and
         * also takes the passage out from brakets if it is inside them
         */

        int[] braketStart = new int[100];
        int[] braketEnd = new int[100];
        int lastStartBraket = 0;
        boolean braketCleaned = false;
        char[] inputChar = input.toCharArray();

        for (int i = 0; i < input.length(); i++) {
            if (inputChar[i] == '(') {
                braketStart[lastStartBraket] = i;
                lastStartBraket++;
            } else if (inputChar[i] == ')') {
                if (braketEnd[lastStartBraket - 1] == 0) {
                    braketEnd[lastStartBraket - 1] = i;
                } else {
                    int lastStartBraketPassing = lastStartBraket - 1;

                    while (braketEnd[lastStartBraketPassing] != 0) {
                        lastStartBraketPassing--;
                    }

                    braketEnd[lastStartBraketPassing] = i;
                }
            }

        } // after this loop i have the adresses of Brakets

        lastStartBraket--;// because after this loop it is always +1

        if (braketStart[0] == 0 && braketEnd[0] == input.length() - 1)// if this happens it means that input starts and
        // ends with brakets
        {
            input = takeOutFromBraket(input);
            braketCleaned = true;
            inputChar = input.toCharArray();
            for (int i = 0; i <= lastStartBraket; i++) {
                braketStart[i] -= 1;
                braketEnd[i] -= 1;
            }
        }

        int jStartPoint;// this is the counter of testing that if operator is between brackets or not
        boolean isBetweenBrakets = false;

        if (braketCleaned == true)
            jStartPoint = 1;
        else
            jStartPoint = 0;
        for (int i = 0; i < input.length(); i++) {
            if (inputChar[i] == '+') {
                if (input.contains("(")) {
                    for (int j = jStartPoint; j <= lastStartBraket; j++) {
                        if (i > braketStart[j] && i < braketEnd[j]) {
                            isBetweenBrakets = true;
                        }
                    }
                    if (isBetweenBrakets == false) {
                        currentOperator = '+';
                        operatorAddress = i;
                        inputChanged = input;
                        return;
                    }
                } else// so there is no brackets around passage
                {
                    for (int k = 0; k < input.length(); k++) {
                        if (inputChar[k] == '+') {
                            currentOperator = '+';
                            operatorAddress = i;
                            inputChanged = input;
                            return;
                        }
                    }
                }
            }
            isBetweenBrakets = false;
        } // end plus

        if (braketCleaned == true)
            jStartPoint = 1;
        else
            jStartPoint = 0;
        for (int i = 0; i < input.length(); i++) {
            if (inputChar[i] == '-') {
                if (input.contains("(")) {
                    for (int j = jStartPoint; j <= lastStartBraket; j++) {
                        if (i > braketStart[j] && i < braketEnd[j]) {
                            isBetweenBrakets = true;
                        }
                    }
                    if (isBetweenBrakets == false) {
                        currentOperator = '-';
                        operatorAddress = i;
                        inputChanged = input;
                        return;
                    }
                } else// so there is no brackets around passage
                {
                    for (int k = 0; k < input.length(); k++) {
                        if (inputChar[k] == '-') {
                            currentOperator = '-';
                            operatorAddress = i;
                            inputChanged = input;
                            return;
                        }
                    }
                }
            }
            isBetweenBrakets = false;
        } // end minus

        if (braketCleaned == true)
            jStartPoint = 1;
        else
            jStartPoint = 0;
        for (int i = 0; i < input.length(); i++) {
            if (inputChar[i] == '*') {
                if (input.contains("(")) {
                    for (int j = jStartPoint; j <= lastStartBraket; j++) {
                        if (i > braketStart[j] && i < braketEnd[j]) {
                            isBetweenBrakets = true;
                        }
                    }
                    if (isBetweenBrakets == false) {
                        currentOperator = '*';
                        operatorAddress = i;
                        inputChanged = input;
                        return;
                    }
                } else// so there is no brackets around passage
                {
                    for (int k = 0; k < input.length(); k++) {
                        if (inputChar[k] == '*') {
                            currentOperator = '*';
                            operatorAddress = i;
                            inputChanged = input;
                            return;
                        }
                    }
                }
            }
            isBetweenBrakets = false;
        } // end zarb

        if (braketCleaned == true)
            jStartPoint = 1;
        else
            jStartPoint = 0;
        for (int i = 0; i < input.length(); i++) {
            if (inputChar[i] == '/') {
                if (input.contains("(")) {
                    for (int j = jStartPoint; j <= lastStartBraket; j++) {
                        if (i > braketStart[j] && i < braketEnd[j]) {
                            isBetweenBrakets = true;
                        }
                    }
                    if (isBetweenBrakets == false) {
                        currentOperator = '/';
                        operatorAddress = i;
                        inputChanged = input;
                        return;
                    }
                } else// so there is no brackets around passage
                {
                    for (int k = 0; k < input.length(); k++) {
                        if (inputChar[k] == '/') {
                            currentOperator = '/';
                            operatorAddress = i;
                            inputChanged = input;
                            return;
                        }
                    }
                }
            }
            isBetweenBrakets = false;
        } // end div

        if (braketCleaned == true)
            jStartPoint = 1;
        else
            jStartPoint = 0;
        for (int i = 0; i < input.length(); i++) {
            if (inputChar[i] == 'M') {
                if (input.contains("(")) {
                    for (int j = jStartPoint; j <= lastStartBraket; j++) {
                        if (i > braketStart[j] && i < braketEnd[j]) {
                            isBetweenBrakets = true;
                        }
                    }
                    if (isBetweenBrakets == false) {
                        currentOperator = 'M';
                        operatorAddress = i;
                        inputChanged = input;
                        return;
                    }
                } else// so there is no brackets around passage
                {
                    for (int k = 0; k < input.length(); k++) {
                        if (inputChar[k] == 'M') {
                            currentOperator = 'M';
                            operatorAddress = i;
                            inputChanged = input;
                            return;
                        }
                    }
                }
            }
            isBetweenBrakets = false;
        } // end Mod

        if (braketCleaned == true)
            jStartPoint = 1;
        else
            jStartPoint = 0;
        for (int i = 0; i < input.length(); i++) {
            if (inputChar[i] == '^') {
                if (lastStartBraket > 0) {
                    for (int j = jStartPoint; j <= lastStartBraket; j++) {
                        if (i > braketStart[j] && i < braketEnd[j]) {
                            isBetweenBrakets = true;
                        }
                    }
                    if (isBetweenBrakets == false) {
                        currentOperator = '^';
                        operatorAddress = i;
                        inputChanged = input;
                        return;
                    }
                } else// so there is no brackets around passage
                {
                    for (int k = 0; k < input.length(); k++) {
                        if (inputChar[k] == '^') {
                            currentOperator = '^';
                            operatorAddress = i;
                            inputChanged = input;
                            return;
                        }
                    }
                }
            }
            isBetweenBrakets = false;
        } // end Power

    }// end operatorDeclare


    private void declarValuesOfParts()
    {
        double value = 0;

        for (int i = partAdd + 1; i >= 1; i--) {
            if (!isInteger(part[i])) {
                if (part[i].contains("asin") && !isThereAnyOperator(part[i])) {
                    value = Double.parseDouble(part[i].replaceAll("[\\D]", ""));
                    part[i] = String.valueOf(Math.asin(Math.toRadians(value)));
                } // end arcsin
                else if (part[i].contains("acos") && !isThereAnyOperator(part[i])) {
                    value = Double.parseDouble(part[i].replaceAll("[\\D]", ""));
                    part[i] = String.valueOf(Math.acos(Math.toRadians(value)));
                } // end arccos
                else if (part[i].contains("atan") && !isThereAnyOperator(part[i])) {
                    value = Double.parseDouble(part[i].replaceAll("[\\D]", ""));
                    part[i] = String.valueOf(Math.atan(Math.toRadians(value)));
                } // end arctan
                else if (part[i].contains("acot") && !isThereAnyOperator(part[i])) {
                    value = Double.parseDouble(part[i].replaceAll("[\\D]", ""));
                    part[i] = String.valueOf((1 / Math.atan(Math.toRadians(value))));
                } // end arccot
                else if (part[i].contains("Sin") && !isThereAnyOperator(part[i])) {
                    value = Double.parseDouble(part[i].replaceAll("[\\D]", ""));
                    part[i] = String.valueOf(Math.sin(Math.toRadians(value)));
                } // end sin
                else if (part[i].contains("cos") && !isThereAnyOperator(part[i])) {
                    value = Double.parseDouble(part[i].replaceAll("[\\D]", ""));
                    part[i] = String.valueOf(Math.cos(Math.toRadians(value)));
                } // end cos
                else if (part[i].contains("tan") && !isThereAnyOperator(part[i])) {
                    value = Double.parseDouble(part[i].replaceAll("[\\D]", ""));
                    part[i] = String.valueOf(Math.tan(Math.toRadians(value)));
                } // end tan
                else if (part[i].contains("cot") && !isThereAnyOperator(part[i])) {
                    value = Double.parseDouble(part[i].replaceAll("[\\D]", ""));
                    part[i] = String.valueOf((1 / Math.tan(Math.toRadians(value))));
                } // end cot
                else if (part[i].contains("%") && !isThereAnyOperator(part[i])) {
                    value = Double.parseDouble(part[i].replaceAll("[\\D]", ""));
                    part[i] = String.valueOf(value / 100);
                } // end %
                else if (part[i].contains("sqrt") && !isThereAnyOperator(part[i])) {
                    value = Double.parseDouble(part[i].replaceAll("[\\D]", ""));
                    part[i] = String.valueOf(Math.sqrt(value));
                } // end sqrt
                else if (part[i].contains("log") && !isThereAnyOperator(part[i])) {
                    value = Double.parseDouble(part[i].replaceAll("[\\D]", ""));
                    part[i] = String.valueOf(Math.log10(value));
                } // end log10
                else if (part[i].contains("ln") && !isThereAnyOperator(part[i])) {
                    value = Double.parseDouble(part[i].replaceAll("[\\D]", ""));
                    part[i] = String.valueOf(Math.log(value));
                } // end ln
                else if (part[i].contains("neg") && !isThereAnyOperator(part[i])) {
                    value = Double.parseDouble(part[i].replaceAll("[\\D]", ""));
                    part[i] = String.valueOf(value * (-1));
                } // end negative
                else if (part[i].contains("!") && !isThereAnyOperator(part[i])) {
                    value = Double.parseDouble(part[i].replaceAll("[\\D]", ""));
                    part[i] = calculateFactorial((int) value);
                }// end factorial
            }
        }
    }// end declarValuesOfParts


    private boolean isInteger(String input)
    {
        try
        {
            Integer.parseInt(input);
        }
        catch (NumberFormatException e)
        {
            return false;
        }
        catch (NullPointerException e)
        {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }


    private String calculateFactorial(int number)
    {
        int result = 1;
        for(int i=number;i>=2;i--)
            result *= i;

        return result + "";
    }


    private String takeOutFromBraket(String input)
    {
        // subString input
        input = input.substring(1);
        input = input.substring(0, input.length() - 1);
        return input;
    }


    public String btnClearToFirstOperatorPressed(String current)
    {

        for (int i = current.length() - 1; i >= 0; i--) {

            if (current.charAt(i) == '+' || current.charAt(i) == '-' || current.charAt(i) == '*' || current.charAt(i) == '/' || current.charAt(i) == 'd')
            {
                current = current.substring(0, i+1);
                break;
            }
        }//end for

        return current;

    }//end btnClearToFirstOperatorPressed

    private boolean endsWithOperator(String input)
    {
        boolean result = false;
        if(input.endsWith("+") || input.endsWith("-") || input.endsWith("/") || input.endsWith("*") || input.endsWith("^"))
            result = true;

        return result;
    }


    private boolean braketsEven(String input)
    {
        int openBraketCount = 0, closeBraketCount = 0;

        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '(')
                openBraketCount++;
            else if (input.charAt(i) == ')')
                closeBraketCount++;
        }//end for

        if (openBraketCount == closeBraketCount)
            return true;
        else
            return false;

    }// end braketEven


    private boolean isThereAnyOperator(String input)
    {
        if (input.contains("+") || input.contains("-") || input.contains("*") || input.contains("/") || input.contains("^") || input.contains("M")) {
            return true;
        } else {
            return false;
        }
    }//end isThereAnyOperator




}//end class
