package com.gman.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * Created by Anton Mikhaylov on 30.12.2020.
 */
@Controller
public class ViewController {

    @GetMapping("news")
    public ModelAndView getNewsView(@RequestParam String queryType, Map<String, Object> model) {
        model.put("queryType", queryType);
        return new ModelAndView("news");
    }

    @GetMapping("index")
    public String getIndexView() {
        return "index";
    }
}
