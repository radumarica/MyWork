import java.util.Stack;
import java.util.HashMap;

public final class LogicalTricks{

    public static boolean evaluateBool(String s)
    {
        Stack<Object> stack = new Stack<>();
        StringBuilder expression =new StringBuilder(s);
        expression.chars().forEach(ch->
        {
            if(ch=='0') stack.push(false);
            else if(ch=='1') stack.push(true);
            else if(ch=='|'||ch=='&')
            {
                boolean op1 = (boolean) stack.pop();
                boolean op2 = (boolean) stack.pop();
                switch(ch)
                {
                    case '&' : stack.push(op2&&op1); break;
                    case '|' : stack.push(op2||op1); break;
                }//endSwitch
            }else
            if(ch=='!')
            {
                boolean op1 = (boolean) stack.pop();
                stack.push(!op1);
            }//endIF
        });
        return (boolean) stack.pop();
    }

    public static String infixToPostfix(String s){
        String postfix = "";
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i<s.length(); i++){
            char token = s.charAt(i);
            if (token == '1' || token == '0'){
                postfix+=s.charAt(i);
            }
            else if (token == '('){
                stack.push(token);
            }
            else if (token == ')'){
                while (!stack.empty() && stack.peek() != '(') {
                    postfix += stack.pop();
                }
                if (stack.peek() == '(') stack.pop();
            }
            else if (token == '!' || token == '|' || token == '&'){
                if (stack.empty() || stack.peek() == '('){
                    stack.push(token);
                }
                else {
                    while (!stack.empty() && stack.peek() != '(' && getPrecedence(token) <=
                            getPrecedence(stack.peek())){
                        postfix += stack.pop();
                    }
                    stack.push(token);
                }
            }
        }
        while (!stack.empty()){
            postfix += stack.pop();
        }
        return postfix;
    }

    public static int getPrecedence(char operator){
        if (operator == '!') return 3;
        else return 2;
    }

    public static boolean evalFormula(String formula, HashMap<Character, Integer> interpretation){
        String instantFormula = "";
        if (formula.equals("TRUE")) return true;
        else {
            for (int i = 0; i<formula.length(); i++){
                char token = formula.charAt(i);
                if (Character.isLetter(token)) instantFormula += interpretation.get(token);
                else instantFormula += token;
            }
        }
        System.out.println(instantFormula);
        return evaluateBool(infixToPostfix(instantFormula));
    }

}