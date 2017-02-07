/*
 * Copyright 2014 Agostino Sturaro.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sandbox;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Agostino Sturaro
 */
public class InboxImpl extends UnicastRemoteObject implements Inbox {

    public static final String MSG_IN_PROPERTY = "ReceivedMessage";

    // useful class for firing custom events
    private final PropertyChangeSupport changeSupport;

    protected InboxImpl() throws RemoteException {
        super();
        changeSupport = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

    @Override
    public void receiveMessage(String message) throws RemoteException {
        changeSupport.firePropertyChange(MSG_IN_PROPERTY, null, message);
    }

}
