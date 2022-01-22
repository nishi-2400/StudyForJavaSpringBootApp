package com.example.demo.app.inquiry;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Inquiry;
import com.example.demo.service.InquiryNotFoundException;
import com.example.demo.service.InquiryService;

@Controller
@RequestMapping("/inquiry")
public class InquiryController {
	
	private final InquiryService inquiryService;
	
	public  InquiryController (InquiryService inquiryService) {
		this.inquiryService = inquiryService;
	}
	
	@GetMapping("/form")
	public String form(InquiryForm inquiryForm, Model model) {
		model.addAttribute("title", "Inquiry Form");
		return "inquiry/form";
	}
	
	@PostMapping("/form")
	public String formGoBack(
			InquiryForm inquiryForm,
			Model model,
			@ModelAttribute("complete") String complete
		) {
			model.addAttribute("title", "Inquiry Form");
			model.addAttribute("inquiryForm", inquiryForm);
			return "inquiry/form";
	}
	
	@PostMapping("/confirm")
	public String confirm(
			@Validated InquiryForm inquiryForm, 
			BindingResult result,
			Model model
	) {
		System.out.println(inquiryForm.getName());
		System.out.println("aaaaaa");
		
		if (result.hasErrors()) {
			model.addAttribute("title", "Inquiry Form");
			return "inquiry/form";
		}

		model.addAttribute("title", "Confirm page");
		return "inquiry/confirm";
	}
	
	@GetMapping
	public String index(Model model) {
		List<Inquiry> list = inquiryService.getAll();
		
//		Inquiry inquiry = new Inquiry();
//		inquiry.setId(4);
//		inquiry.setName("test");
//		inquiry.setEmail("test@test.com");
//		inquiry.setContents("test");
//		
//		try {
//			inquiryService.update(inquiry);
//		} catch (InquiryNotFoundException e) {
//			model.addAttribute("message", e);
//			return "error/CustomPage";
//		}
		
		inquiryService.update(inquiry);
		
		
		
		model.addAttribute("inquiryList", list);
		model.addAttribute("title", "Inquiry Index");
		
		return "inquiry/index";
	}
	
	@PostMapping("/complete")
	public String complete(
			@Validated InquiryForm inquiryForm, 
			BindingResult result,
			Model model,
			RedirectAttributes redirectAttributes
	) {
		
		System.out.println(result);

		if (result.hasErrors()) {
			model.addAttribute("title", "Inquiry Form");
			return "inquiry/form";
		}
		
		//DB Insert
		Inquiry inquiry = new Inquiry();
		inquiry.setName(inquiryForm.getName());
		inquiry.setEmail(inquiryForm.getEmail());
		inquiry.setContents(inquiryForm.getContents());
		inquiry.setCreated(LocalDateTime.now());
		inquiryService.save(inquiry);
		
		redirectAttributes.addFlashAttribute("complete", "registered");
		return "redirect:/inquiry/form";
	}
	
//	@ExceptionHandler(InquiryNotFoundException.class)
//	public String handleExeption(InquiryNotFoundException e, Model model) {
//		model.addAttribute("message", e);
//		return "error/CustomPage";
//	}
}
