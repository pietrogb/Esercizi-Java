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

import java.util.logging.Logger;

/**
 *
 * @author Agostino Sturaro
 */
public class Starter {

    private static final Logger logger = Logger.getLogger(Server.class
            .getName());

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        final Server server = new Server();
        final Client client = new Client();

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        // GUI initialization should always be done this way, it's a heavy task
        // that runs in a separate thread, the Event Dispathing Thread (EDT)
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ServerGUI(server).setVisible(true);
            }
        });

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientGUI(client).setVisible(true);
            }
        });
    }

}
