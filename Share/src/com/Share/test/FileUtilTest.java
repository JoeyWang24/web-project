package com.Share.test;

import static org.junit.Assert.*;

import java.io.FileOutputStream;
import java.io.OutputStream;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;

import com.Share.exception.FileUploadException;
import com.Share.util.FileUtil;

import sun.misc.BASE64Decoder;

public class FileUtilTest {
  
    
    private MockHttpServletRequest request;
	@Test
	public void testRenamePng() {
		System.out.println(FileUtil.renamePng());
	}
    @Test
    public void testRename() {
    	String filename="123.jpg";
    	System.out.println(FileUtil.rename(filename));
    }


     

	@Test
	public void testBase64ToImg() throws Exception {
		String imgStr="data:image/jpeg;base64,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";
		String path="C:/javaprogram/blog/test.jpge";
		imgStr=imgStr.substring(imgStr.lastIndexOf(',')+1);
		System.out.println(generateImage(imgStr, path));
	}
	public static boolean generateImage(String imgStr, String path) {
		if (imgStr == null)
			return false;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// 解密
			byte[] b = decoder.decodeBuffer(imgStr);
			// 处理数据
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {
					b[i] += 256;
				}
			}
			OutputStream out = new FileOutputStream(path);
			System.out.println(1);
			out.write(b);
			
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}



}
