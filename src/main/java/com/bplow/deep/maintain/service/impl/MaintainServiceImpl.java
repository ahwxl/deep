package com.bplow.deep.maintain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bplow.deep.maintain.service.Command;
import com.bplow.deep.maintain.service.MaintainService;

@Service
public class MaintainServiceImpl implements MaintainService {

    //@Autowired
    //@Qualifier("deployCommand")
    private Command deploy;

   // @Autowired
    //@Qualifier("startCommand")
    private Command start;

    //@Autowired
    //@Qualifier("stopCommand")
    private Command stop;

    //@Autowired
    //@Qualifier("restartCommand")
    private Command restart;

    @Override
    public String deploy() {

        String out = deploy.execute();

        return out;
    }

    @Override
    public String start() {

        String out = start.execute();

        return out;
    }

    @Override
    public String stop() {

        String out = stop.execute();

        return out;
    }

    @Override
    public String restart() {
        
        String out = restart.execute();
        
        return out;
    }

}
