package com.qa.hs.tests;

import org.testng.annotations.Test;

import com.qa.hs.keyword.engine.KeywordEngine;

public class LoginTest {
	
	public KeywordEngine keywordengine;
	@Test
	public void LoginTest() {
		keywordengine=new KeywordEngine(); 
		keywordengine.startExcution("login");
	}

}
