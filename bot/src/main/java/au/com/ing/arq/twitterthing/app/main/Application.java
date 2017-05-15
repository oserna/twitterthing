package au.com.ing.arq.twitterthing.app.main;

import au.com.ing.arq.twitterthing.integration.services.streams.StreamsService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;

@ComponentScan(basePackages = {"au.com.ing.arq.twitterthing"})
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            for (int i = 0; i < args.length; i++){
                System.out.println("args[" + i + "]: " + args[i]);
            }
            StreamsService sService = (StreamsService) ctx.getBean("StreamsService");
            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

            // ToDo Just Quick and dirty setup.
            // Let´ say that if args[1] is "Streams".ignoreCase(), then we run on Streams,
            // and args[2 to 5] are the secret keys and whatsoever for twitter's API.
            if ("Streams".equalsIgnoreCase(args[1])) {
                sService.setConsumerKey(args[2]);
                sService.setConsumerSecret(args[3]);
                sService.setAccessToken(args[4]);
                sService.setAccessTokenSecret(args[5]);
                sService.connect();
                sService.start();
            }
        };
    }

}
