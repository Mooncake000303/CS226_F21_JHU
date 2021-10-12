# Discussion

**Document all error conditions you determined and why they are error
 conditions. Do this by including the inputs that you used to test your
  program and what error conditions they exposed:**

1. Error condition: Invalid token

When tokens other than valid ones (`? . ! + - * / % <Integer>`)
are put in, such as `1.0`(doubles), `blah`(words), `.@.!?`(nonsense characters), etc,
the program will skip the invalid tokens with an `ERROR CODE: bad token`.
The program will continue to read in other valid tokens.

Test example

Input 

`1 2 blah 1.0 3 ?`

Output
```
ERROR CODE: bad token
ERROR CODE: bad token
[1, 2, 3]
```


2. Error condition: Empty stack

When the special command `.` is called while the stack is empty, EmptyException
would be thrown, but in this case, to avoid termination with exception,
it is caught and handled with an`ERROR CODE: The stack contains less operand than needed.`
because `.` needs 1 operand but there's only 0.
The program will continue to read in other valid tokens.

Test example

Input

`. ?`

Output
```
ERROR CODE: The stack contains less operand than needed.
[]
```

3. Error condition: Less than (<) 2 operands for `+ - * / %`

When the one of the binary operator `+ - * / %` is called 
but there is less than (<) 2 operands left on the stack, 
EmptyException would be  thrown potentially during the process.
But in this case, to avoid termination with exception,
it is caught and handled with an`ERROR CODE: The stack contains less operand than needed.`
because binary operator (`+ - * / %`) needs 2 operands but there's less than (<) 2.
The stack will be kept the same as before the operator input, and no actual operations are done.
The program will continue to read in other valid tokens.

Test example

Input

`1 3 + ? 5 7 - ? * ? + ? `

Output
```
[4]
[4, -2]
[-8]
ERROR CODE: The stack contains less operand than needed.
[-8]
```

4. Error condition: `/` division by zero

When a `/` is called with sufficient (2) operands, there would be a case that the
second operand（divisor）is 0，and division by zero will throw an ArithmeticException.
But in this case, to avoid termination with exception, instead of catching ArithmeticException,
division by zero will be detected before the division actually happens, and handled with 
`ERROR CODE: cannot divide by zero`. The stack will be kept the same as before the `/` input, 
and no actual operations are done.
The program will continue to read in other valid tokens.

Test example

Input

`24 3 + ? 5 0 / ? + ? - ? !`

Output
```
[27]
ERROR CODE: cannot divide by zero
[27, 5, 0]
[27, 5]
[22]
```

5. Error condition: `%` division by zero

When a `%` is called with sufficient (2) operands, there would be a case that the
second operand（divisor）is 0，and division by zero will throw an ArithmeticException.
But in this case, to avoid termination with exception, instead of catching ArithmeticException,
division by zero will be detected before the division actually happens, and handled with
`ERROR CODE: cannot divide by zero`. The stack will be kept the same as before the `%` input,
and no actual operations are done.
The program will continue to read in other valid tokens.

Test example

Input

`3 42 * ? 0 % ? 5 + - ? !`

Output
```
[126]
ERROR CODE: cannot divide by zero
[126, 0]
[121]
```

