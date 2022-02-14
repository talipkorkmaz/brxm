package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.servlet.service.AssessmentService;
import org.example.servlet.service.exceptions.NodeNotExistsException;
import org.example.servlet.service.impl.AssessmentServiceImpl;
import org.example.servlet.util.AssessmentPathUtil;
import org.example.servlet.util.JcrNode;
import org.springframework.http.HttpStatus;

import javax.jcr.RepositoryException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Slf4j
public class AssessmentServlet extends HttpServlet {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		AssessmentService assessmentService = new AssessmentServiceImpl();
		List<JcrNode> jcrNodes;
		String query = req.getParameter("q");
		if(StringUtils.isEmpty(query)){
			try{
				jcrNodes = assessmentService.listAll(AssessmentPathUtil.CONTENT_DOCUMENTS);
			} catch (RepositoryException e){
				res.setContentType("application/json; charset=UTF-8");
				res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
				res.getWriter().write("repository operation failed!");
				return;
			} catch (NodeNotExistsException e){
				res.setContentType("application/json; charset=UTF-8");
				res.setStatus(HttpStatus.NOT_FOUND.value());
				res.getWriter().write("document node not found!");
				return;
			}
		} else {
			try{
				jcrNodes = assessmentService.search(query);
			} catch (RepositoryException e){
				res.setContentType("application/json; charset=UTF-8");
				res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
				res.getWriter().write("repository operation failed!");
				return;
			}
		}

		PrintWriter out = res.getWriter();
		res.setContentType("application/json");
		res.setStatus(HttpStatus.OK.value());
		res.setCharacterEncoding("UTF-8");
		out.print(objectMapper.writeValueAsString(jcrNodes));
		out.flush();

	}

}
