package sz.fengzfeng.processor;

import us.codecraft.webmagic.processor.PageProcessor;

import java.io.Console;
import java.util.List;
import java.util.Map;

import ch.qos.logback.classic.Logger;
import sz.fengzfeng.datamodel.aikucun.ActivityList;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import com.alibaba.fastjson.*;

public class AikucunProcessor implements PageProcessor {
    private Site site = Site.me().setDomain("zuul.aikucun.com");

    @Override
    public void process(Page page) {
    	System.out.println(page.getJson().toString());
    	Map<String, Object> ret = JSON.parseObject(page.getRawText(), new TypeReference<Map<String, Object>>(){});
    	if((int)ret.get("code") != 0 ) {
    		return;

    	}
    	ActivityList retData = JSON.parseObject(ret.get("data").toString(),ActivityList.class);

    }

    @Override
    public Site getSite() {
        return site;

    }

    public static void main(String[] args) {
    	String url = "https://zuul.aikucun.com/aggregation-center-facade/api/app/activity/list/v1.0?appid=12873468&did=4560639a7b30584fb2aa1a95a9ef5665&id=0&location=0&noncestr=175008&pageno=1&pagesize=10&subuserid=8a9820803601b33c53b43e1087e5307e&timestamp=1556023120&token=d9a2883d78994f6caaa4818afd85e9d2&userid=836de46fed1448b634a17656623bba3f&zuul=1&sig=662e796e3855e06977a111de3ce0cb1ffeeea390";
        Spider.create(new AikucunProcessor()).addUrl(url)
             .addPipeline(new ConsolePipeline()).run();
    }

}
