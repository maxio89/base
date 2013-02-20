/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.project.web;

import javax.faces.component.FacesComponent;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import java.io.IOException;

/**
 * JSF Component for rendering JavaScript code that installs JavaScript handler that raises formChanged event on component specified with "for" attribute.
 */
@FacesComponent(UIFormChangeEvent.COMPONENT_TYPE)
public class UIFormChangeEvent extends UIComponentBase implements NamingContainer {
// ------------------------------ FIELDS ------------------------------

    /**
     * The standard component type for this component.
     */
    public static final String COMPONENT_TYPE = "com.project.web.UIFormChangeEvent";

// -------------------------- OTHER METHODS --------------------------

    @Override
    public void encodeBegin(FacesContext context) throws IOException
    {
        getAttributes().put("forClientId", getForClientId(context));
        super.encodeBegin(context);
    }

    @Override
    public String getFamily()
    {
        return UINamingContainer.COMPONENT_FAMILY;
    }

    protected String getForClientId(FacesContext context)
    {
        String aFor = (String) getAttributes().get("for");
        if (aFor != null) {
            UIComponent component = findComponent(aFor);
            if (component != null) {
                return component.getClientId(context);
            } else {
                component = getParent().findComponent(aFor);
                if (component != null) {
                    return component.getClientId(context);
                } else {
                    return null;
                }
            }
        }
        return null;
    }
}
