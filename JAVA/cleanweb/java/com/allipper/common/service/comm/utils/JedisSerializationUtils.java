package com.allipper.common.service.comm.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.nustaq.serialization.FSTObjectInput;
import org.nustaq.serialization.FSTObjectOutput;

import redis.clients.jedis.exceptions.JedisClusterException;

/**
 * 用于保存到redis的对象的序列化操作
 * Jedis序列化工具类
 * @author 
 *
 */
public class JedisSerializationUtils {
	
	public JedisSerializationUtils() {
		
	}
	/**
	 * 对象序列化方法
	 * @param obj
	 * @return
	 */
   public static byte[] fastSerialize(Object obj) {
	   ByteArrayOutputStream byteArrayOutputStream = null;
       FSTObjectOutput out = null;
       try {
           byteArrayOutputStream = new ByteArrayOutputStream(512);
           out = new FSTObjectOutput(byteArrayOutputStream);  //32000  buffer size
           out.writeObject(obj);
           out.flush();
           return byteArrayOutputStream.toByteArray();
       } catch (IOException ex) {
           throw new JedisClusterException(ex);
       } finally {
           try {
               obj = null;
               if (out != null) {
                   out.close();    //call flush byte buffer
                   out = null;
               }
               if (byteArrayOutputStream != null) {

                   byteArrayOutputStream.close();
                   byteArrayOutputStream = null;
               }
           } catch (IOException ex) {
               // ignore close exception
           }
       } 
	}
   
   /**
    * 对象反序列化方法
    * @param objectData
    * @return
    * @throws Exception
    */
   public static Object fastDeserialize(byte[] objectData) throws Exception {
       ByteArrayInputStream byteArrayInputStream = null;
       FSTObjectInput in = null;
       try {
           byteArrayInputStream = new ByteArrayInputStream(objectData);
           in = new FSTObjectInput(byteArrayInputStream);
           return in.readObject();
       } catch (ClassNotFoundException ex) {
           throw new JedisClusterException(ex);
       } catch (IOException ex) {
           throw new JedisClusterException(ex);
       } finally {
           try {
               objectData = null;
               if (in != null) {
                   in.close();
                   in = null;
               }
               if (byteArrayInputStream != null) {
                   byteArrayInputStream.close();
                   byteArrayInputStream = null;
               }
           } catch (IOException ex) {
               // ignore close exception
           }
       }
   }
   
}
