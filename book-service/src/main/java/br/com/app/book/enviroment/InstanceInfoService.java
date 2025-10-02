package br.com.app.book.enviroment;

import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class InstanceInfoService implements ApplicationListener<WebServerInitializedEvent> {

	private int port;

	@Override
	public void onApplicationEvent(WebServerInitializedEvent event) {
		this.port = event.getWebServer().getPort();
	}

	public int retriveServerPort() {
		return this.port;
	}

}
