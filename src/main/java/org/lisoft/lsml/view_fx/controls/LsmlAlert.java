/*
 * @formatter:off
 * Li Song Mechlab - A 'mech building tool for PGI's MechWarrior: Online.
 * Copyright (C) 2013  Li Song
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
//@formatter:on
package org.lisoft.lsml.view_fx.controls;

import org.lisoft.lsml.view_fx.util.FxControlUtils;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Window;

/**
 * This class applies some standard attributes and settings to the standard {@link Alert} to reduce code duplication
 * throughout LSML.
 *
 * @author Li Song
 */
public class LsmlAlert extends Alert {

    /**
     * @see Alert#Alert(AlertType)
     */
    public LsmlAlert(Node aSource, AlertType aAlertType) {
        super(aAlertType);
        setupThis(aSource);
    }

    /**
     * @see Alert#Alert(AlertType, String, ButtonType...)
     */
    public LsmlAlert(Node aSource, AlertType aAlertType, String aContentText, ButtonType... aButtons) {
        super(aAlertType, aContentText, aButtons);
        setupThis(aSource);
    }

    public LsmlAlert(Window aSource, AlertType aAlertType, String aContentText, ButtonType... aButtons) {
        super(aAlertType, aContentText, aButtons);
        setupThis(aSource);
    }

    /**
     * Performs the setup to make this alert look like one of us
     *
     * @param aSource
     */
    private void setupThis(Node aSource) {
        if (null != aSource && null != aSource.getScene()) {
            setupThis(aSource.getScene().getWindow());
        }
        setupThis((Window) null);
    }

    /**
     * Performs the setup to make this alert look like one of us
     *
     * @param aSource
     */
    private void setupThis(Window aSource) {
        if (null != aSource) {
            initOwner(aSource);
        }
        getDialogPane().getStylesheets().addAll(FxControlUtils.getBaseStyleSheet());
    }
}