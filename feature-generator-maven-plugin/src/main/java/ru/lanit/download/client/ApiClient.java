package ru.lanit.download.client;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import static java.util.Objects.requireNonNull;

public class ApiClient {

    private final OkHttpClient okHttpClient;
    private final Request.Builder builder;

    public ApiClient() {
        builder = new Request.Builder();
        okHttpClient = new OkHttpClient();
    }

    public ApiClient setURI(URI uri) {
        builder.url(convertToUrl(uri));

        return this;
    }

    public ApiClient get() {
        builder.get();

        return this;
    }

    public String send() {
        Request request = builder.build();
        String responseBody = null;

        try (Response response = okHttpClient.newCall(request).execute()) {
            responseBody = requireNonNull(response.body()).string();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseBody;
    }

    private URL convertToUrl(URI uri) {
        URL url = null;
        try {
            url = uri.toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
}
