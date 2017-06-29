package com.bplow.deep.base.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;

import org.apache.commons.io.IOUtils;

import com.bplow.deep.base.classload.DynamicClassLoader;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

public class ClassFileManager extends ForwardingJavaFileManager {

    private JavaClassFileObject classFileObject;
    
    private String rootPath ="C:\\log";
    
    public static DynamicClassLoader classLoader = new DynamicClassLoader();

    /**
     * Creates a new instance of ForwardingJavaFileManager.
     *
     * @param fileManager delegate to this file manager
     */
    protected ClassFileManager(JavaFileManager fileManager) {
        super(fileManager);
        try {
			this.classLoader.addClassPath("C:\\log");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    /**
     * Gets a JavaFileObject file object for output
     * representing the specified class of the specified kind in the given location.
     */
    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className,
                                               JavaFileObject.Kind kind, FileObject sibling)
                                                                                            throws IOException {
        classFileObject = new JavaClassFileObject(className, kind);
        return classFileObject;
    }
    
    public void writeFile() throws IOException{
    	
    	if(classFileObject == null){
    		return ;
    	}
    	
    	byte[] classBytes = classFileObject.getClassBytes();
    	ByteInputStream input = new ByteInputStream(classBytes, classBytes.length);
    	FileOutputStream output = null;
    	
    	File targetClassFile = new File(rootPath + classFileObject.getName());
    	
    	try {
			output = new FileOutputStream(targetClassFile);
			
			IOUtils.copy(input, output);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(output != null){
					output.close();
					input.close();
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
    	
    }

    @Override
    //获得一个定制ClassLoader，返回我们保存在内存的类
    public ClassLoader getClassLoader(Location location) {
    	
    	try {
			this.writeFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
        /*return new ClassLoader() {
            @Override
            protected Class<?> findClass(String name) throws ClassNotFoundException {
            	
            	if(!name.equals("MyPrinter2")){
                    return super.loadClass(name);
            	}
            	
                byte[] classBytes = classFileObject.getClassBytes();
                return super.defineClass(name, classBytes, 0, classBytes.length);
            }
        };*/
    	return this.classLoader;
    }
    
}
