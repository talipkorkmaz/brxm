package org.example.servlet.service.impl.ops;

import org.example.servlet.template.HstRepoOperationTemplate;
import org.example.servlet.util.JcrNode;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.Optional;

public class AssessmentFindByIdRepositoryOperation extends HstRepoOperationTemplate<Optional<JcrNode>, String> {

	protected Optional<JcrNode> customOperation(Session session, String resource) throws RepositoryException {

		Node node = session.getNodeByIdentifier(resource);
		if(node == null){
			return Optional.empty();
		}
		return Optional.of(JcrNode.builder().id(node.getIdentifier()).path(node.getPath()).build());

	}
}
