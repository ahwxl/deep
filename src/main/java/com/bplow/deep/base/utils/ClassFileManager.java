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
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.webapp.WebAppClassLoader;

import com.bplow.deep.base.classload.DynamicClassLoader;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

@SuppressWarnings("rawtypes")
public class ClassFileManager extends ForwardingJavaFileManager {

    private JavaClassFileObject classFileObject;
    
    String rootPath ="d:\\logs\\";
    
    private WebAppClassLoader classLoader;

    /**
     * Creates a new instance of ForwardingJavaFileManager.
     *
     * @param fileManager delegate to this file manager
     */
    protected ClassFileManager(JavaFileManager fileManager) {
        super(fileManager);
    }
    

    public ClassFileManager(JavaFileManager fileManager, WebAppClassLoader classLoader,String rootPath) {
        super(fileManager);
        this.classLoader = classLoader;
        if(StringUtils.isNotEmpty(rootPath)){
            this.rootPath = rootPath;
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
    
    public void writeFile() throws Exception{
    	
    	if(classFileObject == null){
    		return ;
    	}
    	/*rootPath = this.getClass().getResource("/").getPath();
    	if(rootPath.startsWith("/")){
    	    this.classLoader.addClassPath(rootPath.substring(1));
    	}else{
    	    this.classLoader.addClassPath(rootPath);
    	}*/
    	
    	System.out.println("编译之后类存放路径:"+rootPath);
    	
    	byte[] classBytes = classFileObject.getClassBytes();
    	ByteInputStream input = new ByteInputStream(classBytes, classBytes.length);
    	FileOutputStream output = null;
    	
    	
    	String absolutPath = rootPath+ File.separator + classFileObject.getName().substring(classFileObject.getName().lastIndexOf("/")+1);
    	/*String absoluteFolder = absolutPath.substring(0,absolutPath.lastIndexOf("/"));
    	
    	File folderPathFile = new File(absoluteFolder);
    	
    	if(!folderPathFile.exists()){
    	    folderPathFile.mkdir();
        }*/
    	
    	File targetClassFile = new File(absolutPath);
    	
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
				    output.flush();
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
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
        /*return new ClassLoader() {
            @Override
            protected Class<?> findClass(String name) throws ClassNotFoundException {
            	
                byte[] classBytes = classFileObject.getClassBytes();
                return super.defineClass(name, classBytes, 0, classBytes.length);
            }
        };*/
    	return this.classLoader;
    }
    
}
