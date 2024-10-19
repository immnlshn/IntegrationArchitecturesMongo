package de.hbrs.ia.config;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.jline.PromptProvider;


@Configuration
public class ShellConfig {

    @Bean
    public PromptProvider customPromptProvider() {
        return () -> new AttributedString("HPR: ", AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN));
    }

}
