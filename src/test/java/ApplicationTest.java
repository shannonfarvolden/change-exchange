import io.javalin.Javalin;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ApplicationTest {

    int port = 8000;
    String routeBase = "http://localhost:" + port;
    CloseableHttpResponse httpResponse;
    CloseableHttpClient httpclient;
    Javalin app;

    @Before
    public void setup(){
        app = Javalin.create().start(port);
        Application.routeSetup(app);
        httpclient = HttpClients.createDefault();
    }

    @Test
    public void successfulPostTransferRequest() throws IOException {

        HttpPost httpPost = new HttpPost(routeBase+"/users/transfer");
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("fromUserId", "0"));
        nvps.add(new BasicNameValuePair("toUserId", "1"));
        nvps.add(new BasicNameValuePair("amount", "50"));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps));

        httpResponse = httpclient.execute(httpPost);

        assertEquals("Transfer Complete. Michael Scott sent Dwight Schrute amount of 50. Dwight Schrute new balance is 10050.", EntityUtils.toString(httpResponse.getEntity()));
    }

    @Test
    public void correctContentType() throws IOException {

        HttpPost httpPost = new HttpPost(routeBase+"/users/transfer");
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("fromUserId", "0"));
        nvps.add(new BasicNameValuePair("toUserId", "1"));
        nvps.add(new BasicNameValuePair("amount", "50"));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps));

        httpResponse = httpclient.execute(httpPost);

        assertEquals("text/html", ContentType.getOrDefault(httpResponse.getEntity()).getMimeType());
    }

    @Test
    public void okStatusTransfer() throws IOException {

        HttpPost httpPost = new HttpPost(routeBase+"/users/transfer");
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("fromUserId", "0"));
        nvps.add(new BasicNameValuePair("toUserId", "0"));
        nvps.add(new BasicNameValuePair("amount", "0"));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps));

        httpResponse = httpclient.execute(httpPost);

        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void notFoundStatusTransfer() throws IOException {

        HttpPost httpPost = new HttpPost(routeBase+"/users/transfer");
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("id", "0"));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps));

        httpResponse = httpclient.execute(httpPost);

        assertEquals(HttpStatus.SC_NOT_FOUND, httpResponse.getStatusLine().getStatusCode());
    }


    @After
    public void tearDown() throws IOException{
        httpResponse.close();
        app.stop();
    }
}
