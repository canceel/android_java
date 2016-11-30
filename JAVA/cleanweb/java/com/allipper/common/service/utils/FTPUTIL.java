package com.allipper.common.service.utils;


import java.io.InputStream;
import java.io.OutputStream;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.config.Config;

import sun.net.ftp.FtpClient;

public class FTPUTIL {
	
	private static FtpClient ftpClient;

	// 生产
	//private static byte[] ip = { (byte) 10, (byte) 0, (byte) 31, (byte) 220 };
	// 测试10.1.30.135
	//private static byte[] ip = { (byte) 10, (byte) 1, (byte) 30, (byte) 135 };
	//private static int port = 21;
	private static String username = Config.getString("FTPNAME");
	private static String password = Config.getString("FTPPWD");
	
    	

	/**
	 * 服务器连接
	 */
	private static void connectServer() throws Exception {
		Logger log = LogManager.getLogger(FTPManager.class);
		try {
			/******* 连接服务器 *******/
			ftpClient = FtpClient.create(Config.getString("FTPIP"));
			/*InetAddress ipAddress = InetAddress.getByAddress(ip);
			SocketAddress addr = new InetSocketAddress(ipAddress, port);*/
			ftpClient.setConnectTimeout(30 * 1000);// 超时30秒
			ftpClient.setReadTimeout(30 * 1000);// 超时30秒
			ftpClient.login(username, password.toCharArray());
			// 设置成2进制传输
			ftpClient.setBinaryType();
		} catch (Exception ex) {
			System.out.println("login false!");
			log.error("FTPERROR CONNECT: " + ex.getMessage());
			ex.printStackTrace();
			throw new Exception(ex);
		}
	}

	/**
	 * 关闭连接
	 */
	private static void closeConnect() throws Exception {
		Logger log = LogManager.getLogger(FTPManager.class);
		try {
			if (ftpClient.isConnected()) {
				ftpClient.close();
			}
		} catch (Exception ex) {
			System.out.println("close false");
			log.error("FTPERROR CLOSE: " + ex.getMessage());
			ex.printStackTrace();
			throw new Exception(ex);
		}
	}

	/**
	 * 上传文件
	 * 
	 */
	public int upload(MultipartFile mf, String path, String remotefile)throws Exception {
		Logger log = LogManager.getLogger(FTPManager.class);
		OutputStream os = null;
		InputStream in = null;
		int flag=1;
		String[] fileName = remotefile.split("_");
		try {
			connectServer();
			if (path.length() != 0) {
				try {
					// 把远程系统上的目录切换到参数path所指定的目录
					ftpClient.changeDirectory(path);
				} catch (Exception e) {
					ftpClient.makeDirectory(path);
					ftpClient.changeDirectory(path);
				}
				path = path + "/" + fileName[0].substring(0, 8);
				try {
					// 把远程系统上的目录切换到参数path所指定的目录
					ftpClient.changeDirectory(path);
				} catch (Exception e) {
					ftpClient.makeDirectory(path);
					ftpClient.changeDirectory(path);
				}
				path = path + "/" + fileName[1];
				try {
					// 把远程系统上的目录切换到参数path所指定的目录
					ftpClient.changeDirectory(path);
				} catch (Exception e) {
					ftpClient.makeDirectory(path);
					ftpClient.changeDirectory(path);
				}
			}
			ftpClient.setBinaryType();

			// 将远程文件加入输出流中
			String name=new String(remotefile.getBytes("ISO-8859-1"),"UTF-8");
			os = ftpClient.putFileStream(name, true);
			in = mf.getInputStream();
			byte[] bytes = new byte[1024 * 1024];
			int c;
			while ((c = in.read(bytes)) != -1) {
				os.write(bytes, 0, c);
			}
		} catch (Exception ex) {
			flag=0;
			System.out.println("upload false");
			log.error("FTPERROR : " + ex.getMessage());
			ex.printStackTrace();
			throw new Exception(ex);
		} finally {
			if (os != null) {
				os.close();
			}
			if (in != null) {
				in.close();
			}
			closeConnect();
		}
		return flag;
	}

	/**
	 * 下载文件
	 */
	public  void download(HttpServletResponse response, String path,
			String remotefile) throws Exception {
		Logger log = LogManager.getLogger(FTPManager.class);
		InputStream in = null;
		OutputStream os = response.getOutputStream();
		response.setContentType("application/pdf;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment; filename="
				+ remotefile);
		try {
			connectServer();
			if (path.length() != 0) {
				// 把远程系统上的目录切换到参数path所指定的目录
				ftpClient.changeDirectory(path);
			}
			ftpClient.setBinaryType();

			// 将远程文件加入输出流中
			in = ftpClient.getFileStream(remotefile);
			byte[] bytes = new byte[1024];
			int c;
			while ((c = in.read(bytes)) != -1) {
				os.write(bytes, 0, c);
			}
			os.flush();
			os.close();
		} catch (Exception ex) {
			System.out.println("download false");
			log.error("FTPERROR : " + ex.getMessage());
			ex.printStackTrace();
			throw new Exception(ex);
		} finally {
			if (os != null) {
				os.close();
			}
			if (in != null) {
				in.close();
			}
			closeConnect();
		}
	}

	/**
	 * 下载文件
	 */
	public static void download(OutputStream os, String path, String remotefile)
			throws Exception {
		Logger log = LogManager.getLogger(FTPManager.class);
		InputStream in = null;
		try {
			connectServer();
			if (path.length() != 0) {
				// 把远程系统上的目录切换到参数path所指定的目录
				ftpClient.changeDirectory(path);
			}
			ftpClient.setBinaryType();

			// 将远程文件加入输出流中
			in = ftpClient.getFileStream(remotefile);
			byte[] bytes = new byte[1024];
			int c;
			while ((c = in.read(bytes)) != -1) {
				os.write(bytes, 0, c);
			}
			os.flush();
			os.close();
		} catch (Exception ex) {
			System.out.println("download false");
			log.error("FTPERROR : " + ex.getMessage());
			ex.printStackTrace();
			throw new Exception(ex);
		} finally {
			if (os != null) {
				os.close();
			}
			if (in != null) {
				in.close();
			}
			closeConnect();
		}
	}
}
