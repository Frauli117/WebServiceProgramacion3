/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoP3.webservice.socket;

import java.io.*;
import java.net.Socket;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
/**
 *
 * @author Kenneth
 */
public class AvailabilityClient {
    private final String host;
    private final int port;
    private final int timeoutSec;

    public AvailabilityClient(String host, int port, int timeoutSec) {
        this.host = host;
        this.port = port;
        this.timeoutSec = timeoutSec;
    }

    public List<String> consultar(long medicoId, LocalDate fecha, long servicioId) {
        try (Socket s = new Socket(host, port)) {
            s.setSoTimeout(Math.max(1, timeoutSec) * 1000);
            try (
                var in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                var out = new PrintWriter(new OutputStreamWriter(s.getOutputStream()), true)) {

                in.readLine();
                out.println("AVAIL medicoId=" + medicoId + " fecha=" + fecha + " servicioId=" + servicioId);

                String resp = in.readLine();
                if (resp == null || resp.startsWith("ERR") || resp.equalsIgnoreCase("EMPTY")) {
                    return Collections.emptyList();
                }
                return Arrays.asList(resp.split(","));
            }
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }
}

