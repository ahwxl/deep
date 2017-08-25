package com.bplow.deep;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import com.bplow.deep.base.patchca.ValidateCode;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
    	BigDecimal num = new BigDecimal("-0.156").abs();
    	System.out.println(num.doubleValue());
        assertTrue( true );
        
        ValidateCode vCode = new ValidateCode(120,40,5,100);
        try {
            String path="D:/log/"+new Date().getTime()+".png";
            System.out.println(vCode.getCode()+" >"+path);
            vCode.write(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void testChar(){
        
        String txt = "ABcDeFF";
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while(i < txt.length()){
            char tmp = txt.charAt(i);
            if(i == 0){
                sb.append(tmp);
            }else{
                if(Character.isUpperCase(tmp)){
                    sb.append("_").append(Character.toLowerCase(tmp));
                }else{
                    sb.append(tmp);
                }
            }
            i++;
        }
        
        System.out.println(sb.toString());
    }
    
}
