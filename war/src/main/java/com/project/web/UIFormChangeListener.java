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

import javax.el.MethodExpression;
import javax.faces.component.FacesComponent;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseId;

/**
 * JSF component that notifies listener when enclosing form gets formChanged JavaScript event.
 */
@FacesComponent(UIFormChangeListener.COMPONENT_TYPE)
public class UIFormChangeListener extends UIComponentBase implements NamingContainer {
// ------------------------------ FIELDS ------------------------------

    /**
     * The standard component type for this component.
     */
    public static final String COMPONENT_TYPE = "com.project.web.UIFormChangeListener";

// -------------------------- OTHER METHODS --------------------------

    @Override
    public void broadcast(FacesEvent event)
    {
        super.broadcast(event);
        if (this.equals(event.getComponent()) && event instanceof FormChageEvent) {
            MethodExpression listener = (MethodExpression) getAttributes().get("listener");
            if (listener != null) {
                listener.invoke(FacesContext.getCurrentInstance().getELContext(), null);
            }
        }
    }

    @Override
    public void decode(FacesContext context)
    {
        super.decode(context);
        final String paramValue = context.getExternalContext().getRequestParameterMap().get(getClientId(context));
        if (paramValue != null) {
            new FormChageEvent(this).queue();
        }
    }

    @Override
    public String getFamily()
    {
        return UINamingContainer.COMPONENT_FAMILY;
    }

// -------------------------- INNER CLASSES --------------------------

    public static class FormChageEvent extends ActionEvent {
// --------------------------- CONSTRUCTORS ---------------------------

        /**
         * <p>Construct a new event object from the specified source component.</p>
         *
         * @param component Source {@link javax.faces.component.UIComponent} for this event
         *
         * @throws IllegalArgumentException if <code>component</code> is
         *                                  <code>null</code>
         */
        public FormChageEvent(UIComponent component)
        {
            super(component);
        }

// -------------------------- OTHER METHODS --------------------------

        @Override
        public PhaseId getPhaseId()
        {
            return PhaseId.INVOKE_APPLICATION;
        }
    }
}
