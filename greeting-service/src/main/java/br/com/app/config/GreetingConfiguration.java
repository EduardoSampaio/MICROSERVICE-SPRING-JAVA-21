package br.com.app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ConfigurationProperties("greeting-service")
@RefreshScope
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GreetingConfiguration {
	private String greeting;
	private String defaultValue;
}
