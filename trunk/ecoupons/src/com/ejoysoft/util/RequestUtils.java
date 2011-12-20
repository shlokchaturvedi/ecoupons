package com.ejoysoft.util;

/**
 * �ļ���ƣ�RequestUtils.java
 * ģ�����:  ��װ������
 * @author feiwj
 * @version $Revision: 1.0 $ $Date: 2004-08-31 11:31 $
 */


import java.util.*;
import java.lang.reflect.*;
import java.beans.*;
import javax.servlet.ServletException;

import org.apache.struts.upload.MultipartRequestWrapper;
import org.apache.struts.upload.MultipartRequestHandler;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

import javax.servlet.http.*;


public class RequestUtils {

    public RequestUtils() {
    }


    /**
     *  Module function:  Encapsulate HttpServletRequest
     * @param bean
     * @param request
     * @throws javax.servlet.ServletException
     */
    public static void populate(Object bean, HttpServletRequest request)
            throws ServletException {
        populate(bean, null, null, request);
    }

    /**
     *  Module function: Encapsulate HttpServletRequest
     * @param bean
     * @param prefix
     * @param suffix
     * @param request
     * @throws javax.servlet.ServletException
     */
    public static void populate(Object bean, String prefix, String suffix, HttpServletRequest request)
            throws ServletException {
        HashMap properties = new HashMap();
        Enumeration names = null;
        Map multipartElements = null;
        boolean isMultipart = false;
        String contentType = request.getContentType();
        String method = request.getMethod();
        if (contentType != null && contentType.startsWith("multipart/form-data") && method.equalsIgnoreCase("POST")) {

            //populated   method
            //throw new IllegalArgumentException("  Bean is not  populated  multipart/form-data");
            MultipartRequestHandler multipartHandler = null;
            HttpServletRequest req=null;
            isMultipart = true;
            try {
                log.debug("processMultipart init ...");
                multipartHandler = (MultipartRequestHandler) applicationInstance(multipartClass);
                multipartHandler.handleRequest(request);
                // request.setAttribute("javax.servlet.context.fileMaxSize",10*1024*1024);
                Object temp = request.getAttribute(
                        MultipartRequestHandler.ATTRIBUTE_MAX_LENGTH_EXCEEDED);
                if (temp != null && ((Boolean) temp).booleanValue()) {
                    request.removeAttribute(
                            MultipartRequestHandler.ATTRIBUTE_MAX_LENGTH_EXCEEDED);

                    throw new ServletException("handling for uploads that are too big");
                }

                req=processMultipart(request);

                log.debug("processMultipart init sucessfully");
            } catch (ClassNotFoundException cnfe) {
                log.error(cnfe.toString());
            } catch (InstantiationException ie) {
                log.error(ie.toString());
            } catch (IllegalAccessException iae) {
                log.error(iae.toString());
            }

            multipartElements = getAllParametersForMultipartRequest(req, multipartHandler);
            request.setAttribute("javax.servlet.upload.parameter",multipartElements);
            names = Collections.enumeration(multipartElements.keySet());

        }
        if (!isMultipart)
            names = request.getParameterNames();

        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            String stripped = name;

            int subscript = stripped.lastIndexOf("[");
            if (prefix != null) {
                if (!stripped.startsWith(prefix))
                    continue;
                stripped = stripped.substring(prefix.length());



            }
            if (suffix != null) {
                if (!stripped.endsWith(suffix))
                    continue;
                stripped = stripped.substring(0, stripped.length() - suffix.length());
            }

            if (isMultipart) {
                properties.put(stripped, multipartElements.get(name));
            } else {
                properties.put(stripped, request.getParameterValues(name));
            }
        }
        try {
        	  org.apache.commons.beanutils.BeanUtils.populate(bean, properties);
        } catch (IllegalAccessException e) {
            throw new ServletException("RequestUtils.populate", e);
        } catch (InvocationTargetException e) {
            throw new ServletException("RequestUtils.populate", e);
        } catch (NoClassDefFoundError e) {
           try{
               populate(bean, properties);
           } catch (Exception io) {
               throw new ServletException("RequestUtils.populate", e);}
        }
    }

    private static void populate(Object bean, Map properties)
               throws IllegalAccessException, InvocationTargetException {
           if (bean == null || properties == null)
               return;
           for (Iterator names = properties.keySet().iterator(); names.hasNext();) {
               String name = (String) names.next();
               if (name != null) {
                   Object value = properties.get(name);
                   PropertyDescriptor descriptor = null;
                   try {
                       descriptor = getPropertyDescriptor(bean, name);
                   } catch (Throwable throwable) {
                       descriptor = null;
                   }
                   if (descriptor != null) {
                       Method setter = null;
                       if (descriptor instanceof IndexedPropertyDescriptor)
                           setter = ((IndexedPropertyDescriptor) descriptor).getIndexedWriteMethod();
                       if (setter == null)
                           setter = descriptor.getWriteMethod();
                       if (setter != null) {
                           Class parameterTypes[] = setter.getParameterTypes();
                           Class parameterType = parameterTypes[0];
                           if (parameterTypes.length > 1)
                               parameterType = parameterTypes[1];
                           Object parameters[] = new Object[1];
                           if (parameterTypes[0].isArray()) {
                               if (value instanceof String) {
                                   String values[] = new String[1];
                                   values[0] = (String) value;
                                   parameters[0] = ConvertUtils.convert((String[]) values, parameterType);
                               } else if (value instanceof String[])
                                   parameters[0] = ConvertUtils.convert((String[]) value, parameterType);
                               else
                                   parameters[0] = value;
                           } else if (value instanceof String)
                               parameters[0] = ConvertUtils.convert((String) value, parameterType);
                           else if (value instanceof String[])
                               parameters[0] = ConvertUtils.convert(((String[]) value)[0], parameterType);

                           else
                               parameters[0] = value;
                           try {

                               setProperty(bean, name, parameters[0]);
                           } catch (NoSuchMethodException nosuchmethodexception) {
                           }
                       }
                   }
               }
           }

       }


    public static Object getIndexedProperty(Object bean, String name)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (bean == null)
            throw new IllegalArgumentException("No bean specified");
        if (name == null)
            throw new IllegalArgumentException("No name specified");
        int delim = name.indexOf(91);
        int delim2 = name.indexOf(93);
        if (delim < 0 || delim2 <= delim)
            throw new IllegalArgumentException("Invalid indexed property '" + name + "'");
        int index = -1;
        try {
            String subscript = name.substring(delim + 1, delim2);
            index = Integer.parseInt(subscript);
        } catch (NumberFormatException numberformatexception) {
            throw new IllegalArgumentException("Invalid indexed property '" + name + "'");
        }
        name = name.substring(0, delim);
        return getIndexedProperty(bean, name, index);
    }

    public static Object getIndexedProperty(Object bean, String name, int index)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (bean == null)
            throw new IllegalArgumentException("No bean specified");
        if (name == null)
            throw new IllegalArgumentException("No name specified");
        PropertyDescriptor descriptor = getPropertyDescriptor(bean, name);
        if (descriptor == null)
            throw new NoSuchMethodException("Unknown property '" + name + "'");
        Method readMethod;
        if (descriptor instanceof IndexedPropertyDescriptor) {
            readMethod = ((IndexedPropertyDescriptor) descriptor).getIndexedReadMethod();
            if (readMethod != null) {
                Object subscript[] = new Object[1];
                subscript[0] = new Integer(index);
                return readMethod.invoke(bean, subscript);
            }
        }
        readMethod = getReadMethod(descriptor);
        if (readMethod == null)
            throw new NoSuchMethodException("Property '" + name + "' has no getter method");
        Object value = readMethod.invoke(bean, new Object[0]);
        if (!value.getClass().isArray())
            throw new IllegalArgumentException("Property '" + name + "' is not indexed");
        else
            return Array.get(value, index);
    }

    public static Object getNestedProperty(Object bean, String name)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (bean == null)
            throw new IllegalArgumentException("No bean specified");
        if (name == null)
            throw new IllegalArgumentException("No name specified");
        do {
            int delim = name.indexOf(46);
            if (delim < 0)
                break;
            String next = name.substring(0, delim);
            if (next.indexOf(91) >= 0)
                bean = getIndexedProperty(bean, next);
            else
                bean = getSimpleProperty(bean, next);
            if (bean == null)
                throw new IllegalArgumentException("Null property value for '" + name.substring(0, delim) + "'");
            name = name.substring(delim + 1);
        } while (true);
        if (name.indexOf(91) >= 0)
            return getIndexedProperty(bean, name);
        else
            return getSimpleProperty(bean, name);
    }

    public static Object getProperty(Object bean, String name)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return getNestedProperty(bean, name);
    }

    private static PropertyDescriptor getPropertyDescriptor(Object bean, String name)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (bean == null)
            throw new IllegalArgumentException("No bean specified");
        if (name == null)
            throw new IllegalArgumentException("No name specified");
        do {
            int period = name.indexOf(46);
            if (period < 0)
                break;
            String next = name.substring(0, period);
            if (next.indexOf(91) >= 0)
                bean = getIndexedProperty(bean, next);
            else
                bean = getSimpleProperty(bean, next);
            if (bean == null)
                throw new IllegalArgumentException("Null property value for '" + name.substring(0, period) + "'");
            name = name.substring(period + 1);
        } while (true);
        int left = name.indexOf(91);
        if (left >= 0)
            name = name.substring(0, left);
        if (bean == null || name == null)
            return null;

        PropertyDescriptor descriptors[] = getPropertyDescriptors(bean);

        if (descriptors == null)
            return null;
        for (int i = 0; i < descriptors.length; i++)
            if (name.equals(descriptors[i].getName()))
                return descriptors[i];

        return null;
    }

    private static PropertyDescriptor[] getPropertyDescriptors(Object bean) {
        if (bean == null)
            throw new IllegalArgumentException("No bean specified");

        String beanClassName = bean.getClass().getName();

        PropertyDescriptor descriptors[] = null;

        BeanInfo beanInfo = null;
        try {
            beanInfo = Introspector.getBeanInfo(bean.getClass());
        } catch (IntrospectionException introspectionexception) {
            return new PropertyDescriptor[0];
        }
        descriptors = beanInfo.getPropertyDescriptors();
        if (descriptors == null)
            descriptors = new PropertyDescriptor[0];

        return descriptors;
    }


    private static Method getReadMethod(PropertyDescriptor descriptor) {
        return getAccessibleMethod(descriptor.getReadMethod());
    }


    public static Object getSimpleProperty(Object bean, String name)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (bean == null)
            throw new IllegalArgumentException("No bean specified");
        if (name == null)
            throw new IllegalArgumentException("No name specified");
        if (name.indexOf(46) >= 0)
            throw new IllegalArgumentException("Nested property names are not allowed");
        if (name.indexOf(91) >= 0)
            throw new IllegalArgumentException("Indexed property names are not allowed");
        PropertyDescriptor descriptor = getPropertyDescriptor(bean, name);
        if (descriptor == null)
            throw new NoSuchMethodException("Unknown property '" + name + "'");
        Method readMethod = getReadMethod(descriptor);
        if (readMethod == null) {
            throw new NoSuchMethodException("Property '" + name + "' has no getter method");
        } else {
            Object value = readMethod.invoke(bean, new Object[0]);
            return value;
        }
    }


    private static Method getWriteMethod(PropertyDescriptor descriptor) {
        return getAccessibleMethod(descriptor.getWriteMethod());
    }

    public static void setIndexedProperty(Object bean, String name, Object value)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (bean == null)
            throw new IllegalArgumentException("No bean specified");
        if (name == null)
            throw new IllegalArgumentException("No name specified");
        int delim = name.indexOf(91);
        int delim2 = name.indexOf(93);
        if (delim < 0 || delim2 <= delim)
            throw new IllegalArgumentException("Invalid indexed property '" + name + "'");
        int index = -1;
        try {
            String subscript = name.substring(delim + 1, delim2);
            index = Integer.parseInt(subscript);
        } catch (NumberFormatException numberformatexception) {
            throw new IllegalArgumentException("Invalid indexed property '" + name + "'");
        }
        name = name.substring(0, delim);
        setIndexedProperty(bean, name, index, value);
    }

    public static void setIndexedProperty(Object bean, String name, int index, Object value)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (bean == null)
            throw new IllegalArgumentException("No bean specified");
        if (name == null)
            throw new IllegalArgumentException("No name specified");
        PropertyDescriptor descriptor = getPropertyDescriptor(bean, name);
        if (descriptor == null)
            throw new NoSuchMethodException("Unknown property '" + name + "'");
        if (descriptor instanceof IndexedPropertyDescriptor) {
            Method writeMethod = ((IndexedPropertyDescriptor) descriptor).getIndexedWriteMethod();
            if (writeMethod != null) {
                Object subscript[] = new Object[2];
                subscript[0] = new Integer(index);
                subscript[1] = value;
                writeMethod.invoke(bean, subscript);
                return;
            }
        }
        Method readMethod = descriptor.getReadMethod();
        if (readMethod == null)
            throw new NoSuchMethodException("Property '" + name + "' has no getter method");
        Object array = readMethod.invoke(bean, new Object[0]);
        if (!array.getClass().isArray()) {
            throw new IllegalArgumentException("Property '" + name + "' is not indexed");
        } else {
            Array.set(array, index, value);
            return;
        }
    }

    public static void setNestedProperty(Object bean, String name, Object value)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (bean == null)
            throw new IllegalArgumentException("No bean specified");
        if (name == null)
            throw new IllegalArgumentException("No name specified");
        do {
            int delim = name.indexOf(46);
            if (delim < 0)
                break;
            String next = name.substring(0, delim);

            if (next.indexOf(91) >= 0)
                bean = getIndexedProperty(bean, next);
            else
                bean = getSimpleProperty(bean, next);
            if (bean == null)
                throw new IllegalArgumentException("Null property value for '" + name.substring(0, delim) + "'");
            name = name.substring(delim + 1);
        } while (true);
        if (name.indexOf(91) >= 0)
            setIndexedProperty(bean, name, value);
        else
            setSimpleProperty(bean, name, value);
    }

    public static void setProperty(Object bean, String name, Object value)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        setNestedProperty(bean, name, value);
    }

    public static void setSimpleProperty(Object bean, String name, Object value)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {


        if (bean == null)
            throw new IllegalArgumentException("No bean specified");
        if (name == null)
            throw new IllegalArgumentException("No name specified");
        if (name.indexOf(46) >= 0)
            throw new IllegalArgumentException("Nested property names are not allowed");
        if (name.indexOf(91) >= 0)
            throw new IllegalArgumentException("Indexed property names are not allowed");

        PropertyDescriptor descriptor = getPropertyDescriptor(bean, name);


        if (descriptor == null)
            throw new NoSuchMethodException("Unknown property '" + name + "'");


        Method writeMethod = getWriteMethod(descriptor);
        if (writeMethod == null) {
            throw new NoSuchMethodException("Property '" + name + "' has no setter method");
        } else {


            Object values[] = new Object[1];
            values[0] = value;
            writeMethod.invoke(bean, values);

            return;
        }
    }

    private static Method getAccessibleMethod(Method method) {
        if (method == null)
            return null;
        if (!Modifier.isPublic(method.getModifiers()))
            return null;
        Class clazz = method.getDeclaringClass();
        if (Modifier.isPublic(clazz.getModifiers())) {
            return method;
        } else {
            String methodName = method.getName();
            Class parameterTypes[] = method.getParameterTypes();
            method = getAccessibleMethodFromInterfaceNest(clazz, method.getName(), method.getParameterTypes());
            return method;
        }
    }

    private static Method getAccessibleMethodFromInterfaceNest(Class clazz, String methodName, Class parameterTypes[]) {
        Method method = null;
        Class interfaces[] = clazz.getInterfaces();
        for (int i = 0; i < interfaces.length; i++) {
            if (!Modifier.isPublic(interfaces[i].getModifiers()))
                continue;
            try {
                method = interfaces[i].getDeclaredMethod(methodName, parameterTypes);
            } catch (NoSuchMethodException nosuchmethodexception) {
            }
            if (method != null)
                break;
            method = getAccessibleMethodFromInterfaceNest(interfaces[i], methodName, parameterTypes);
            if (method != null)
                break;
        }

        return method;
    }

    protected static HttpServletRequest processMultipart(HttpServletRequest request) {
        if (!"POST".equalsIgnoreCase(request.getMethod()))
            return request;
        String contentType = request.getContentType();
        if (contentType != null && contentType.startsWith("multipart/form-data"))
            return new MultipartRequestWrapper(request);
        else
            return request;
    }
      protected static String multipartClass =
            "org.apache.struts.upload.CommonsMultipartRequestHandler";

    /**
     * Create a map containing all of the parameters supplied for a multipart
     * request, keyed by parameter name. In addition to text and file elements
     * from the multipart body, query string parameters are included as well.
     *
     * @param req The (wrapped) HTTP request whose parameters are to be
     *                added to the map.
     * @param multipartHandler The multipart handler used to parse the request.
     *
     * @return the map containing all parameters for this multipart request.
     */
    private static Map getAllParametersForMultipartRequest(
            HttpServletRequest req,
            MultipartRequestHandler multipartHandler) {

        Map parameters = new HashMap();
        Enumeration enum0;

        Hashtable elements = multipartHandler.getAllElements();
        enum0 = elements.keys();

        while (enum0.hasMoreElements()) {
            String key = (String) enum0.nextElement();
            parameters.put(key, elements.get(key));

        }

        if (req instanceof MultipartRequestWrapper) {

            req = ((MultipartRequestWrapper) req).getRequest();
            enum0 = req.getParameterNames();
            while (enum0.hasMoreElements()) {
                String key = (String) enum0.nextElement();
                parameters.put(key, req.getParameterValues(key));
            }
        } else {
            log.info("Gathering multipart parameters for unwrapped request");
        }

        return parameters;
    }

    public static Object applicationInstance(String className)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        return (applicationClass(className).newInstance());

    }

    public static Class applicationClass(String className) throws ClassNotFoundException {

        // Look up the class loader to be used
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            classLoader = RequestUtils.class.getClassLoader();
        }
        // Attempt to load the specified class
        return (classLoader.loadClass(className));

    }
    protected static Log log = LogFactory.getLog(RequestUtils.class);
    public static final char INDEXED_DELIM = 91;
    public static final char INDEXED_DELIM2 = 93;
    public static final char NESTED_DELIM = 46;
}