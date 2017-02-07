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
import java.rmi.NoSuchObjectException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Agostino Sturaro
 */
public class Server {

    public static final int PORT = 1099;
    public static final String INBOX_NAME = "inbox";

    private static final Logger logger = Logger.getLogger(Server.class
            .getName());
    private Registry registryObject;
    private Registry registryStub;
    private InboxImpl inbox;

    public Server() {
        if (System.getSecurityManager() == null) {
            // a quick trick to load the policy file, check package name
            String myPolicy = Server.class.getClassLoader()
                    .getResource("sandbox/policy.txt").toString();
            System.out.println(myPolicy);
            System.setProperty("java.security.policy", myPolicy);
            System.setSecurityManager(new RMISecurityManager());
        }
        try {
            inbox = new InboxImpl();
        } catch (RemoteException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    // this is used only by the server GUI, not from the client!
    // the client gets a remote reference through the rmi registry in order
    // to send messages to the server (see below)
    public InboxImpl getInbox() {
        return inbox;
    }

    public void activate() {
        try {
            // start the rmiregistry service on port PORT, keep this reference
            // to stop the service later
            registryObject = LocateRegistry.createRegistry(PORT);

            // this call returns a stub of the registry object, this can be used
            // to bind names, but not to stop the service
            registryStub = LocateRegistry.getRegistry("localhost", PORT);

            // bind the remote object's stub in the registry
            // the client will get the remote reference from this registry
            registryStub.rebind(INBOX_NAME, inbox);

            logger.log(Level.INFO, "Server on");
        } catch (AccessException ex) {
            logger.log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public void deactivate() {
        // stop the rmiregistry service
        if (registryObject != null) {
            try {
                UnicastRemoteObject.unexportObject(registryObject, true);
                registryObject = null;
            } catch (NoSuchObjectException ex) {
                logger.log(Level.WARNING, null, ex);
            }
        }
        logger.log(Level.INFO, "Server off");
    }

}
