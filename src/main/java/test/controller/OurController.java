package test.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class OurController {

	@RequestMapping("/")
	public ModelAndView helloSlash(Model model) {
	    
		List<String> lines = new ArrayList<String>();
		lines.add("no proper request received");
	    model.addAttribute("lines", lines);
	    
	    return new ModelAndView("index");
	}
	
	@RequestMapping(value="/intro")
	public ModelAndView introduction(Model model){
		System.out.println("Hello World!");
		//return "intro";
		List<String> linesList = new ArrayList<String>();
		linesList.add("This is the first line");
		linesList.add("Controller created a list with these lines and passed it to the view.");
		linesList.add("The view layer can use any view technology like jsp, jstl, velocity to display this message passed to the view");
		
		model.addAttribute("lines", linesList);
		
		return new ModelAndView("intro");
	}
	
}
