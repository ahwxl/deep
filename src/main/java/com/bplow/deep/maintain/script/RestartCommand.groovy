package com.bplow.deep.maintain.script;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bplow.deep.maintain.service.Command;

public class RestartCommand implements Command {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public String execute() {
        logger.info("deploy...");
        def sout = new StringBuilder()

        def process = "cmd /c dir".execute()

        process.consumeProcessOutputStream(sout);

        process.in.eachLine { line -> println line }

        return sout.toString();
    }
}


