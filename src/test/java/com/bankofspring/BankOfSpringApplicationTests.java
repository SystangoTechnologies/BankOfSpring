package com.bankofspring;

import com.bankofspring.test.controller.AccountControllerTest;
import com.bankofspring.test.controller.CustomerControllerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		CustomerControllerTest.class,
		AccountControllerTest.class
})
public class BankOfSpringApplicationTests {

}
