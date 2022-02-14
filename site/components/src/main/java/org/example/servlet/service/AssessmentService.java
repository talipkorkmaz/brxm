package org.example.servlet.service;

import org.example.servlet.service.exceptions.NodeNotExistsException;
import org.example.servlet.util.JcrNode;

import javax.jcr.RepositoryException;
import java.util.List;
import java.util.Optional;

public interface AssessmentService {

	List<JcrNode> listAll(String rootNodePath) throws RepositoryException, NodeNotExistsException;

	List<JcrNode> search(String query) throws RepositoryException;

	Optional<JcrNode> findById(String resource) throws RepositoryException;

	Optional<JcrNode> findByPath(String resource) throws RepositoryException;
}
