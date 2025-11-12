/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoP3.webservice.socket;

import proyectoP3.webservice.service.DisponibilidadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 *
 * @author Kenneth
 */
@Component
public class AvailabilityServer implements SmartLifecycle {

    private final DisponibilidadService disponibilidadService;

    @Value("${socket.availability.port:9090}")
    private int port;

    @Value("${socket.availability.bind:0.0.0.0}")
    private String bindAddr;

    @Value("${socket.availability.poolSize:16}")
    private int poolSize;

    @Value("${socket.availability.clientIdleTimeoutSec:60}")
    private int clientIdleTimeoutSec;

    private volatile boolean running = false;
    private ServerSocket serverSocket;
    private ExecutorService pool;
    private Thread acceptThread;

    public AvailabilityServer(DisponibilidadService disponibilidadService) {
        this.disponibilidadService = disponibilidadService;
    }

    @Override
    public void start() {
        if (running) return;
        try {
            pool = Executors.newFixedThreadPool(Math.max(2, poolSize));
            serverSocket = new ServerSocket(port, 50, InetAddress.getByName(bindAddr));
        } catch (IOException e) {
            throw new RuntimeException("No se pudo iniciar el servidor de sockets en " + bindAddr + ":" + port, e);
        }

        acceptThread = new Thread(this::acceptLoop, "socket-availability-accept");
        acceptThread.setDaemon(true);
        acceptThread.start();
        running = true;
        System.out.println("[AvailabilityServer] Escuchando en " + bindAddr + ":" + port);
    }

    private void acceptLoop() {
        while (!serverSocket.isClosed()) {
            try {
                Socket client = serverSocket.accept();
                pool.submit(() -> handleClient(client));
            } catch (IOException e) {
                if (!serverSocket.isClosed()) {
                    System.err.println("[AvailabilityServer] Error en acceptar: " + e.getMessage());
                }
            }
        }
    }

    private void handleClient(Socket s) {
        try (s) {
            s.setSoTimeout(Math.max(1, clientIdleTimeoutSec) * 1000);
            try (var in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                 var out = new PrintWriter(new OutputStreamWriter(s.getOutputStream()), true)) {
                out.println("OK AvailabilityServer READY (HELP para ayuda)");
                String line;
                while ((line = in.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty()) continue;

                    String upper = line.toUpperCase(Locale.ROOT);

                    if ("PING".equals(upper)) {
                        out.println("PONG");
                        continue;
                    }
                    if ("HELP".equals(upper)) {
                        out.println("CMDS: PING | AVAIL medicoId=<id> fecha=<YYYY-MM-DD> servicioId=<id> | QUIT");
                        continue;
                    }
                    if ("QUIT".equals(upper)) {
                        out.println("BYE");
                        break;
                    }
                    if (upper.startsWith("AVAIL")) {
                        out.println(handleAvail(line));
                        continue;
                    }

                    out.println("ERR Comando no válido. Use HELP.");
                }
            }
        } catch (IOException ioe) {
         
        } catch (Exception ex) {
            System.err.println("[AvailabilityServer] Error cliente: " + ex.getMessage());
        }
    }

    private String handleAvail(String line) {
        try {
            String[] toks = line.split("\\s+");
            Map<String, String> args = parseArgs(toks, 1);

            Long medicoId = requireLong(args, "medicoId");
            String fechaStr = require(args, "fecha");
            Long servicioId = requireLong(args, "servicioId");

            LocalDate fecha = LocalDate.parse(fechaStr);

            var slots = disponibilidadService.obtenerSlots(medicoId, fecha, servicioId);
            if (slots == null || slots.isEmpty()) return "EMPTY";
            return String.join(",", slots);
        } catch (IllegalArgumentException iae) {
            return "ERR " + iae.getMessage();
        } catch (Exception e) {
            return "ERR Error procesando AVAIL";
        }
    }

    private Map<String, String> parseArgs(String[] tokens, int fromIdx) {
        Map<String, String> map = new HashMap<>();
        for (int i = fromIdx; i < tokens.length; i++) {
            String t = tokens[i].trim();
            int eq = t.indexOf('=');
            if (eq > 0 && eq < t.length() - 1) {
                String k = t.substring(0, eq).trim();
                String v = t.substring(eq + 1).trim();
                map.put(k, v);
            }
        }
        return map;
    }

    private String require(Map<String, String> m, String key) {
        String v = m.get(key);
        if (v == null || v.isBlank())
            throw new IllegalArgumentException("Falta parámetro: " + key);
        return v;
    }

    private Long requireLong(Map<String, String> m, String key) {
        try {
            return Long.parseLong(require(m, key));
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("Parámetro inválido " + key + " (se espera numérico)");
        }
    }

    @Override
    public void stop() {
        running = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) serverSocket.close();
        } catch (IOException ignored) {}
        if (pool != null) pool.shutdownNow();
        System.out.println("[AvailabilityServer] Detenido");
    }

    @Override
    public boolean isRunning() {
        return running;
    }
}

