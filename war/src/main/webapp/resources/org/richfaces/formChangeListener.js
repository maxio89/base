/*
 * JBoss, Home of Professional Open Source
 * Copyright ${year}, Red Hat, Inc. and individual contributors
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

(function ($, rf)
{

    rf.ui = rf.ui || {};

    /**
     * Registers new listener for formchanged event. ServerSide will be notified via ajax only once.
     * @param componentId identifier of script component with code invoking this method
     */
    rf.ui.FormChangeListener = function (componentId)
    {
        var jform = jQuery(document.getElementById(componentId)).parents("form").eq(0);
        this.formchangehandler = function (event)
        {
            jform.unbind(rf.ui.FormChangeListener.formchangedEvent, this.formchangehandler);
            var params = {};
            params[componentId] = componentId;
            var options = {clientParameters:params};
            RichFaces.ajax(document.getElementById(componentId), event, options);
        };
        jform.bind(rf.ui.FormChangeListener.formchangedEvent, this.formchangehandler);
    };
    rf.BaseComponent.extend(rf.ui.FormChangeListener);

    // define super class link
    var $super = rf.ui.FormChangeListener.$super;

    rf.ui.FormChangeListener.formchangedEvent = "formchanged";
    rf.ui.FormChangeListener.bindEvent = function (componentId)
    {
        var inputChangeHandler = function (a, b, c, d, e, f)
        {
            $(a.target).trigger(rf.ui.FormChangeListener.formchangedEvent);
        };
        $(document.getElementById(componentId)).bind("paste", inputChangeHandler).bind("cut", inputChangeHandler).bind("change",
                inputChangeHandler).bind("keyup", inputChangeHandler);
    };
})(jQuery, RichFaces);

