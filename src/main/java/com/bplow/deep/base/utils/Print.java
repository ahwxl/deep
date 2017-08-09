package com.bplow.deep.base.utils;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;

public class Print {

    void print() throws PrintException {
        DocFlavor psInFormat = DocFlavor.INPUT_STREAM.GIF;

        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
        aset.add(new Copies(1));// 打印份数，1份

        //指定打印输出格式
        DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PRINTABLE;

        PrintService[] services = PrintServiceLookup.lookupPrintServices(psInFormat, aset);

        DocAttributeSet das = new HashDocAttributeSet();
        //指定打印内容
        Doc doc = new SimpleDoc(this, flavor, das);

        for (int i = 0; i < services.length; i++) {

            System.out.println("service found: " + services[i]);

            if ("Fax".equals(services[i].getName())) {
                System.out.println("111");
                PrintService myPrinter = services[i];
                DocPrintJob job = myPrinter.createPrintJob();
                job.print(doc, aset);
            }

        }

    }
    
    public static void main(String[] args) {
        Print p = new Print();
        try {
            p.print();
        } catch (PrintException e) {
            e.printStackTrace();
            //logger.error("", e);
        }
    }
    

}
