/*
 * JBoss, Home of Professional Open Source
 * Copyright 2012, Red Hat Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.as.jsf.deployment;

import com.sun.faces.spi.DiscoverableInjectionProvider;
import com.sun.faces.spi.InjectionProviderException;
import org.jboss.as.jsf.JSFMessages;
import org.jboss.as.web.deployment.WebInjectionContainer;

import javax.naming.NamingException;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Stuart Douglas
 */
public class JSFInjectionProvider extends DiscoverableInjectionProvider {

    private final WebInjectionContainer container;

    public JSFInjectionProvider() {
        this.container = WebInjectionContainer.getCurrentInjectionContainer();
        if (this.container == null) {
            throw JSFMessages.MESSAGES.noThreadLocalInjectionContainer();
        }
    }

    @Override
    public void inject(final Object managedBean) throws InjectionProviderException {

    }

    @Override
    public void invokePreDestroy(final Object managedBean) throws InjectionProviderException {
        try {
            container.destroyInstance(managedBean);
        } catch (IllegalAccessException e) {
            throw JSFMessages.MESSAGES.instanceDestructionFailed(e);
        } catch (InvocationTargetException e) {
            throw JSFMessages.MESSAGES.instanceDestructionFailed(e);
        }
    }

    @Override
    public void invokePostConstruct(final Object managedBean) throws InjectionProviderException {
        try {
            container.newInstance(managedBean);
        } catch (IllegalAccessException e) {
            throw JSFMessages.MESSAGES.instanceCreationFailed(e);
        } catch (InvocationTargetException e) {
            throw JSFMessages.MESSAGES.instanceCreationFailed(e);
        } catch (NamingException e) {
            throw JSFMessages.MESSAGES.instanceCreationFailed(e);
        }
    }
}
