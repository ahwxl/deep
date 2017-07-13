package com.bplow.deep.bpm.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.bpm.domain.BpmForm;
import com.bplow.deep.bpm.service.BpmFormService;

@Controller
@RequestMapping(value = "/form")
public class FormController {

    private Logger         logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BpmFormService bpmFormService;

    @RequestMapping(value = "/index")
    public String index(HttpServletRequest httpRequest, HttpServletResponse response, Model view) {

        logger.info("展示表单列表页面");

        return "bpm/form/form";
    }

    @RequestMapping(value = "/formList")
    @ResponseBody
    public Page<BpmForm> formItems(HttpServletRequest httpRequest, HttpServletResponse response,
                                   Model view, BpmForm form) {

        logger.info("展示表单列表页面");

        Page<BpmForm> page = bpmFormService.queryFormsForPage(form);

        return page;
    }

    //添加页面
    @RequestMapping(value = "/formAdd")
    public String formAddPage(HttpServletRequest httpRequest, HttpServletResponse response,
                              Model view, BpmForm form) {

        logger.info("展示表单列表页面");

        return "bpm/form/form-add";
    }

    @RequestMapping(value = "/formAddAction")
    @ResponseBody
    public String formAdd(HttpServletRequest httpRequest, HttpServletResponse response, Model view,
                          BpmForm form) {

        logger.info("展示表单列表页面");

        bpmFormService.addForm(form);

        return String.format("{\"responseMsg\":\"%s\"}", "添加成功");
    }

    //查询
    @RequestMapping(value = "/queryForm")
    @ResponseBody
    public BpmForm queryForm(HttpServletRequest httpRequest, HttpServletResponse response,
                             Model view, BpmForm form) {

        logger.info("展示表单列表页面");

        BpmForm bpmform = bpmFormService.queryFormById(form.getFormId());

        return bpmform;
    }

    @RequestMapping(value = "/updateForm")
    public String updateForm(HttpServletRequest httpRequest, HttpServletResponse response,
                             Model view, BpmForm form) {

        logger.info("展示表单列表页面");

        view.addAttribute("formId", form.getFormId());
        return "bpm/form/form-modify";
    }

    @RequestMapping(value = "/updateFormAction")
    @ResponseBody
    public String updateFormAction(HttpServletRequest httpRequest, HttpServletResponse response,
                                   Model view, BpmForm form) {

        logger.info("展示表单列表页面");

        bpmFormService.updateForm(form);

        return String.format("{\"responseMsg\":\"%s\"}", "成功");
    }

    //查询
    @RequestMapping(value = "/delForm")
    @ResponseBody
    public String delForm(HttpServletRequest httpRequest, HttpServletResponse response, Model view,
                          BpmForm form) {

        logger.info("展示表单列表页面");

        bpmFormService.delForm(form.getFormId());

        return String.format("{\"responseMsg\":\"%s\"}", "删除成功");
    }

    //表单列表展示
    @RequestMapping(value = "/formItem")
    @ResponseBody
    public List<BpmForm> getForms(BpmForm form) {

        List<BpmForm> list = bpmFormService.queryForms(form);

        return list;
    }

}
