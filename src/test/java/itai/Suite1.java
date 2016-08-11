package itai;

import org.testng.annotations.Test;

import base.AbstractSuite;

public class Suite1 extends AbstractSuite{
	
	@Test
	public void A(){
		System.out.println("Suite1 - A");
	}
	
	@Test
	public void B(){
		System.out.println("Suite1 - B");
	}

}
