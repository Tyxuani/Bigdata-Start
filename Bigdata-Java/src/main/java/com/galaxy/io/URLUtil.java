package com.galaxy.io;

import java.net.URI;
import java.net.URISyntaxException;

public class URLUtil {
    public static void main(String[] args) throws URISyntaxException {
        URI uri = new URI("http://192.8.0.14:3660/as/pk");
        System.out.println(uri.getScheme());
        System.out.println(uri.getAuthority());
        System.out.println(uri.getHost());
        System.out.println(uri.getPort());
        System.out.println(uri.getPath());
    }
}
