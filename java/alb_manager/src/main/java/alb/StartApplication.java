package alb;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Start the program
 *
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@EnableCaching
@EnableScheduling
@Order(value = 1)
public class StartApplication implements CommandLineRunner
{
    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
        System.out.println("(♥◠‿◠)ノ゙  Start Success   ლ( ́ڡ`ლ)゙" +
                " ___  ____   __    ____  ____    ___  __  __  ___  ___  ____  ___  ___ \n" +
                "/ __)(_  _) /__\\  (  _ \\(_  _)  / __)(  )(  )/ __)/ __)( ___)/ __)/ __)\n" +
                "\\__ \\  )(  /(__)\\  )   /  )(    \\__ \\ )(__)(( (__( (__  )__) \\__ \\\\__ \\\n" +
                "(___/ (__)(__)(__)(_)\\_) (__)   (___/(______)\\___)\\___)(____)(___/(___/");
    }

    @Override
    public void run(String... args) throws Exception {
    }

}
