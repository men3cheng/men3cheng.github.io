package cn.crxy.spider.process;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.html.HTML.Tag;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.json.JSONArray;
import org.json.JSONObject;

import cn.crxy.spider.Utils.HtmlUtils;
import cn.crxy.spider.Utils.PageUtils;
import cn.crxy.spider.domain.Page;

public class JdProcessImpl implements Processable {

	
	@Override
	public void process(Page page) {

		HtmlCleaner htmlCleaner = new HtmlCleaner();
		//表示对页面内容进行格式化
		TagNode rootNode = htmlCleaner.clean(page.getContent());
		try {
			if(page.getUrl().startsWith("http://item.jd.com/")){
				parseProduct(page, rootNode);
			}else{
				//列表页面中的所有商品的url
				// //*[@id="plist"]/ul/li[1]/div/div[1]/a
				Object[] li_objs = rootNode.evaluateXPath("//*[@id=\"plist\"]/ul/li[1]/div/div[1]/a");
				for (Object object : li_objs) {
					TagNode aNode = (TagNode)object;
					page.addNextUrls(aNode.getAttributeByName("href"));
				}
				//获取下一页的url
				// //*[@id="J_topPage"]/a[2]
				Object[] next_objs = rootNode.evaluateXPath("//*[@id=\"J_topPage\"]/a[2]");	
				if(next_objs!=null && next_objs.length>0){
					TagNode nextNode = (TagNode)next_objs[0];
					String nextUrl = nextNode.getAttributeByName("href");
					//因为最后一页是href="javascript:;&ms=5"，所以做出下面判断
					//nextUrl.equals("javascript:;") 这样写，如果nexturl为null，那么会报空指针异常
					if(!"javascript:;".equals(nextUrl)){
						page.addNextUrls(nextUrl);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void parseProduct(Page page, TagNode rootNode)throws XPatherException {
		//标题
		String title = HtmlUtils.getText(rootNode, "//div[@class=\"sku-name\"]");
		page.addFiled("title", title);
		
		//图片地址
		//*[@id="spec-img"]
		String picpath = HtmlUtils.getAttributeByName(rootNode, "//*[@id=\"spec-img\"]", "data-origin");
		page.addFiled("picpath", "http:"+picpath);
		
		//价格  京东的价格是使用ajax异步加载实现(动态加载的js请求)
		String price = getPrice(page);
		page.addFiled("price",price);
		
		/**
		 * 规格参数的解析思路
		 * 首先都是要分析页面规则，发现所有的规格参数都存在Ptable-item这个div标签中
		 * 所有先获取到所有的这个div标签
		 * 获取到之后再分析，
		 * 里面都包含h3标签和dt、dd标签
		 * 所以，再循环解析每个div中的h3标签和dt、dd标签
		 * 这样就可以吧商品的所有规格参数解析出来
		 * 最后为了后期在页面展现这些参数，所有在存储的时候按照一定规则，在这使用的是json数组的形式
		 */
		
		JSONArray spec_jsonarray = getSpec(rootNode);
		page.addFiled("spec", spec_jsonarray.toString());
	}

	Random random = new Random();
	
	private String getPrice(Page page) {
		String url = page.getUrl();
		//"http://item.jd.com/([0-9]+)\\.html"
		Pattern pattern = Pattern.compile("http://item.jd.com/([0-9]+)\\.html");
		Matcher matcher = pattern.matcher(url);
		String goodsId = null;
		if(matcher.find()){
			//得到第一组匹配--也就是([0-9]+)
			goodsId = matcher.group(1);
			//System.out.println("商品ID:"+goodsId);
			page.setGoodsId(goodsId);
		}
		String content = PageUtils.getContent("http://p.3.cn/prices/mgets?pduid="+(random.nextInt(100000))+"&skuIds=J_"+goodsId);
		//jQuery2210209([{"id":"J_2782640","p":"2499.00","m":"6666.00"}]);
		JSONArray jsonArray = new JSONArray(content);
		JSONObject jsonObject = jsonArray.getJSONObject(0);//{"id":"J_2782640","p":"2499.00","m":"6666.00"}
		//System.out.println("商品价格:"+jsonObject.getString("p"));
		return jsonObject.getString("p");//"p":"2499.00"
	}

	private JSONArray getSpec(TagNode rootNode) throws XPatherException {
		JSONArray spec_jsonarray = new JSONArray();
		Object[] item_objs = rootNode.evaluateXPath("//*[@id=\"detail\"]/div[2]/div[2]/div[2]/div");
		if (item_objs != null && item_objs.length > 0) {
			for (Object object : item_objs) {
				// 获取div标签
				TagNode divnode = (TagNode) object;
				// 获取h3标签内容
				Object[] h3_objs = divnode.evaluateXPath("/h3");
				if (h3_objs != null && h3_objs.length > 0) {
					JSONObject h3_jsonobj = new JSONObject();
					TagNode h3Node = (TagNode) h3_objs[0];
					//System.out.println("h3标签:"+h3Node.getText().toString());
					h3_jsonobj.put("name", h3Node.getText().toString());
					h3_jsonobj.put("value", "");
					spec_jsonarray.put(h3_jsonobj);
				}
				// 获取div中的dt和dd标签内容
				Object[] dt_objs = divnode.evaluateXPath("/dl/dt");
				Object[] dd_objs = divnode.evaluateXPath("/dl/dd");
				if (dt_objs != null && dd_objs != null && dt_objs.length > 0 && dd_objs.length > 0) {
					for (int i = 0; i < dt_objs.length; i++) {
						TagNode dtnode = (TagNode) dt_objs[i];
						TagNode ddnode = (TagNode) dd_objs[i];
						//System.out.println("dt标签:"+dtnode.getText().toString()+"--"+"dd标签:"+ddnode.getText().toString());
						JSONObject dd_dt_jsonobj = new JSONObject();
						dd_dt_jsonobj.put("name", dtnode.getText().toString());
						dd_dt_jsonobj.put("value", ddnode.getText().toString());
						spec_jsonarray.put(dd_dt_jsonobj);
					}
				}
			}
		}
		return spec_jsonarray;
	}
}

