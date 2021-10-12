package hw4;

import exceptions.EmptyException;
import java.util.Scanner;

/**
 * A program for an RPN calculator that uses a stack.
 */
public final class Calc {
  private static final String CUR = "?";
  private static final String TOP = ".";
  private static final String END = "!";

  private static final String ADD = "+";
  private static final String SUB = "-";
  private static final String MUL = "*";
  private static final String DIV = "/";
  private static final String MOD = "%";

  // Check if stack.top() will throw Empty exception (return true if not)
  private static boolean guardedTop(LinkedStack<Integer> stack) {
    boolean flag = false;
    try {
      stack.top();
      flag = true;
    } catch (EmptyException ex) {
      System.out.println("ERROR CODE: The stack contains less operand than needed."); // empty stack and < 2 operands
    }
    return flag;
  }

  // Pre: There is already valid op1 and op2. Input can only be div or mod.
  // Deal with div and mod, guarded by div-by-zero
  private static void divCheckZero(int op1, int op2, String input, LinkedStack<Integer> stack) {
    if (op2 == 0) {
      System.out.println("ERROR CODE: cannot divide by zero");
      stack.push(op1); // push back
      stack.push(op2);
    } else {
      if (input.equals(DIV)) {
        stack.push(op1 / op2);
      } else { // MOD
        stack.push(op1 % op2);
      }
    }
  }

  // Pre: There is already valid op1 and op2. Input can only be one of the + - * / %
  private static void doActualCalculation(int op1, int op2, String input, LinkedStack<Integer> stack) {
    switch (input) {
      case ADD:
        stack.push(op1 + op2);
        break;
      case SUB:
        stack.push(op1 - op2);
        break;
      case MUL:
        stack.push(op1 * op2);
        break;
      default: // handle div and mod separately because there's a need to check division by zero
        divCheckZero(op1, op2, input, stack);
    }
  }

  // check if it is one of ? . ! (return true if it is)
  private static boolean isSpecial(String input) {
    return (input.equals(CUR) || input.equals(TOP) || input.equals(END));
  }

  // Pre: Input can only be ? .
  private static void doSpecial(String input, LinkedStack<Integer> stack) {
    if (input.equals(CUR)) {
      System.out.println(stack.toString());
    } else {
      if (guardedTop(stack)) {
        System.out.println(stack.top());
      } // else: empty stack, do nothing (ERROR CODE: operand less than needed)
    }
  }

  // check if it is one of + - * / % (return true if it is)
  private static boolean isOperator(String input) {
    return (input.equals(ADD) || input.equals(SUB) || input.equals(MUL) || input.equals(DIV) || input.equals(MOD));
  }

  // Pre: Input can only be + - * / %
  private static void doOperator(String input, LinkedStack<Integer> stack) {
    if (guardedTop(stack)) {
      int op2 = stack.top();
      stack.pop();
      if (guardedTop(stack)) {
        int op1 = stack.top();
        stack.pop();
        doActualCalculation(op1, op2, input, stack);
      } else { // op2 yes, op1 no
        stack.push(op2); // push back op2 (ERROR CODE: operand less than needed)
      }
    } // op2 no, op1 no, do nothing (ERROR CODE: operand less than needed)
  }

  private static void doInteger(String input, LinkedStack<Integer> stack) {
    try {
      int num = Integer.parseInt(input);
      stack.push(num);
    } catch (NumberFormatException ex) {
      System.out.println("ERROR CODE: bad token"); // not an integer & all other invalid inputs
    }
  }

  /**
   * The main function of RPN calculator that can perform ? . ! + - * % / on integers only.
   * EmptyException, NumberFormatException are handled and ArithmeticException is avoided.
   *
   * @param args Not used.
   */
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    LinkedStack<Integer> stack = new LinkedStack<>();
    while (sc.hasNext()) { // continue reading
      String input = sc.next();
      if (isSpecial(input)) { // ? . !
        if (input.equals(END)) {
          return;
        }
        doSpecial(input, stack);
      } else if (isOperator(input)) { // + - * / %
        doOperator(input, stack);
      } else { // Integer and other invalid input
        doInteger(input, stack);
      }
    }
  }
}

