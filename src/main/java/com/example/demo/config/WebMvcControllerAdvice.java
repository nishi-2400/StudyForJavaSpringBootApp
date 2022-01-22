package com.example.demo.config;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

import com.example.demo.service.InquiryNotFoundException;


/**
 * 全てのControllerで共通処理を定義
 */
@ControllerAdvice
public class WebMvcControllerAdvice {

	/*
	 * This method changes empty character to null
	 * 空文字をnullに変換
	 */
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
    
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public String handlerException(EmptyResultDataAccessException e, Model model) {
    	model.addAttribute("message", e);
    	return "error/CustomPage";
    }
    
    @ExceptionHandler(InquiryNotFoundException.class)
	public String handleExeption(InquiryNotFoundException e, Model model) {
		model.addAttribute("message", e);
		return "error/CustomPage";
	}
   
}