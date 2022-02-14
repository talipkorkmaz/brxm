package org.example.servlet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.servlet.service.AssessmentService;
import org.example.servlet.service.exceptions.NodeNotExistsException;
import org.example.servlet.util.AssessmentPathUtil;
import org.example.servlet.util.JcrNode;

import javax.jcr.RepositoryException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.Optional;

@Slf4j
@Path ("/api/v1/nodes")
@RequiredArgsConstructor
public class AssessmentResource extends org.hippoecm.hst.jaxrs.services.AbstractResource {

	private final AssessmentService assessmentService;

	@GET
	@Path ("/")
	@Produces ("application/json")
	public Response findAll(@QueryParam ("q") String query) {

		try{
			List<JcrNode> jcrNodes;
			if(query == null){
				jcrNodes = assessmentService.listAll(AssessmentPathUtil.CONTENT_DOCUMENTS);
			} else {
				if(StringUtils.isEmpty(query)){
					return Response.status(Response.Status.BAD_REQUEST).entity("query shouldnt be null!").type(MediaType.APPLICATION_JSON).build();
				}
				jcrNodes = assessmentService.search(query);
			}
			return Response.status(Response.Status.OK).entity(jcrNodes).type(MediaType.APPLICATION_JSON).build();
		} catch (NodeNotExistsException e){
			return Response.status(Response.Status.NOT_FOUND).entity("node not found!").type(MediaType.APPLICATION_JSON).build();
		} catch (RepositoryException e){
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("repository operation failed!").type(MediaType.APPLICATION_JSON).build();
		}

	}

	@GET
	@Path ("{resource}")
	@Produces ("application/json")
	public Response findById(@PathParam ("resource") String resource) {

		if(StringUtils.isEmpty(resource)){
			return Response.status(Response.Status.BAD_REQUEST).entity("resource shouldn't be null!").type(MediaType.APPLICATION_JSON).build();
		}

		try{
			Optional<JcrNode> optionalJcrNode = assessmentService.findById(resource);

			if(optionalJcrNode.isPresent()){
				return Response.status(Response.Status.OK).entity(optionalJcrNode.get()).type(MediaType.APPLICATION_JSON).build();
			} else {
				return Response.status(Response.Status.NOT_FOUND).type(MediaType.APPLICATION_JSON).build();
			}

		} catch (RepositoryException e){
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("repository operation failed!").type(MediaType.APPLICATION_JSON).build();
		}

	}

	@GET
	@Path ("/path/{s:.*}")
	@Produces ("application/json")
	public Response findByPath(@Context UriInfo ui) {

		try{
			Optional<JcrNode> optionalJcrNode = assessmentService.findByPath(StringUtils.substringAfter(ui.getPath(), "api/v1/nodes/path"));
			if(optionalJcrNode.isPresent()){
				return Response.status(Response.Status.OK).entity(optionalJcrNode.get()).type(MediaType.APPLICATION_JSON).build();
			} else {
				return Response.status(Response.Status.NOT_FOUND).type(MediaType.APPLICATION_JSON).build();
			}
		} catch (RepositoryException e){
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("repository operation failed!").type(MediaType.APPLICATION_JSON).build();
		}

	}

}

