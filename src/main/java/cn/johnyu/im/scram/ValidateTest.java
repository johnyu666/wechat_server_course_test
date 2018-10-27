package cn.johnyu.im.scram;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;


//江苏农林职业学院
//http://121.248.70.120/jwweb/
//校验码的获取及使用



public class ValidateTest {
	
	private static Properties conf=new Properties();
	//cookie保存到 conf.properties中
	public static void getValidateAndCookie() throws Exception{
		String address="http://121.248.70.120/jwweb/sys/ValidateCode.aspx?t=812";	
		URL url=new URL(address);
		URLConnection con=url.openConnection();
		//con.setRequestProperty("Cookie", "ASP.NET_SessionId=tsczmgeydfuqrz3bces5fh45");
		con.setRequestProperty("Referer", "http://121.248.70.120/jwweb/ZNPK/KBFB_ClassSel.aspx");
		con.setDoInput(true);
		con.connect();
		String cookie=con.getHeaderField("Set-Cookie");
		System.out.println(cookie);
		conf.setProperty("cookie", cookie.split(";")[0]);
		conf.store(new FileOutputStream("conf.properties"), "comment");
		
		
		byte[] buf=new byte[512];
		InputStream input=con.getInputStream();
		OutputStream out=new FileOutputStream("k.png");
		int len=0;
		while((len=input.read(buf))!=-1) {
			out.write(buf,0,len);
		}
		input.close();
		out.close();
	}
	
	//从conf.properties取出cookie
	public static void getScheduleByTeacher() throws Exception{
		String yzm="d43a";
		conf.load(new FileInputStream("conf.properties"));
		String cookie=conf.getProperty("cookie");
		
		String referer="http://121.248.70.120/jwweb/ZNPK/TeacherKBFB.aspx";
		String address="http://121.248.70.120/jwweb/ZNPK/TeacherKBFB_rpt.aspx";
		
		URL url=new URL(address);
		HttpURLConnection con=(HttpURLConnection)url.openConnection();
		con.setDoOutput(true);
		con.setDoInput(true);
		con.setRequestProperty("Cookie", cookie);
		con.setRequestProperty("Referer", referer);
		con.setRequestMethod("POST");
		StringBuilder builder=new StringBuilder();
		builder.append("Sel_XNXQ=20180&Sel_JS=0000315&type=1&txt_yzm="+yzm);
		OutputStream netOut=con.getOutputStream();
		netOut.write(builder.toString().getBytes());
		int len=con.getContentLength();
		System.out.println(len);
		byte[] buf=new byte[512];
		//获取输入流的时候，完成请求
		InputStream input=con.getInputStream();
		OutputStream out=new FileOutputStream("aa1.txt");
		while((len=input.read(buf))!=-1) {
			out.write(buf,0,len);
			System.out.println(new String(buf,"GB2312"));
		}
		input.close();
		out.close();
	}
	public static void main(String[] args) throws Exception{
	
		//getValidateAndCookie();
		getScheduleByTeacher();
	}

}
