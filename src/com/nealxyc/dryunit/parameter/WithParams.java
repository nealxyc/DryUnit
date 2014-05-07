package com.nealxyc.dryunit.parameter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface WithParams {
    /** Parameter ids */
    String[] value();

    Mode mode() default Mode.GROUP;

    public static enum Mode {
	/**
	 * This is the default mode. In GROUP mode all the parameters should have the same number of
	 * values
	 * For example, if the test method is annotated with <pre>@WithParams({"v1", "v2"})</pre>
	 * and 
	 * <pre>
	 * @ParamValues("v1") public static int[] v1 = {1, 2};
	 * @ParamValues("v2") public static int[] v2 = {3, 6};
	 * </pre>
	 * Then the parameters that will be used to invoke this test method will be:<br>
	 * <pre>{1, 3}, {2, 6}</pre>
	 */
	GROUP, //
	/** Take all the possible combinations of parameters from the values lists. <br>
	 * For example, if the test method is annotated with <pre>@WithParams({"v1", "v2"})</pre>
	 * and 
	 * <pre>
	 * @ParamValues("v1") public static int[] v1 = {1, 2};
	 * @ParamValues("v2") public static int[] v2 = {1, 2, 3};
	 * </pre>
	 * Then the parameters that will be used to invoke this test method will be:<br>
	 * <pre>{1,1}, {1, 2}, {1, 3}, {2, 1}, {2, 2}, {2, 3}</pre>
	 * */
	PERMUTATION
    }

    public static class Helper {

	public static Collection<Object[]> permuateToParameters(Collection<Object>[] lists) {
	    List<Combination> combinations = new ArrayList<Combination>();
	    List<Object[]> listOfObjectArray = new ArrayList<Object[]>();
	    for (Collection<Object> list : lists) {
		if (combinations.size() == 0) {
		    for (Object obj : list) {
			combinations.add(new Combination().combine(obj));
		    }
		} else {
		    List<Combination> newCombinations = new ArrayList<Combination>();
		    for (Object obj : list) {
			for (Combination comb : combinations) {
			    newCombinations.add(comb.clone().combine(obj));
			}
		    }
		    combinations = newCombinations ;
		}
	    }
	    for(Combination comb: combinations){
		listOfObjectArray.add(comb.toArray());
	    }
	    return listOfObjectArray ;
	}
    }

    public static class Combination {
	private List<Object> elements = new ArrayList<Object>();

	public Combination combine(Object obj) {
	    elements.add(obj);
	    return this;
	}
	
	public Combination clone(){
	    Combination comb = new Combination();
	    comb.elements.addAll(elements);
	    return comb ;
	}

	public Object[] toArray() {
	    return elements.toArray(new Object[0]);
	}
    }
}
