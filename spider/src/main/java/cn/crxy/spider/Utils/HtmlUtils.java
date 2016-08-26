package cn.crxy.spider.Utils;

import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

public class HtmlUtils {
	/**
	 * 根据xpath获取标签的内容
	 */
	public static String getText(TagNode tagNode, String xpath) {
		
		// 这种/开头的xpath是不可用的		
		// 注：/html/body/div[5]/div/div[2]/div[1]
		
		String result = null;
		try {
			// 根据传递的xpath规则到页面内容中去定位标签
			Object[] objs = tagNode.evaluateXPath(xpath);
			if (objs != null && objs.length > 0) {
				TagNode title_node = (TagNode) objs[0];
				result = title_node.getText().toString();
				//System.out.println("标签的内容:"+result);
			}
		} catch (XPatherException e) {
			e.printStackTrace();
		}
		return result;
		
	}
	
	/**
	 * 根据xpath获取某一个标签的属性的值
	 * @param tagNode
	 * @param xpath
	 * @param attr
	 * @return 
	 * @return
	 */
	
	public static String getAttributeByName(TagNode tagNode, String xpath,String attr){
		String result = null;
		Object[] picpath_objs;
		try {
			picpath_objs = tagNode.evaluateXPath(xpath);
			if(picpath_objs!=null && picpath_objs.length>0){
				TagNode picpath_Node = (TagNode)picpath_objs[0];
				result = picpath_Node.getAttributeByName(attr);
				//System.out.println("图片地址:"+result);
			}
		} catch (XPatherException e) {
			e.printStackTrace();
		}
		return result;
	}
}
