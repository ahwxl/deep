package com.bplow.deep.stock.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("warn")
public class WarnRuleController {
    
    public Logger logger = LoggerFactory.getLogger(this.getClass());

}
