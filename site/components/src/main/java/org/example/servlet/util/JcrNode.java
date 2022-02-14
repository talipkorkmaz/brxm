package org.example.servlet.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude (content = JsonInclude.Include.NON_NULL)
public class JcrNode {

	private String id;
	private String path;
	private List<JcrProperty> properties;

}
