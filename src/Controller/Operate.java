package Controller;

import sun.util.resources.th.CalendarData_th;

import javax.xml.transform.Result;
import java.util.ArrayList;

public class Operate {

    public String multiply(String firstNumber, String secondNumber)
    {

        String result = "",finalAnswer = "";
        ArrayList<Integer> indexStorage = new ArrayList<>();
        indexStorage.add(-1);

        try {


            int remained = 0;
            for (int i = firstNumber.length() - 1; i >= 0; i--) {
                if (i != firstNumber.length() - 1) {
                    indexStorage.add(result.length());
                    result += "!"; //my sepration
                }

                for (int j = 0; j < firstNumber.length() - i - 1; j++)
                    result += "0";
                //added the zeros

                for (int j = secondNumber.length() - 1; j >= 0; j--) {
                    int currentMultiply = ((firstNumber.charAt(i) - 48) * (secondNumber.charAt(j) - 48));
                    currentMultiply += remained;
                    remained = currentMultiply / 10;
                    result += currentMultiply % 10;
                    if (j == 0 && remained != 0) {
                        result += remained;
                        remained = 0;
                    }
                }
            }//end for


            int storageSize = indexStorage.size();
            int sumColumn = 0;
            remained = 0;
            int storageIndexTaker = 0;
            int maxLengthForRow = result.length() - 1 - indexStorage.get(storageSize - 1);

            for (int i = 1; i <= maxLengthForRow; i++) {
                sumColumn = 0;
                storageIndexTaker = 0;

                for (int j = 0; j < storageSize; j++) {
                    int digitAddress = indexStorage.get(storageIndexTaker++);
                    int maxIndexCanGo = j == storageSize - 1 ? result.length() : indexStorage.get(j + 1);

                    if ((digitAddress + i) < maxIndexCanGo)
                        sumColumn += (result.charAt(digitAddress + i) - 48);

                }
                //here i got the sum of one column
                //not just store them
                sumColumn += remained;
                remained = sumColumn / 10;
                finalAnswer = (sumColumn % 10) + finalAnswer;

                if (i == maxLengthForRow && remained != 0)
                    finalAnswer = remained + finalAnswer;

            }

            return finalAnswer;
        }
        catch (Exception e)
        {
            System.out.println(e);
            return "";
        }
    }//end multiply



    public String sum(String firstNumber, String secondNumber)
    {

        try {
            String result = "";
            int remained = 0;

            int a = firstNumber.length();
            int b = secondNumber.length();

            if(a < b)
                for(int i=0;i<b-a;i++)
                    firstNumber = "0" + firstNumber;
            else
                for(int i=0;i<a-b;i++)
                    secondNumber = "0" + secondNumber;

            a = firstNumber.length();

            //lengths are equal
            remained = 0;
            result = "";

            for(int i=a-1;i>=0;i--)
            {
                int currentSum = firstNumber.charAt(i) + secondNumber.charAt(i) - 96 + remained;
                remained = currentSum / 10;
                result = (currentSum%10) + result;
            }//end for

            if(remained>0)
                result = remained + result;

            return result;
        }
        catch (Exception e)
        {
            System.out.println(e);
            return "";
        }

    }//end sum




