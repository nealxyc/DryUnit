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
    
    @ParamValues("fibin") public static int[] fibin = {0, 1, 2, 3};
    
    @ParamValues("fibout") public static int[] fibout = {0, 1, 1, 2};
}
```

Here is another example for testing some math functions:
```java
@RunWith(DryRunner.class)
public class MathTest {
    
    @Test
    @WithParams({"sqIn","sqOut"})
    public void testSquare(double input, double output){
	    Assert.assertEquals(output, MyMath.square(input), 0);
    }
    
    @ParamValues("sqIn") public static double[]  sqIn = {1,2,3,4,5,6,7};
    @ParamValues("sqOut") public static double[]  sqOut = {1,4,9,16,25,36, 49};
    
    @Test
    @WithParams({"absIn", "absOut"})
    public void testAbs(double input, double output){
	    Assert.assertEquals(output, MyMath.abs(input), 0);
    }
    
    @ParamValues("absIn") public static double[]  absIn = {1,2,-3,4,-5,6,-7, -100};
    
    @ParamValues("absOut")
    public static double[] absOut(){
    	double[] ret = new double[absIn.length];
    	List<Double> doubles = new ArrayList<Double>();
    	for(int i = 0; i < ret.length; i ++){
    	   ret[i] = java.lang.Math.abs(absIn[i]);
    	}
    	return ret;
    }
    
}
```
## Documentation
### DryRunner
DryUnit test class needs to be annotated with `@RunWith(DryRunner.class)`.

### @WithParams
Put this annotation on the test method that needs parameters. This annotation accepts an array of Strings as parameter identifiers.
It can also take a second argument `mode` with a value defined in the enum `com.nealxyc.dryunit.parameter.WithParams.Mode`. Default value is `Mode.GROUP`.
* `Mode.GROUP`: In `GROUP` mode all the parameters should have the same number of values.
	 For example, if the test method is annotated with `@WithParams({"v1", "v2"})` and 
	
    ```java
	 @ParamValues("v1") public static int[] v1 = {1, 2};
	 @ParamValues("v2") public static int[] v2 = {3, 6};
	 ```

Then the parameters that will be used to invoke this test method will be `{1, 3}` and `{2, 6}`.
* `Mode.PERMUTATION`: Take all the possible combinations of parameters from the values collections. For example, if the test method is annotated with `@WithParams({"v1", "v2"})` and 

```java
@ParamValues("v1") public static int[] v1 = {1, 2};
@ParamValues("v2") public static int[] v2 = {1, 2, 3};
```
Then the parameters that will be used to invoke this test method will be the following:
```java
{1,1}, {1, 2}, {1, 3}, {2, 1}, {2, 2}, {2, 3}
```


### @ParamValues
`@ParamValues` annotates the collection of value of the parameter identified. It can be on a public static field or a public static method.

## Install/Download
### Release 0.9.0
* Download ([jar file](https://github.com/nealxyc/DryUnit/releases/download/0.9.0/dryunit-0.9.0.jar)) ([source file](https://github.com/nealxyc/DryUnit/archive/0.9.0.zip))

## Build 
 
```
mvn compile test package
```

## License
The MIT License (MIT) http://opensource.org/licenses/MIT

