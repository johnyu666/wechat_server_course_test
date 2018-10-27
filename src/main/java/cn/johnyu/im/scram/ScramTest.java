package cn.johnyu.im.scram;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ScramTest {
	private static String urlSpec="http://gs.xjtu.edu.cn/sjlby.jsp?urltype=tree.TreeTempUrl&wbtreeid=1010";
	
	public static void main(String[] args) throws Exception{
		//urlSpec="http://www.cctv.com/";
//		URL url=new URL(urlSpec);
//		URLConnection con=url.openConnection();
//		con.connect();
//		InputStream in=con.getInputStream();
//		BufferedReader reader=new BufferedReader(new InputStreamReader(in));
//		String line=null;
//		while((line=reader.readLine())!=null) {
//			System.out.print(line);
//		}
		URL url=new URL(urlSpec);
		Connection con1= Jsoup.connect(urlSpec);
		//con1.header(name, value);
		Document doc= con1.get();
		Element ele= doc.getElementsByClass("zipp_list").get(0);
		Elements as=ele.getElementsByTag("a");
		for(int i=0;i<as.size();i++) {
			Element a=as.get(i);
			if(a.hasText()) {
				System.out.println(a.absUrl("href")+"\t"+a.text());
			}
		}
	}
}
