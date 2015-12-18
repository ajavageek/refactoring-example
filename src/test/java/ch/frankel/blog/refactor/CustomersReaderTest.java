package ch.frankel.blog.refactor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
public class CustomersReaderTest {

    @Mock private ConfigurationService configurationService;
    @Mock private CloseableHttpClient client;
    @Mock private CloseableHttpResponse response;
    @Mock private HttpEntity entity;

    private CustomersReader customersReader;

    @Before
    public void setUp() {
        customersReader = new CustomersReader(configurationService);
    }

    @Test
    @PrepareForTest(HttpClients.class)
    public void should_return_json() throws IOException {
        when(configurationService.getCustomersUrl()).thenReturn("crap://test");
        when(HttpClients.createDefault()).thenReturn(client);
        when(client.execute(any(HttpUriRequest.class))).thenReturn(response);
        when(response.getEntity()).thenReturn(entity);
        InputStream stream = new ByteArrayInputStream("{ \"hello\" : \"world\" }".getBytes());
        when(entity.getContent()).thenReturn(stream);
        JSONObject json = customersReader.read();
        assertThat(json.has("hello"));
        assertThat(json.get("hello")).isEqualTo("world");
    }
}
