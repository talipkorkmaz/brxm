package org.example.listener;

import lombok.extern.slf4j.Slf4j;
import org.example.servlet.util.AssessmentPathUtil;
import org.onehippo.cms7.event.HippoEvent;
import org.onehippo.cms7.event.HippoEventConstants;
import org.onehippo.cms7.services.eventbus.Subscribe;

@Slf4j
public class MyListener {

	@Subscribe
	public void handleEvent(HippoEvent event) {

		if(HippoEventConstants.CATEGORY_WORKFLOW.equals(event.category())){
			// respond to event
			String documentPath = (String) event.get("documentPath");
			if(documentPath != null && documentPath.startsWith(AssessmentPathUtil.CONTENT)){
				log.info(event.user() + "-" + event.action());
			}

		}
	}
}
