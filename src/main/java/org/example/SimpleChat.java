package org.example;

import jakarta.annotation.PostConstruct;
import org.jgroups.*;
import org.jgroups.util.Util;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service
public class SimpleChat implements Receiver {
    JChannel channel;

    private final Map<String, Long> messages = Collections.synchronizedMap(new HashMap<>());

    @PostConstruct
    private void start() throws Exception {
        channel=new JChannel().setReceiver(this);
        channel.connect("ChatCluster");
        channel.getState(null, 10000);

        System.out.println("Nodo unido al cluster. Estado sincronizado con " + messages.size() + " elementos.");
        System.out.println("Historial actual: " + messages);
    }


    @Override
    public void viewAccepted(View new_view) {
        System.out.println("** view: " + new_view);
    }

    @Override
    public void receive(Message msg) {
        synchronized(messages) {
            messages.put(msg.getObject(),  System.currentTimeMillis());
            System.out.println("Mensaje recibido: " + msg.getObject());
        }
    }

    @Override
    public void getState(OutputStream output) throws Exception {
        synchronized (messages) {
            Util.objectToStream(messages, new DataOutputStream(output));
        }
    }

    @Override
    public void setState(InputStream input) throws Exception {
        Map<String, Long> state = (Map<String, Long>) Util.objectFromStream(new DataInputStream(input));
        synchronized (messages) {
            messages.clear();
            messages.putAll(state);
        }
        System.out.println("Estado actualizado desde otro nodo. Total: " + messages.size());
    }

    public static void main(String[] args) throws Exception {
        new SimpleChat().start();
    }
}
