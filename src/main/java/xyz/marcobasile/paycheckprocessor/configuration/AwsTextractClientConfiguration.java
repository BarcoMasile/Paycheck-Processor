package xyz.marcobasile.paycheckprocessor.configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.textract.AmazonTextract;
import com.amazonaws.services.textract.AmazonTextractClientBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class AwsTextractClientConfiguration {

    private final AwsProperties props;

    @Bean
    public AmazonTextract textract() {

        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(props.getAccessKey(), props.getSecretKey());

        return AmazonTextractClientBuilder.standard()
                // .withRegion(props.getRegion())
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
                        "https://textract.us-east-1.amazonaws.com", "us-east-1"))
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }


}
