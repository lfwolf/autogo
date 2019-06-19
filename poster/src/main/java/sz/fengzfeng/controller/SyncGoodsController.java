package sz.fengzfeng.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sync/goods")
public class SyncGoodsController {
	
	@RequestMapping("/")
    public String index() {
        return "/sync/goods/index";
    }

}
