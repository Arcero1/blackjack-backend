package com.qa.blackjack;

import com.qa.blackjack.repositories.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.qa.blackjack")
public class BlackJackConfig {
}
