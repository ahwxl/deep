package com.bplow.deep.authority;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class MyHostnameVerifier implements HostnameVerifier{

    @Override
    public boolean verify(String s, SSLSession sslsession) {
        return true;
    }
}
