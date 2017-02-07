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

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Agostino Sturaro
 */
public class Client {

    private static final Logger logger = Logger.getLogger(Client.class
            .getName());
    private Inbox inbox;

    public Client() {
        if (System.getSecurityManager() == null) {
            // a quick trick to load the policy file, check package name
            String myPolicy = Client.class.getClassLoader()
                    .getResource("sandbox/policy.txt").toString();
            System.out.println(myPolicy);
            System.setProperty("java.security.policy", myPolicy);
            System.setSecurityManager(new RMISecurityManager());
        }
    }

    public boolean connect() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", Server.PORT);
            try {
                inbox = (Inbox) registry.lookup(Server.INBOX_NAME);
            } catch (NotBoundException | AccessException ex) {
                logger.log(Level.SEVERE, null, ex);
                return false;
            }
            return true;
        } catch (RemoteException ex) {
            logger.log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean sendMessage(String message) {
        try {
            inbox.receiveMessage(message);
            return true;
        } catch (RemoteException ex) {
            logger.log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
