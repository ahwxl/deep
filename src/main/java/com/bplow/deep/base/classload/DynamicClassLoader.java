package com.bplow.deep.base.classload;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DynamicClassLoader extends URLClassLoader {
    
    private Logger          Log = LoggerFactory.getLogger(this.getClass());

    private String      _name;
    private ClassLoader _parent;
    
    private String[] _systemClasses = 
        {
            "java.",
            "javax.",
            "org.mortbay.",
            "org.xml.",
            "org.w3c.", 
            "org.apache.commons.logging.", 
            "org.apache.log4j."
        };

    public DynamicClassLoader() {
        this(null);
    }

    public DynamicClassLoader(ClassLoader parent) {
        super(new URL[] {}, parent != null ? parent : (Thread.currentThread()
            .getContextClassLoader() != null ? Thread.currentThread().getContextClassLoader()
            : (DynamicClassLoader.class.getClassLoader() != null ? DynamicClassLoader.class
                .getClassLoader() : ClassLoader.getSystemClassLoader())));
        _parent = getParent();
    }

    public void addClassPath(String classPath) throws Exception {
        if (classPath == null)
            return;

        StringTokenizer tokenizer = new StringTokenizer(classPath, ",;");
        while (tokenizer.hasMoreTokens()) {
            //Resource resource = Resource.newResource(tokenizer.nextToken());
            String tmpPath = tokenizer.nextToken();
            if (Log.isDebugEnabled())
                Log.debug("Path resource=" + ""/*resource*/);

            // Resolve file path if possible
            File file = new File(tmpPath)/*resource.getFile()*/;
            if (file != null) {
                URL url = createUrl(tmpPath);
                addURL(url);
            } else {
                // Add resource or expand jar/
                if (/*!resource.isDirectory()*/ true && file == null) {
                    InputStream in = new FileInputStream(file)/*resource.getInputStream()*/;
                    File tmp_dir = new File("");
                    if (tmp_dir == null) {
                        tmp_dir = File.createTempFile("jetty.cl.lib", null);
                        tmp_dir.mkdir();
                        tmp_dir.deleteOnExit();
                    }
                    File lib = new File(tmp_dir, "lib");
                    if (!lib.exists()) {
                        lib.mkdir();
                        lib.deleteOnExit();
                    }
                    File jar = File.createTempFile("Jetty-", ".jar", lib);

                    jar.deleteOnExit();
                    if (Log.isDebugEnabled())
                        Log.debug("Extract " + "" + " to " + jar);
                    FileOutputStream out = null;
                    try {
                        out = new FileOutputStream(jar);
                        //IO.copy(in, out);
                    } finally {
                        //IO.close(out);
                    }

                    URL url = jar.toURL();
                    addURL(url);
                } else {
                    //URL url = resource.getURL();
                    //addURL(url);
                }
            }
        }
    }
    
    public URL getResource(String name)
    {
        URL url= null;
        boolean tried_parent= false;
        if (isSystemPath(name))
        {
            tried_parent= true;
            
            if (_parent!=null)
                url= _parent.getResource(name);
        }

        if (url == null)
        {
            url= this.findResource(name);

            if (url == null && name.startsWith("/"))
            {
                if (Log.isDebugEnabled())
                    Log.debug("HACK leading / off " + name);
                url= this.findResource(name.substring(1));
            }
        }

        if (url == null && !tried_parent)
        {
            if (_parent!=null)
                url= _parent.getResource(name);
        }

        if (url != null)
            if (Log.isDebugEnabled())
                Log.debug("getResource("+name+")=" + url);

        return url;
    }
    
    public boolean isSystemPath(String name)
    {
        name=name.replace('/','.');
        while(name.startsWith("."))
            name=name.substring(1);
        String[] system_classes = _systemClasses;
        if (system_classes!=null)
        {
            for (int i=0;i<system_classes.length;i++)
            {
                boolean result=true;
                String c=system_classes[i];
                
                if (c.startsWith("-"))
                {
                    c=c.substring(1); // TODO cache
                    result=false;
                }
                
                if (c.endsWith("."))
                {
                    if (name.startsWith(c))
                        return result;
                }
                else if (name.equals(c))
                    return result;
            }
        }
        
        return false;
        
    }
    
    public URL createUrl(String resource) throws Exception{
        
        File file=new File(resource).getCanonicalFile();
        
        StringBuffer buf = encodePath(null,file.toURL().toString());
        String urlstr = buf==null?file.toURL().toString():buf.toString();
        
        URL url=new URL(urlstr);                    
        
        return url;
    }
    
    /** Encode a URI path.
     * @param path The path the encode
     * @param buf StringBuffer to encode path into (or null)
     * @return The StringBuffer or null if no substitutions required.
     */
    public static StringBuffer encodePath(StringBuffer buf, String path)
    {
        if (buf==null)
        {
        loop:
            for (int i=0;i<path.length();i++)
            {
                char c=path.charAt(i);
                switch(c)
                {
                    case '%':
                    case '?':
                    case ';':
                    case '#':
                    case '\'':
                    case '"':
                    case '<':
                    case '>':
                    case ' ':
                        buf=new StringBuffer(path.length()<<1);
                        break loop;
                }
            }
            if (buf==null)
                return null;
        }
        
        synchronized(buf)
        {
            for (int i=0;i<path.length();i++)
            {
                char c=path.charAt(i);       
                switch(c)
                {
                  case '%':
                      buf.append("%25");
                      continue;
                  case '?':
                      buf.append("%3F");
                      continue;
                  case ';':
                      buf.append("%3B");
                      continue;
                  case '#':
                      buf.append("%23");
                      continue;
                  case '"':
                      buf.append("%22");
                      continue;
                  case '\'':
                      buf.append("%27");
                      continue;
                  case '<':
                      buf.append("%3C");
                      continue;
                  case '>':
                      buf.append("%3E");
                      continue;
                  case ' ':
                      buf.append("%20");
                      continue;
                  default:
                      buf.append(c);
                      continue;
                }
            }
        }

        return buf;
    }
    
    public Class loadClass(String name) throws ClassNotFoundException
    {
        return loadClass(name, false);
    }

    /* ------------------------------------------------------------ */
    protected synchronized Class loadClass(String name, boolean resolve) throws ClassNotFoundException
    {
        Class c= findLoadedClass(name);
        ClassNotFoundException ex= null;
        boolean tried_parent= false;
        
        if (c == null && _parent!=null && (isSystemPath(name)) )
        {
            tried_parent= true;
            try
            {
                c= _parent.loadClass(name);
                if (Log.isDebugEnabled())
                    Log.debug("loaded " + c);
            }
            catch (ClassNotFoundException e)
            {
                ex= e;
            }
        }

        if (c == null)
        {
            try
            {
                c= this.findClass(name);
            }
            catch (ClassNotFoundException e)
            {
                ex= e;
            }
        }

        if (c == null && _parent!=null && !tried_parent )
            c= _parent.loadClass(name);

        if (c == null)
            throw ex;

        if (resolve)
            resolveClass(c);

        if (Log.isDebugEnabled())
            Log.debug("loaded " + c+ " from "+c.getClassLoader());
        
        return c;
    }
    
    public static void main(String[] args) throws Exception {
	
    	DynamicClassLoader cl = new DynamicClassLoader();
    	cl.addClassPath("C:\\log");
    	
    	try {
			Class beanClass = cl.loadClass("com.bplow.deep.base.classload.MyPrinter2");
			
			Method m = beanClass.getMethod("print", null);
	        m.invoke(beanClass.newInstance(), null);
			
			BeanInfo beanInfo = Introspector.getBeanInfo(beanClass, Introspector.IGNORE_ALL_BEANINFO);
	        
	        System.out.println(beanInfo);
	        
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    	
	}

}
