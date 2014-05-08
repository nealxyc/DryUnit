package com.nealxyc.dryunit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.nealxyc.dryunit.example.FibonacciTest;
import com.nealxyc.dryunit.paramter.ParamDefinitionTest;
import com.nealxyc.dryunit.paramter.ParamRefTest;
import com.nealxyc.dryunit.paramter.ParameterResolverTest;
import com.nealxyc.dryunit.runner.DryRunnerTest;

@RunWith(Suite.class)
@SuiteClasses({
    FibonacciTest.class,
    ParamDefinitionTest.class,
    ParameterResolverTest.class,
    ParamRefTest.class,
    DryRunnerTest.class
})
public class DryUnitSuite {

}
