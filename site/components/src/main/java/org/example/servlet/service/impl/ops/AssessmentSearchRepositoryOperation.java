package org.example.servlet.service.impl.ops;

import com.google.common.collect.Lists;
import org.example.servlet.template.HstRepoOperationTemplate;
import org.example.servlet.util.JcrNode;
import org.example.servlet.util.JcrProperty;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import java.util.List;

public class AssessmentSearchRepositoryOperation extends HstRepoOperationTemplate<List<JcrNode>, String> {

	protected List<JcrNode> customOperation(Session session, String queryText) throws RepositoryException {

		List<JcrNode> results = Lists.newArrayList();
		QueryManager queryManager = session.getWorkspace().getQueryManager();

		//TODO: SECURITY ?
		Query query = queryManager.createQuery("//*[jcr:contains(., '" + queryText + "')]", Query.XPATH);
		QueryResult result = query.execute();

		NodeIterator nodeIter = result.getNodes();
		while(nodeIter.hasNext()){
			Node node = nodeIter.nextNode();

			JcrNode jcrNode = JcrNode.builder().path(node.getPath()).id(node.getIdentifier()).build();

			List<JcrProperty> jcrProperties = Lists.newArrayList();
			PropertyIterator properties = node.getProperties();
			while(properties.hasNext()){
				Property property = properties.nextProperty();
				jcrProperties.add(JcrProperty.builder().name(property.getName()).build());
			}
			jcrNode.setProperties(jcrProperties);

			results.add(jcrNode);
		}

		return results;

	}
}
