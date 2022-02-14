package org.example.servlet.template;

import lombok.Getter;
import org.hippoecm.hst.site.HstServices;

import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

public abstract class HstRepoOperationTemplate<T, D> {

	public T operation(D data) throws RepositoryException {

		Repository repository = HstServices.getComponentManager().getComponent(Repository.class.getName());
		Session session = null;
		try{
			session = repository.login(new SimpleCredentials("admin", "admin".toCharArray()));

			return customOperation(session, data);

		} finally{
			if(session != null){
				session.logout();
			}
		}
	}

	protected abstract T customOperation(Session session, D data) throws RepositoryException;
}
