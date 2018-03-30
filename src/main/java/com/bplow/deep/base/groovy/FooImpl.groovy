package com.bplow.deep.base.groovy

class FooImpl implements Foo{

    @Override
    public void doIt() {
        print("ok111111111111111111111111");
    }

    @Override
    public String execute(String scriptCxt) {
        
        def process = "cmd /c dir".execute()
        
        process.in.eachLine { line ->
            println line
        }
        
        return null;
    }

}
