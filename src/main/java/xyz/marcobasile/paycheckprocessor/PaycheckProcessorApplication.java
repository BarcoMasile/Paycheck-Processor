package xyz.marcobasile.paycheckprocessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import xyz.marcobasile.paycheckprocessor.configuration.ApplicationProperties;
import xyz.marcobasile.paycheckprocessor.configuration.AwsProperties;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableConfigurationProperties(value = { ApplicationProperties.class, AwsProperties.class })
public class PaycheckProcessorApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext appContext = SpringApplication.run(PaycheckProcessorApplication.class, args);
	}

}

