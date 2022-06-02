import java.util.ArrayList;

public class CalculateFactory {

    public CalculateFactory() {}

    public static String calculateOperation(String operationText) {
        String[] operators = {"+", "-", "/", "*"};
        String[] arrayNumbers = operationText.split("[+-/*]");
        ArrayList<Double> numbers = new ArrayList<>();
        String[] arrayExpression = operationText.split("");
        ArrayList<String> arrayOperators = new ArrayList<>();

        for (String s : arrayExpression) {
            for (String operator : operators) {
                if (s.equals(operator)) {
                    arrayOperators.add(s);
                }
            }
        }

        if (arrayOperators.size() == 0) {
            arrayOperators.add("000");
        }
        // Test valid expression
        if (arrayOperators.size()-arrayNumbers.length > 0 ||
                arrayExpression[arrayExpression.length-1].equals(arrayOperators.get(arrayOperators.size()-1))) {

            return "Invalid operation!";
        }

        // convert numbers to int
        for (String numb: arrayNumbers) {
            if (numb.equals("")){
                numbers.add(0.0);
            } else {
                numbers.add((double) Integer.parseInt(numb));
            }
        }

        int nOp = 0;
        // first priority
        for (int i = 0; i < arrayOperators.size(); i++) {
            Double opNumber1;
            Double opNumber2;
            double result;

            if (arrayOperators.get(i).equals("*")) {
                System.out.println(numbers);
                System.out.println(arrayOperators);
                opNumber1 = numbers.get(i-nOp);
                opNumber2 = numbers.get(i+1-nOp);
                result = opNumber1 * opNumber2;
            } else if (arrayOperators.get(i).equals("/")) {
                opNumber1 = numbers.get(i-nOp);
                opNumber2 = numbers.get(i+1-nOp);
                result = opNumber1 / opNumber2;
            } else {
                continue;
            }
            numbers.remove(opNumber1);
            numbers.remove(opNumber2);
            numbers.add(i-nOp, result);
            nOp++;
        }

        ArrayList<String> arraySecondPriorityOperators = new ArrayList<>();
        for (String op : arrayOperators) {
            if (!op.equals("*") && !op.equals("/")) {
                arraySecondPriorityOperators.add(op);
            }
        }

        nOp = 0;
        // second priority
        for (int i = 0; i < arraySecondPriorityOperators.size(); i++) {
            Double opNumber1;
            Double opNumber2;
            double result;

            if (arraySecondPriorityOperators.get(i).equals("+")) {
                System.out.println(numbers);
                System.out.println(arraySecondPriorityOperators);
                System.out.println(numbers.get(0) + " " + numbers.get(1));
                opNumber1 = numbers.get(i-nOp);
                opNumber2 = numbers.get(i+1-nOp);
                result = opNumber1 + opNumber2;
            } else if (arraySecondPriorityOperators.get(i).equals("-")) {
                System.out.println(numbers);
                System.out.println(arraySecondPriorityOperators);
                opNumber1 = numbers.get(i-nOp);
                opNumber2 = numbers.get(i+1-nOp);
                result = opNumber1 - opNumber2;
            } else {
                continue;
            }
            numbers.remove(opNumber1);
            numbers.remove(opNumber2);
            numbers.add(i-nOp, result);
            nOp++;
        }

        return ""+numbers.get(0);
    }
}