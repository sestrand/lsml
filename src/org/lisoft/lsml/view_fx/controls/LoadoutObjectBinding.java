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

import java.util.concurrent.Callable;

import org.lisoft.lsml.messages.Message;
import org.lisoft.lsml.messages.MessageReceiver;
import org.lisoft.lsml.messages.MessageReception;
import org.lisoft.lsml.view_fx.LiSongMechLab;

import javafx.beans.binding.ObjectBinding;
import javafx.util.Callback;

/**
 * This binding will bind to an arbitrary attribute of a loadout and provide automatic updating.
 * 
 * @author Li Song
 * @param <T>
 *            Type of object the binding contains.
 */
public class LoadoutObjectBinding<T> extends ObjectBinding<T> implements MessageReceiver {
    private final Callable<T>                valueFunction;
    private final Callback<Message, Boolean> invalidationFilter;

    public LoadoutObjectBinding(MessageReception aMessageReception, Callable<T> aValueFunction,
            Callback<Message, Boolean> aInvalidationFilter) {
        aMessageReception.attach(this);
        valueFunction = aValueFunction;
        invalidationFilter = aInvalidationFilter;
    }

    @Override
    protected T computeValue() {
        try {
            return valueFunction.call();
        }
        catch (Exception e) {
            LiSongMechLab.showError(e);
        }
        return null;
    }

    @Override
    public void receive(Message aMsg) {
        try {
            if (invalidationFilter.call(aMsg).booleanValue() == true) {
                invalidate();
            }
        }
        catch (Exception e) {
            LiSongMechLab.showError(e);
        }
    }
}