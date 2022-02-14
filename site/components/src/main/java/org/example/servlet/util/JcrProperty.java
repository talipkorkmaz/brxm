package org.example.servlet.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude (content = JsonInclude.Include.NON_NULL)
public class JcrProperty {

	private String name;
	private String value;

}
