//package com.example.gandy;
//
//
//public class ReverseSSH {
//
//    private Session session;
//
//    public void createReverseTunnel(String sshHost, int sshPort, String sshUser, String sshPassword,
//                                    int remotePort, String localHost, int localPort) throws JSchException {
//        JSch jsch = new JSch();
//        session = jsch.getSession(sshUser, sshHost, sshPort);
//        session.setPassword(sshPassword);
//
//        // Configuring SSH session
//        java.util.Properties config = new java.util.Properties();
//        config.put("StrictHostKeyChecking", "no");
//        session.setConfig(config);
//
//        // Establishing connection
//        session.connect();
//
//        // Setting up the reverse tunnel
//        session.setPortForwardingR(remotePort, localHost, localPort);
//    }
//
//    public void closeConnection() {
//        if (session != null && session.isConnected()) {
//            session.disconnect();
//        }
//    }
//}