    public String division (String firstNumber, String secondNumber){
        String answer="";
        String help="";
        boolean firstNumberPositivity=true;//true means num is positive and false means it is negative
        boolean secondNumberPositivity=true;//true means num is positive and false means it is negative
        int counter=0;
        int firstNumberLength;
        int secondNumberLength;
        ArrayList<Integer> resultNumberDigits =new ArrayList <Integer> ();
        ////////////////////////////////////////////////////////////
        if (firstNumber.charAt(0)=='-'){
            firstNumberPositivity=false;
            firstNumber = firstNumber.substring(1);
            firstNumberLength=firstNumber.length();

        }
        else{
            firstNumberPositivity=true;
            firstNumberLength=firstNumber.length();
        }
        //////////////////////////////////////////////////////////////
        if (secondNumber.charAt(0)=='-'){
            secondNumberPositivity=false;
            secondNumber = secondNumber.substring(1);
            secondNumberLength=secondNumber.length();

        }
        else{
            secondNumberPositivity=true;
            secondNumberLength=secondNumber.length();

        }

        if(firstNumber.equals(secondNumber))
            answer="1";

        else if(firstNumberLength<secondNumberLength){
            answer="0";
        }
        else{

            for(int i=0 ; i<firstNumberLength; i++){
                help=help+firstNumber.charAt(i);
                if(substraction(help, secondNumber).charAt(0)=='-'){
                    resultNumberDigits.add(0);


                }//end if
                else if(substraction(help, secondNumber).equals("0")){
                    resultNumberDigits.add(1);
                    help="";
                }// end else if
                else{
                    while(isFirstNumberBigger(help, secondNumber)){
                        help=substraction(help, secondNumber);
                        counter++;

                    }//end while
                    if(help.equals("0"))
                        help="";
                    resultNumberDigits.add(counter);
                    counter=0;

                }
            }//end for
            for(int i =0; i<resultNumberDigits.size() ; i++){
                answer+=resultNumberDigits.get(i);
            }
        }//end main else
        if(! answer.equals("0")){
            while(answer.charAt(0)=='0'){
                answer = answer.substring(1);
            }
        }
        if( (firstNumberPositivity==true&&secondNumberPositivity==false) || (firstNumberPositivity==false&&secondNumberPositivity==true))
            answer = "-"+answer;


        return answer;
    }



