/*******************************************************************************
 * Copyright (c) 2007 - 2013 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.jboss.tools.as.test.core.parametized.server.publishing;

import java.io.IOException;
import java.util.Collection;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.server.core.IModule;
import org.eclipse.wst.server.core.IServer;
import org.eclipse.wst.server.core.model.IModuleFile;
import org.jboss.ide.eclipse.as.core.modules.SingleDeployableFactory;
import org.jboss.tools.as.test.core.internal.utils.MatrixUtils;
import org.jboss.tools.as.test.core.internal.utils.ResourceUtils;
import org.jboss.tools.as.test.core.internal.utils.wtp.CreateProjectOperationsUtility;
import org.jboss.tools.as.test.core.internal.utils.wtp.JavaEEFacetConstants;
import org.jboss.tools.as.test.core.internal.utils.wtp.OperationTestCase;
import org.jboss.tools.as.test.core.parametized.server.ServerParameterUtils;
import org.jboss.tools.test.util.JobUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class SingleDeployableFileTest extends AbstractPublishingTest {
	public static final String PROJECT_ROOT_NAME = "SingleDeployableTest";
	public static int count = 0;
	
	
	@Parameters
	public static Collection<Object[]> params() {
		Object[] servers = ServerParameterUtils.getPublishServerTypes();
		Object[] zipOption = ServerParameterUtils.getServerZipOptions();
		Object[][] allOptions = new Object[][] {
				servers, zipOption, new Object[]{ServerParameterUtils.DEPLOY_META}, new Object[]{ServerParameterUtils.DEPLOY_PERMOD_DEFAULT}
		};
		return MatrixUtils.toMatrix(allOptions);
	}
	
	private String projectName;

	public SingleDeployableFileTest(String serverType, String zip,
			String deployLoc, String perMod) {
		super(serverType, zip, deployLoc, perMod);
	}
	
	@Override
	protected void createProjects() throws Exception {
		projectName = PROJECT_ROOT_NAME + count;
		count++;
		
		IDataModel dm = CreateProjectOperationsUtility.getEARDataModel(projectName, "earContent", null, null, JavaEEFacetConstants.EAR_5, false);
		OperationTestCase.runAndVerify(dm);
		IProject p = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		assertTrue(p.exists());
		final String filename = "test.xml";
		IResource file = ResourceUtils.createFile(p, filename, "<test>done</test>");
		IModule[] mods = SingleDeployableFactory.getFactory().getModules();
		assertEquals(mods.length, 0);
		SingleDeployableFactory.makeDeployable(file);
		mods = SingleDeployableFactory.getFactory().getModules();
		assertEquals(mods.length, 1);
		addModuleToServer(mods[0]);
	}
	
	@Test
	public void testSingleDeployableFullPublish() throws IOException, CoreException {
		IProject p = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		
		// Initial
		fullPublishAndVerify(IServer.PUBLISH_FULL, "<test>done</test>");
		
		/* Add incremental */
		ResourceUtils.setContents(p, new Path("test.xml"), "2");
		JobUtils.waitForIdle();
		fullPublishAndVerify(IServer.PUBLISH_INCREMENTAL, "2");
		ResourceUtils.setContents(p, new Path("test.xml"), "3");
		JobUtils.waitForIdle();
		fullPublishAndVerify(IServer.PUBLISH_INCREMENTAL, "3");
	}
	
	private void fullPublishAndVerify(int publishType, String expectedContents) throws CoreException, IOException  {
		publishAndCheckError(server,publishType);
		boolean isFullPublish = publishType == IServer.PUBLISH_FULL;
		int[] vals = isFullPublish ? new int[] { 2,0} : new int[] {2,0};
		// binary modules are always full publish really
		vals[0] += getFullPublishChangedResourceCountModifier();
		vals[1] += getFullPublishRemovedResourceCountModifier();
		verifyPublishMethodResults(vals[0], vals[1]);
	}
	
	private IModuleFile findBinaryModuleFile(IModuleFile[] files, String name) {
		for( int i = 0; i < files.length; i++ ) {
			if( files[i].getName().equals(name))
				return files[i];
		}
		return null;
	}
}
