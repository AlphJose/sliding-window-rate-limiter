package com.rateLimiter.demo;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Main {
    static Timestamp current_time = new Timestamp(System.currentTimeMillis());
    static Timestamp trackerTS = null;
    static int total_requests = 0;
    static int index = 0;
    static ArrayList <RequestsLog> requestsLog_arr = new ArrayList <RequestsLog> ();
    static RequestsLog requests_log = null;

    static class RequestsLog {
        Timestamp timestamp;
        int requests_per_second;

        public RequestsLog(Timestamp timestamp) {
            this.timestamp = timestamp;
            this.requests_per_second = 1;
        }

        public void update() {
            this.requests_per_second++;
        }

        @Override
        public String toString() {
            return String.format(timestamp + " : " + requests_per_second);
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 2345;
        int limit = 100;
       
        System.out.println("Connecting to server at port : " + port);
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Connected at : " + current_time + "\nAccepting requests...");

        Socket socket = null;
        while (true) {
            try {
                //accept a connection
                socket = serverSocket.accept();

                current_time = new Timestamp(System.currentTimeMillis());

                //reset total_requests every minute
                long mins = 0;
                if (null == trackerTS) {
                    trackerTS = new Timestamp(System.currentTimeMillis());
                } 
                else {
                    mins = current_time.getTime() - trackerTS.getTime();
                    mins /= 1000;
                    mins /= 60;
                    if (mins > 0) {
                        //delete entries until trackerTS
                        //System.out.println("Removing elements 0 - " + (index - 1) + " of " + requestsLog_arr);
                        requestsLog_arr.subList(0, index).clear();
                        trackerTS = current_time;
                        index = 0;
                        total_requests = 0;
                    }
                }

                //add logs of requests
                if (requestsLog_arr.size() == 0) {
                    requests_log = new RequestsLog(current_time);
                    requestsLog_arr.add(requests_log);
                } 
                else if (total_requests < limit) {
                    requests_log = requestsLog_arr.get(requestsLog_arr.size() - 1);
                    mins = current_time.getTime() - requests_log.timestamp.getTime();
                    mins /= 1000; //requests within same second are grouped.

                    if (0 == mins) {
                        requests_log.update();
                    } 
                    else {
                        requests_log = new RequestsLog(current_time);
                        requestsLog_arr.add(requests_log);
                        index ++;
                    }
                }

                total_requests ++;

                //build response 
                OutputStream out = socket.getOutputStream();
                String html = null;
                String response = null;

                if (total_requests > limit) {
                    html = "Rate limited!\r\nRequests : " + total_requests;
                    response = "HTTP/1.1 429 Too Many Requests \r\n" + //Status line : HTTP version RESPONSE_CODE RESPONSE_MESSAGE
                        "Content-Length: " + html.getBytes().length + "\r\n\r\n" + // HEADER
                        html + "\r\n\r\n";
                } 
                else {
                    html = "Requests : " + total_requests;
                    response = "HTTP/1.1 200 OK \r\n" + //Status line : HTTP version RESPONSE_CODE RESPONSE_MESSAGE
                        "Content-Length: " + html.getBytes().length + "\r\n\r\n" + // HEADER
                        html + "\r\n\r\n";
                }
                out.write(response.getBytes("US-ASCII"));
                out.flush();

                //close a connection
                out.close();
                socket.close();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}