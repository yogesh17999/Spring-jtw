package com.spring.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.spring.demo.dto.FootballMatches;
import com.spring.demo.dto.MatchResult;
import com.spring.demo.entity.User;
import com.spring.demo.repo.UserRepo;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	UserRepo userRepo;

	RestTemplate restTemplate = new RestTemplate();

	@GetMapping("/{id}")
	public User getUserById(@PathVariable("id") Long id) {
		return userRepo.findById(id).get();
	}

	@GetMapping("/findDrawMatches")
	public Long findDrawMatches(@RequestParam("year") Long year) {

		String resourceUrl = "https://jsonmock.hackerrank.com/api/football_matches?year=" + year;
		System.out.println(resourceUrl);
		ResponseEntity<FootballMatches> response = restTemplate.getForEntity(resourceUrl, FootballMatches.class);

		System.out.println(response.getBody());
		Long count = 0L;
		FootballMatches matchResults = response.getBody();
		
	   for(int i = 1; i <= matchResults.getTotal_pages(); i++) {
		  
		   count += countDrawMatches(resourceUrl + "&page="+ i );
	   }
		

		return count;

	}

	@Async
	private Long countDrawMatches(String resourceUrl) {
	
		ResponseEntity<FootballMatches> response = restTemplate.getForEntity(resourceUrl, FootballMatches.class);
		List<MatchResult> matchResults = response.getBody().getData();
		Long count = 0L;
		if (matchResults != null && matchResults.size() > 0) {
			for (MatchResult matcheResult : matchResults) {
				if (matcheResult.getTeam1goals().equals(matcheResult.getTeam2goals())) {
					count++;
				}
			}
		}
		return count;

	}
}
