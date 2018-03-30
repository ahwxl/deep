package com.bplow.deep.maintain.service;

public interface MaintainService {
    
    String deploy();
    
    String start();
    
    String stop();

    String restart();
    
}