    public String substraction(String firstNumber , String secondNumber)
    {

        /////////////////////.... my variables....////////////////////////

        String answer="";
        String temp=""; //using this for exchanging first num and second num in case we need
        boolean firstNumberPositivity=true;//true means num is positive and false means it is negative
        boolean secondNumberPositivity=true;//true means num is positive and false means it is negative
        boolean isFirstNumberBigger=true;
        int firstNumberLength;
        int secondNumberLength;
        int biggerLength;
        int MT;
        ArrayList <Integer> firstNumberDigits =new ArrayList <Integer> ();
        ArrayList <Integer> secondNumberDigits =new ArrayList <Integer> ();
        ArrayList <Integer> resultNumber = new ArrayList <Integer> ();


        ////////////////////////////////////////////////////////////
        if (firstNumber.charAt(0)=='-'){
            firstNumberPositivity=false;
            firstNumber = firstNumber.substring(1);
            firstNumberLength=firstNumber.length();

        }
        else{
            firstNumberPositivity=true;
            firstNumberLength=firstNumber.length();
        }
        //////////////////////////////////////////////////////////////
        if (secondNumber.charAt(0)=='-'){
            secondNumberPositivity=false;

            secondNumber = secondNumber.substring(1);
            secondNumberLength=secondNumber.length();

        }
        else{
            secondNumberPositivity=true;
            secondNumberLength=secondNumber.length();

        }
        /////////////////////////finding bigger length//////////////////////////////////

        if (firstNumberLength>secondNumberLength)
            biggerLength=firstNumberLength;
        else
            biggerLength=secondNumberLength;

        //is absolute value of first num bigger than absolute value of second num?! returns a boolean
        if(firstNumberLength>secondNumberLength)
            isFirstNumberBigger=true;
        else if(secondNumberLength>firstNumberLength)
            isFirstNumberBigger=false;
        else if(firstNumberLength==secondNumberLength){
            for (int i = 0; i < firstNumberLength; i++) {
                if(Integer.parseInt(String.valueOf(firstNumber.charAt(i)))>Integer.parseInt(String.valueOf(secondNumber.charAt(i))))
                {
                    isFirstNumberBigger=true;
                    break;
                }
                else if(Integer.parseInt(String.valueOf(firstNumber.charAt(i)))<Integer.parseInt(String.valueOf(secondNumber.charAt(i))))
                {
                    isFirstNumberBigger=false;
                    break;
                }

            }

        }


        ///////checking if second num is bigger so we can exchange values
        if(isFirstNumberBigger==false){
            temp=firstNumber;
            firstNumber=secondNumber;
            secondNumber=temp;
            MT=firstNumberLength;
            firstNumberLength=secondNumberLength;
            secondNumberLength=MT;

        }

        //////////////////assigning first and second number in ArrayLists//////////////////

        if (firstNumberLength==biggerLength){
            for(int i=biggerLength-1 ; i>=0 ; i--){
                firstNumberDigits.add(Integer.parseInt(String.valueOf(firstNumber.charAt(i))));
            }
        }
        else{
            for(int i =firstNumberLength-1; i>=0; i-- ){
                firstNumberDigits.add(Integer.parseInt(String.valueOf(firstNumber.charAt(i))));
            }
            for(int i=biggerLength-firstNumberLength-1; i>=0 ; i--){
                firstNumberDigits.add(0);
            }

        }

        ///////////second num

        if (secondNumberLength==biggerLength){
            for(int i=biggerLength-1 ; i>=0 ; i--){
                secondNumberDigits.add(Integer.parseInt(String.valueOf(secondNumber.charAt(i))));
            }
        }
        else{
            for(int i =secondNumberLength-1; i>=0; i-- ){
                secondNumberDigits.add(Integer.parseInt(String.valueOf(secondNumber.charAt(i))));
            }
            for(int i=biggerLength-secondNumberLength-1; i>=0 ; i--){
                secondNumberDigits.add(0);
            }

        }


        /////////////////////////substraction/////////////////////////////////////////////


        if(firstNumber.equals(secondNumber)){
            answer="0";
        }

        else{
            for (int i = 0; i < biggerLength; i++) {
                if(firstNumberDigits.get(i) >= secondNumberDigits.get(i)){
                    resultNumber.add(firstNumberDigits.get(i)-secondNumberDigits.get(i));

                }
                else{
                    resultNumber.add(firstNumberDigits.get(i)+10 - secondNumberDigits.get(i));
                    i++;
                    while(firstNumberDigits.get(i)==0){
                        firstNumberDigits.set(i , 9);
                        resultNumber.add(firstNumberDigits.get(i)-secondNumberDigits.get(i));
                        i++;
                    }
                    firstNumberDigits.set(i , firstNumberDigits.get(i) - 1);
                    i--;
                    continue;


                }

            }

            for(int i=resultNumber.size()-1 ; i>=0 ; i--){
                answer+=String.valueOf(resultNumber.get(i));
            }

            while(answer.charAt(0)=='0'){
                answer = answer.substring(1);
            }

            if(  ((isFirstNumberBigger==false) &&(firstNumberPositivity==true) &&(secondNumberPositivity==true))
                    || ((isFirstNumberBigger==true) &&(firstNumberPositivity==false) &&(secondNumberPositivity==false)) ){
                answer="-"+answer;
            }
            // obirsi halatlarda jam da duzalir olari bunun idamasinda yaz
            //istasan de halatlari yollum bilava
        }
        return answer;


    }


    private boolean isFirstNumberBigger(String firstNumber,String secondNumber){
        boolean isFirstNumberBigger=true;
        if(firstNumber.length()>secondNumber.length())
            isFirstNumberBigger=true;
        else if(secondNumber.length()>firstNumber.length())
            isFirstNumberBigger=false;
        else if(firstNumber.length()==secondNumber.length()){
            for (int i = 0; i < firstNumber.length(); i++) {
                if(Integer.parseInt(String.valueOf(firstNumber.charAt(i)))>Integer.parseInt(String.valueOf(secondNumber.charAt(i))))
                {
                    isFirstNumberBigger=true;
                    break;
                }
                else if(Integer.parseInt(String.valueOf(firstNumber.charAt(i)))<Integer.parseInt(String.valueOf(secondNumber.charAt(i))))
                {
                    isFirstNumberBigger=false;
                    break;
                }

            }

        }
        return isFirstNumberBigger;
    }


}//end TestClass








