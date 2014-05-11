# DryUnit
A JUnit test runner for [parameterized tests](https://github.com/junit-team/junit/wiki/Parameterized-tests)

## What does it do exactly?
For example, to test a Fibonacci function, one usually write:

```java
public class FibonacciTest {
    
    @Test
    public void test() {
        assertEquals(0, fibonacci(0));
        assertEquals(1, fibonacci(1));
        assertEquals(1, fibonacci(2));
        assertEquals(2, fibonacci(3));
        //... 
    }
}
```

Using DryUnit, we can do better:

```java
@RunWith(DryRunner.class)
public class FibonacciTest {
    
    @Test
    @WithParams({"fibin","fibout"})
    public void test(int input, int output) {
        assertEquals(output, fibonacci(input));
    }
    
    @ParamValues("fibin")
    public static int[] fibin = {0, 1, 2, 3};
    
    @ParamValues("fibout")
    public static int[] fibout = {0, 1, 1, 2};
}
```

## Install/Download
### Relase 0.9.0
* Download ([jar file](https://github.com/nealxyc/DryUnit/releases/download/0.9.0/dryunit-0.9.0.jar)) ([source file](https://github.com/nealxyc/DryUnit/archive/0.9.0.zip))

## Build 
 
```
mvn compile test package
```

## License
The MIT License (MIT) http://opensource.org/licenses/MIT

