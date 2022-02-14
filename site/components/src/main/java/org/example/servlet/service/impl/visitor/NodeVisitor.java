package org.example.servlet.service.impl.visitor;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.example.servlet.util.JcrNode;

import javax.jcr.ItemVisitor;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import java.util.List;
import java.util.Optional;

@Slf4j
public class NodeVisitor implements ItemVisitor {

	private final List<String> excludedNodeTypes;
	@Getter
	private final List<JcrNode> jcrNodes;

	public NodeVisitor(List<String> excludedNodeTypes) {
		this.excludedNodeTypes = excludedNodeTypes;
		jcrNodes = Lists.newArrayList();
	}

	@Override
	public void visit(Property property) {

	}

	@Override
	public void visit(Node node) throws RepositoryException {

		boolean excludedNode = isExcludedNode(node);
		if(excludedNode){
			log.info("EXCLUDED NODE: " + node.getName() + "-" + node.getIdentifier());
			return;
		}

		jcrNodes.add(JcrNode.builder().path(node.getPath()).id(node.getIdentifier()).build());
		NodeIterator children = node.getNodes();
		while(children.hasNext()){
			Node childNode = children.nextNode();
			visit(childNode);
		}

	}

	private boolean isExcludedNode(Node node) {
		return Optional.ofNullable(excludedNodeTypes).orElse(Lists.newArrayList()).stream().anyMatch(s -> {
			try{
				return node.isNodeType(s);
			} catch (RepositoryException e){
				log.error(e.getMessage(), e);
			}
			return false;
		});
	}

}
