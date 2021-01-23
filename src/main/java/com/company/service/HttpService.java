package com.company.service;

import static org.apache.commons.codec.CharEncoding.UTF_8;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.yaml.snakeyaml.Yaml;
import com.company.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class HttpService {

    // Get mapping
    public void externalCall() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {

            HttpGet request = new HttpGet("https://httpbin.org/get");

            // add request headers
            request.addHeader("custom-key", "mkyong");
            request.addHeader(HttpHeaders.USER_AGENT, "Googlebot");

            CloseableHttpResponse response = httpClient.execute(request);

            try {

                // Get HttpResponse Status
                System.out.println(response.getProtocolVersion());              // HTTP/1.1
                System.out.println(response.getStatusLine().getStatusCode());   // 200
                System.out.println(response.getStatusLine().getReasonPhrase()); // OK
                System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK

                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    // return it as a String
                    String result = EntityUtils.toString(entity);
                    System.out.println(result);
                }

            } finally {
                response.close();
            }
        } finally {
            httpClient.close();
        }
    }

    // Simple Get mapping
    public void internalGetCall() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpGet request = new HttpGet("http://localhost:9910/v1/library/welcome");
            CloseableHttpResponse response = httpClient.execute(request);

            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    // return it as a String
                    String result = EntityUtils.toString(entity);
                    System.out.println(result);
                }
            } finally {
                response.close();
            }
        } finally {
            httpClient.close();
        }
    }

    // Git fetch config
    public void gitYamlGetCall() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpGet request = new HttpGet("http://localhost:8888/karthikbhat003/yaml-file/develop/user.yaml");
            CloseableHttpResponse response = httpClient.execute(request);

            try {
                HttpEntity entity = response.getEntity();
                final InputStreamReader inputStreamReader = new InputStreamReader(entity.getContent());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder sb = new StringBuilder();
                String str;
                while((str = bufferedReader.readLine())!= null){
                    sb.append(str);
                    sb.append("\n");
                }
                System.out.println(sb.toString());
                ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
                User emp = mapper.readValue(sb.toString(), User.class);
                System.out.println("Received the config... Printing...!");
                System.out.println(emp.toString());
            } finally {
                response.close();
            }
        } finally {
            httpClient.close();
        }
    }

    // Post Mapping
    public void getUser() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        final ObjectMapper objectMapper = new ObjectMapper();
        final User user = new User("karthik bhat this is", "003", null);
        final String jsonBody = objectMapper.writeValueAsString(user);
        try {
            HttpPost post = new HttpPost("http://localhost:9910/v1/library/user");
            post.setEntity(new StringEntity(jsonBody));
            post.setHeader("Content-type", "application/json");
            CloseableHttpResponse response = httpClient.execute(post);
            try {
                HttpEntity entity = response.getEntity();
                final User user1 = objectMapper.readValue(EntityUtils.toString(entity), User.class);
                if(user1!= null){
                    System.out.println("To string of the entity : " + user1.toString());
                    System.out.println("Mapped the entity..!! Printing the values");
                    System.out.println("Name: "+ user1.getName());
                    System.out.println("Id: "+ user1.getId());
                }
            } finally {
                response.close();
            }
        } finally {
            httpClient.close();
        }
    }
}
