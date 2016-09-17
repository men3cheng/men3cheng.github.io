package cn.crxy.spider.store;

import java.util.Date;
import java.util.Map;

import cn.crxy.spider.Utils.MyDateUtils;
import cn.crxy.spider.Utils.MyDbUtils;
import cn.crxy.spider.domain.Page;

public class MySqlStoreImpl implements Storeable {

	/**
	 *  CREATE TABLE `spider` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `goods_id` varchar(20) DEFAULT NULL,
  `data_url` varchar(300) DEFAULT NULL,
  `pic_url` varchar(300) DEFAULT NULL,
  `title` varchar(300) DEFAULT NULL,
  `price` varchar(10) DEFAULT NULL,
  `param` text,
  `current_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
	 */
	@Override
	public void store(Page page) {

		String data_url = page.getUrl();
		String goodsId = page.getGoodsId();
		Map<String, String> values = page.getValues();
		String pic_url = values.get("picpath");
		String title = values.get("title");
		String price = values.get("price");
		String param = values.get("spec");
		String current_time = MyDateUtils.formatDate2(new Date());
		//goods_id,data_url,pic_url,title,price,param,`current_time`
		MyDbUtils.update(MyDbUtils.INSERT_LOG, goodsId,data_url,pic_url,title,price,param,current_time);
		
	}

}
