package org.example.servlet.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.servlet.service.AssessmentService;
import org.example.servlet.service.exceptions.NodeNotExistsException;
import org.example.servlet.service.impl.ops.AssessmentFindByIdRepositoryOperation;
import org.example.servlet.service.impl.ops.AssessmentFindByPathRepositoryOperation;
import org.example.servlet.service.impl.ops.AssessmentListAllRepositoryOperation;
import org.example.servlet.service.impl.ops.AssessmentSearchRepositoryOperation;
import org.example.servlet.util.JcrNode;

import javax.jcr.RepositoryException;
import java.util.List;
import java.util.Optional;

@Slf4j
public class AssessmentServiceImpl implements AssessmentService {

	/**
	 * list all nodes for a root node
	 *
	 * @param rootNodePath
	 * @return
	 * @throws NodeNotExistsException
	 * @throws RepositoryException
	 */
	@Override
	public List<JcrNode> listAll(String rootNodePath) throws NodeNotExistsException, RepositoryException {

		if(StringUtils.isEmpty(rootNodePath)){
			throw new IllegalArgumentException("root node path parameter shouldnt be null!");
		}

		AssessmentListAllRepositoryOperation assessmentListAllRepositoryOperation = new AssessmentListAllRepositoryOperation();
		return assessmentListAllRepositoryOperation.operation(rootNodePath);
	}

	/**
	 * search all nodes by query text
	 *
	 * @param query
	 * @return
	 * @throws RepositoryException
	 */
	@Override
	public List<JcrNode> search(String query) throws RepositoryException {

		if(StringUtils.isEmpty(query)){
			throw new IllegalArgumentException("query parameter shouldnt be null!");
		}

		AssessmentSearchRepositoryOperation assessmentSearchRepositoryOperation = new AssessmentSearchRepositoryOperation();
		return assessmentSearchRepositoryOperation.operation(query);
	}

	/**
	 * find by id of node
	 *
	 * @param resource
	 * @return
	 * @throws RepositoryException
	 */
	@Override
	public Optional<JcrNode> findById(String resource) throws RepositoryException {

		AssessmentFindByIdRepositoryOperation assessmentFindByIdRepositoryOperation = new AssessmentFindByIdRepositoryOperation();
		return assessmentFindByIdRepositoryOperation.operation(resource);
	}

	/**
	 * find by node absolute path
	 *
	 * @param resource
	 * @return
	 * @throws RepositoryException
	 */
	@Override
	public Optional<JcrNode> findByPath(String resource) throws RepositoryException {

		AssessmentFindByPathRepositoryOperation assessmentFindByPathRepositoryOperation = new AssessmentFindByPathRepositoryOperation();
		return assessmentFindByPathRepositoryOperation.operation(resource);
	}
}
