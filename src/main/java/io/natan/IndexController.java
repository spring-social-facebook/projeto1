package io.natan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {

	@Autowired
	private Facebook facebook;
	
	@Autowired
	private ConnectionRepository connectionRepository;
	
	@GetMapping
	public String homePage(Model model) {
		
		if(connectionRepository.findPrimaryConnection(Facebook.class) == null) {
			return "redirect:/connect/facebook";
		}
		
		String[] fields = {"name", "email"};
		
		model.addAttribute("facebookProfile", facebook.fetchObject("me", User.class, fields));
		
		
		PagedList<Post> feed = facebook.feedOperations().getFeed();
		model.addAttribute("feed", feed);
		
		return "home";
		
	}
	
	
}
