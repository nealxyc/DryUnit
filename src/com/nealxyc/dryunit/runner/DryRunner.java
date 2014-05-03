package com.nealxyc.dryunit.runner;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

public class DryRunner extends BlockJUnit4ClassRunner{

	public DryRunner(Class<?> klass) throws InitializationError {
		super(klass);
		// TODO Auto-generated constructor stub
	}

}
