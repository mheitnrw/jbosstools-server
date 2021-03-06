/*******************************************************************************
 * Copyright (c) 2014 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.jboss.ide.eclipse.as.classpath.core.runtime.jbossmodules.internal;

import java.util.ArrayList;

import org.eclipse.wst.server.core.IRuntimeType;
import org.jboss.ide.eclipse.as.classpath.core.runtime.IRuntimePathProvider;
import org.jboss.ide.eclipse.as.classpath.core.runtime.cache.internal.InternalRuntimeClasspathModel;
import org.jboss.ide.eclipse.as.classpath.core.runtime.path.internal.LayeredProductPathProvider;

/**
 * This class is in charge of what modules 
 * should be returned for any given facet, 
 * facet+version combination.
 * 
 * This class is currently unused. 
 */
public class JBossModulesDefaultClasspathModel extends InternalRuntimeClasspathModel {
	
	public JBossModulesDefaultClasspathModel(IRuntimeType rtt) {
		super();
		setProviders(getDefaultJBossModulesEntries());
		// TODO add customizations on a per-facet basis
	}
	
	

	
	private IRuntimePathProvider[] getDefaultJBossModulesEntries() {
		ArrayList<IRuntimePathProvider> sets = new ArrayList<IRuntimePathProvider>();
		sets.add(createModulePath("javax.security.auth.message.api")); //$NON-NLS-1$ 
		sets.add(createModulePath("javax.security.jacc.api"));//$NON-NLS-1$ 
		sets.add(createModulePath("javax.ejb.api"));//$NON-NLS-1$ 
		sets.add(createModulePath("javax.persistence.api"));//$NON-NLS-1$ 
		sets.add(createModulePath("javax.faces.api"));//$NON-NLS-1$ 
		sets.add(createModulePath("javax.interceptor.api"));//$NON-NLS-1$ 
		sets.add(createModulePath("javax.rmi.api"));//$NON-NLS-1$ 
		sets.add(createModulePath("javax.inject.api"));//$NON-NLS-1$ 
		sets.add(createModulePath("javax.resource.api"));//$NON-NLS-1$ 
		sets.add(createModulePath("javax.ws.rs.api"));//$NON-NLS-1$ 
		sets.add(createModulePath("javax.xml.bind.api"));//$NON-NLS-1$ 
		sets.add(createModulePath("javax.xml.rpc.api"));//$NON-NLS-1$ 
		sets.add(createModulePath("javax.xml.registry.api"));//$NON-NLS-1$ 
		sets.add(createModulePath("javax.xml.ws.api"));//$NON-NLS-1$ 
		sets.add(createModulePath("javax.xml.soap.api"));//$NON-NLS-1$ 
		sets.add(createModulePath("javax.wsdl4j.api"));//$NON-NLS-1$ 
		sets.add(createModulePath("javax/jms.api"));//$NON-NLS-1$ 
		sets.add(createModulePath("javax.management.j2ee.api"));//$NON-NLS-1$ 
		sets.add(createModulePath("javax.transaction.api"));//$NON-NLS-1$ 
		sets.add(createModulePath("javax.jws.api"));//$NON-NLS-1$ 
		sets.add(createModulePath("javax.mail.api"));//$NON-NLS-1$ 
		sets.add(createModulePath("javax.enterprise.deploy.api"));//$NON-NLS-1$ 
		sets.add(createModulePath("javax.enterprise.api"));//$NON-NLS-1$ 
		sets.add(createModulePath("javax.servlet.jstl.api"));//$NON-NLS-1$ 
		sets.add(createModulePath("javax.servlet.jsp.api"));//$NON-NLS-1$ 
		sets.add(createModulePath("javax.servlet.api"));//$NON-NLS-1$ 
		sets.add(createModulePath("javax.activation.api"));//$NON-NLS-1$ 
		sets.add(createModulePath("javax.validation.api"));//$NON-NLS-1$ 
		sets.add(createModulePath("javax.annotation.api"));//$NON-NLS-1$ 
		sets.add(createModulePath("javax.el.api"));//$NON-NLS-1$ 
		sets.add(createModulePath("org.hibernate.validator"));//$NON-NLS-1$ 
		sets.add(createModulePath("org.picketbox"));//$NON-NLS-1$ 
		sets.add(createModulePath("org.jboss.as.controller-client"));//$NON-NLS-1$ 
		sets.add(createModulePath("org.jboss.dmr"));//$NON-NLS-1$ 
		sets.add(createModulePath("org.jboss.logging"));//$NON-NLS-1$ 
		sets.add(createModulePath("org.jboss.resteasy.resteasy-jaxb-provider"));//$NON-NLS-1$ 
		sets.add(createModulePath("org.jboss.resteasy.resteasy-jaxrs"));//$NON-NLS-1$ 
		sets.add(createModulePath("org.jboss.resteasy.resteasy-multipart-provider"));//$NON-NLS-1$ 
		sets.add(createModulePath("org.jboss.ejb3"));//$NON-NLS-1$ 
		return (IRuntimePathProvider[]) sets.toArray(new IRuntimePathProvider[sets.size()]);
	}
	
	private LayeredProductPathProvider createModulePath(String moduleName) {
		return createModulePath(moduleName, null);
	}
	private LayeredProductPathProvider createModulePath(String moduleName, String slot) {
		return new LayeredProductPathProvider(moduleName, slot);
	}
	
}
