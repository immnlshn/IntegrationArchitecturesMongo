package de.hbrs.ia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringShellApplication {

    public static void main(String[] args) {
        // stop error message in this version of springshell
        System.setProperty("org.jline.terminal.exec.redirectPipeCreationMode", "native");
        // start the spring application
        SpringApplication.run(SpringShellApplication.class);
    }

}
