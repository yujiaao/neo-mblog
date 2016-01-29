package com.chengfu.neomblog.controller;

import java.util.ArrayList;
import java.util.List;






import org.neo4j.graphdb.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.data.neo4j.core.GraphDatabase;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.chengfu.neomblog.model.User;
import com.chengfu.neomblog.repository.UserRepository;
import com.chengfu.neomblog.util.Utils;


@Transactional
@Controller
public class PublicController {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	Utils utils;
	
	@Autowired GraphDatabase graphDatabase;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String publicInfo(Model model) {
		Transaction tx = graphDatabase.beginTx();
		
		try {
		List<User> result = userRepository.findAll().as(List.class);
		model.addAttribute("users", result);
		tx.success();
		} finally {
			tx.close();
		}
//		List<User> result = new ArrayList<User>(); 
//		for (User user : users) {
//			result.add(user);
//		}
	
		User currentUser = userRepository.findByLoginName(utils.getCurrentUsername());
		model.addAttribute("currentUser", currentUser);
		return "publicInfo";
	}
}
