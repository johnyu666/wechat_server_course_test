package cn.johnyu.im.scram;

import java.io.File;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ScrapeTest {

	public static void main(String[] args) throws Exception{
		Document doc= Jsoup.parse(new File("abc.html"),"UTF-8", "http://www.johnyu.cn");
		Element e=doc.select("#target").get(0);
		System.out.println(e);
		System.out.println(e.attr("href"));
		System.out.println(e.absUrl("href"));
		
	}
}
