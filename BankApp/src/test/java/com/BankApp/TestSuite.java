package com.BankApp;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectClasses( { AdminDoaTest.class, CustomerDoaTest.class } )
public class TestSuite {

}
