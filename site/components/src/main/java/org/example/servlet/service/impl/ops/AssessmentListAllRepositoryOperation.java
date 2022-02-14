package org.example.servlet.service.impl.ops;

import com.google.common.collect.Lists;
import org.example.servlet.service.exceptions.NodeNotExistsException;
import org.example.servlet.service.impl.visitor.NodeVisitor;
import org.example.servlet.template.HstRepoOperationTemplate;
import org.example.servlet.util.JcrNode;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.List;

public class AssessmentListAllRepositoryOperation extends HstRepoOperationTemplate<List<JcrNode>, String> {

	protected List<JcrNode> customOperation(Session session, String rootNodePath) throws RepositoryException {

		boolean exists = session.nodeExists(rootNodePath);
		if(!exists){
			throw new NodeNotExistsException(rootNodePath + "path not exists!");
		}

		NodeVisitor visitor = new NodeVisitor(Lists.newArrayList("hippofacnav:facetnavigation"));
		Node node = session.getNode(rootNodePath);
		if(node == null){
			throw new NodeNotExistsException(rootNodePath + "path not exists!");
		}
		node.accept(visitor);
		return visitor.getJcrNodes();

	}
}
