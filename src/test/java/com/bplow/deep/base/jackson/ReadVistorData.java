package com.bplow.deep.base.jackson;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ReadVistorData {

    @Test
    public void read() throws JsonParseException, JsonMappingException, IOException {
        InputStream in = this.getClass().getResourceAsStream("/vistor-json.txt");

        ObjectMapper mapper = new ObjectMapper();
        mapper.enableDefaultTyping();
        Message massage = mapper.readValue(in, Message.class);
        
        System.out.println(massage.getMessage());
    }

}
