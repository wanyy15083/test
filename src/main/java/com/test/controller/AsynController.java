package com.test.controller;

import com.test.callback.LongTermTaskCallback;
import com.test.service.LongTimeAsyncCallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.WebAsyncTask;
import org.springframework.web.servlet.ModelAndView;

import java.util.concurrent.Callable;

@Controller
@RequestMapping("/async")
public class AsynController {
    @Autowired
    private LongTimeAsyncCallService longTimeAsyncCallService;

    @RequestMapping("/task")
    @ResponseBody
    public DeferredResult<ModelAndView> task() {
        final DeferredResult<ModelAndView> deferredResult = new DeferredResult<ModelAndView>();
        System.out.println("async task start:" + Thread.currentThread().getId());
        longTimeAsyncCallService.makeRemoteCallAndUnknownWhenFinish(new LongTermTaskCallback() {
            @Override
            public void callback(Object result) {
                System.out.println("async task complete:" + Thread.currentThread().getId());
                ModelAndView mav = new ModelAndView("remotecalltask");
                mav.addObject("result", result);
                deferredResult.setResult(mav);
            }
        });

        deferredResult.onTimeout(new Runnable() {
            @Override
            public void run() {
                System.out.println("async task timeout:" + Thread.currentThread().getId());
                ModelAndView mav = new ModelAndView("remotecalltask");
                mav.addObject("result", "异步调用执行超时");
                deferredResult.setResult(mav);
            }
        });
        return deferredResult;

    }

    @RequestMapping("/longTimeTask")
    public WebAsyncTask longTimeTask() {
        System.out.println("long time task start:" + Thread.currentThread().getId());
        Callable<ModelAndView> callable = new Callable<ModelAndView>() {
            @Override
            public ModelAndView call() throws Exception {
                Thread.sleep(3000);
                ModelAndView mav = new ModelAndView("remotecalltask");
                mav.addObject("result", "执行成功");
                System.out.println("task complete:" + Thread.currentThread().getId());
                return mav;
            }
        };

//        return new WebAsyncTask(callable);

        WebAsyncTask asyncTask = new WebAsyncTask(2000, callable);
        asyncTask.onTimeout(
                new Callable<ModelAndView>() {
                    public ModelAndView call() throws Exception {
                        ModelAndView mav = new ModelAndView("remotecalltask");
                        mav.addObject("result", "执行超时");
                        System.out.println("task timeout:" + Thread.currentThread().getId());
                        return mav;
                    }
                }
        );
        return new WebAsyncTask(3000, callable);
    }

    @RequestMapping("/async")
    public ModelAndView async() {
        ModelAndView mav = new ModelAndView("remotecalltask");
        longTimeAsyncCallService.asyncDoSth();
        mav.addObject("result", "异步返回");
        return mav;
    }
}
