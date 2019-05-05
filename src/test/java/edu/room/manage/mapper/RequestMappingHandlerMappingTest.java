package edu.room.manage.mapper;

import edu.room.manage.common.mybatis.condition.MybatisCondition;
import edu.room.manage.domain.Menu;
import edu.room.manage.service.MenuService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Map;
import java.util.Set;

/**
 * @author 执笔
 * @date 2019/5/2 17:39
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RequestMappingHandlerMappingTest {

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;
    @Autowired
    private MenuMapper                   menuMapper;
    @Autowired
    private MenuService                  menuService;

    @Test
    public void testAll() {
        // 获取url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();
        for (RequestMappingInfo info : map.keySet()) {
            // 获取url的Set集合，一个方法可能对应多个url
            Set<String> patterns = info.getPatternsCondition().getPatterns();
            for (String url : patterns) {
                if (!menuService.isExist(new MybatisCondition().eq("url", url))) {
                    Menu menu = new Menu()
                            .setUrl(url)
                            .setParentId(0)
                            .setSort(0)
                            .setCode(url)
                            .setType(Menu.MenuTypeStatus.MENU)
                            .setIcon("fa fa-dashboard")
                            .setName(url);
                    menuMapper.insertSelective(menu);
                }
            }
        }
    }
}
