package net.hashsploit.mediusdiscordbot.util;

import java.util.List;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.URI;
import java.net.http.HttpRequest.BodyPublishers;
import java.time.Duration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.CompletableFuture;

public class HTTPRequestor{
    String uri;
    String body;
    List<String> headers;
    int timeout;
    
    public HTTPRequestor(String uri, String body, List<String> headers, int timeout){
        this.uri = uri;
        this.body = body;
        this.headers = headers;
        this.timeout = timeout;
    }

    public CompletableFuture<String> makeRequest(){
        HttpClient client = HttpClient.newBuilder()
        .build();
        
        HttpRequest req = HttpRequest.newBuilder()
        .uri(URI.create(this.uri))
        .timeout(Duration.ofMinutes(this.timeout))
        .POST(BodyPublishers.ofByteArray(body.getBytes()))
        .build();
        
        CompletableFuture<String> res = new CompletableFuture<String>();

        try{
            client.sendAsync(req, BodyHandlers.ofString())
            .thenAccept((HttpResponse serverRes) -> {
                if (serverRes.statusCode() != 200){
                    res.complete(null);
                } 
                res.complete(serverRes.body().toString());
            });
        } catch(Exception err){
            res.complete(null);
        }

        return res;
    }
}