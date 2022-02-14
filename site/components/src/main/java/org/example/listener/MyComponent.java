package org.example.listener;

import lombok.extern.slf4j.Slf4j;
import org.onehippo.cms7.services.eventbus.HippoEventListenerRegistry;

@Slf4j
public class MyComponent {

	private MyListener listener;

	public void init() {
		log.info("MyListener registered..");
		listener = new MyListener();
		HippoEventListenerRegistry.get().register(listener);
	}

	public void destroy() {
		HippoEventListenerRegistry.get().unregister(listener);
		log.info("MyListener destroyed..");
	}

}
