package com.test.listener;

import com.test.base.BaseInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * spring容器初始化完成事件
 * Created by shuzheng on 2017/1/7.
 */
public class ApplicationContextListener implements ApplicationListener<ContextRefreshedEvent> {

    private final static Logger log = LogManager.getLogger(ApplicationContextListener.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        // root application context
        if (null == contextRefreshedEvent.getApplicationContext().getParent()) {
            log.debug(">>>>> spring初始化完毕 <<<<<");
//            // spring初始化完毕后，通过反射调用所有使用BaseService注解的initMapper方法
//            Map<String, Object> baseServices = contextRefreshedEvent.getApplicationContext().getBeansWithAnnotation(BaseService.class);
//            for(Object service : baseServices.values()) {
//                _log.debug(">>>>> {}.initMapper()", service.getClass().getName());
//                try {
//                    Method initMapper = service.getClass().getMethod("initMapper");
//                    initMapper.invoke(service);
//                } catch (Exception e) {
//                    _log.error("初始化BaseService的initMapper方法异常", e);
//                    e.printStackTrace();
//                }
//            }
//
            // 系统入口初始化
            Map<String, BaseInterface> baseInterfaceBeans = contextRefreshedEvent.getApplicationContext().getBeansOfType(BaseInterface.class);
            for (Object service : baseInterfaceBeans.values()) {
                log.debug(">>>>> {}.init()", service.getClass().getName());
                try {
                    Method init = service.getClass().getMethod("init");
                    init.invoke(service);
                } catch (Exception e) {
                    log.error("初始化BaseInterface的init方法异常", e);
                    e.printStackTrace();
                }
            }

        }
    }

}
