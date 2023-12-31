import java.util.ArrayList;

public class Stack
{
    public ArrayList<Character> stack;
    public char initialSymbol = 'Z';

    public Stack()
    {
        stack = new ArrayList<Character>();
        stack.add(initialSymbol);
    }

    public Stack(Stack oldStack)
    {
        stack = (ArrayList<Character>) oldStack.stack.clone();
        initialSymbol = oldStack.initialSymbol;
    }
    //for custom pushdown symbol
    public Stack(char initialSymbol)
    {
        stack = new ArrayList<Character>();
        stack.add(initialSymbol);
    }

    public char getTopOfStack()
    {
        //if empty stack, return lambda
        if (stack.size() == 0)
        {
            return '&';
        }
        System.out.println("Top of Stack: " + stack.get(0));
        return stack.get(0);
    }

    //push by removing the top of the stack and adding the new symbol
    public void push (ArrayList<Character> symbol)
    {
        stack.remove(0);
        stack.addAll(0,symbol);
    }

    public void pushFromLambda (ArrayList<Character> symbol)
    {
        stack.addAll(0,symbol);
    }

    public void pop()
    {
        stack.remove(0);
    }
    public boolean isEmpty()
    {
        return stack.size() == 0;
    }

    public void printStack()
    {
        if(stack.size() == 0)
            System.out.println("[]");
        else
            System.out.println(stack);
    }

    public String showStack()
    {
        String stackString = "";
        for(char c : stack)
            stackString += c;
        return stackString;
    }

}