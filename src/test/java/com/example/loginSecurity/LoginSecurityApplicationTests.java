package com.example.loginSecurity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.example.config.AppConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@EnableTransactionManagement
@ContextConfiguration(classes = {AppConfig.class})
@SpringBootTest
public class LoginSecurityApplicationTests {
	
	@Test
	public void contextLoads() {
	}

}
