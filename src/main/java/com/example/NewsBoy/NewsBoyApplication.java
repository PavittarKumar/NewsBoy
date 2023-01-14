package com.example.NewsBoy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@SpringBootApplication
@RestController
public class NewsBoyApplication {

	@Value("${accessToken}")
	private String accessToken;
	@Value("${mediaStackUrl}")
	private String mediaStackUrl;
	@Value("${pageLimit}")
	private String pageLimit;
	public static void main(String[] args) {
		SpringApplication.run(NewsBoyApplication.class, args);
	}

	@GetMapping("/news")
	@ResponseStatus(HttpStatus.CONFLICT)
	public String getCategorisedNews(@RequestParam(required = false) String categories,
									 @RequestParam(required = false) String countries,
									 @RequestParam(required = false) String sources,
									 @RequestParam(required = false) String keywords,
									 @RequestParam(required = false) String date,
									 @RequestParam(required = false) String languages) {
		String uri = mediaStackUrl +
				"?access_key=" + accessToken +
				(StringUtils.isEmpty(categories) ? "" : ("&categories=" + categories)) +
				(StringUtils.isEmpty(countries) ? "" : ("&countries=" + countries)) +
				(StringUtils.isEmpty(keywords) ? "" : ("&keywords=" + keywords)) +
				(StringUtils.isEmpty(date) ? "" : ("&date=" + date)) +
				(StringUtils.isEmpty(languages) ? "" : ("&languages=" + languages)) +
				(StringUtils.isEmpty(sources) ? "" : ("&sources=" + sources)) +
				"&limit=" + pageLimit;
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(uri, String.class);
		return result;
	}

}
