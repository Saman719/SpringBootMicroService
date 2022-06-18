package com.example.SpringProject;

import com.example.SpringProject.model.CardPrintRequest;
import com.example.SpringProject.model.CardPrintRequestPK;
import com.example.SpringProject.repository.CardPrintRequestRepository;
import com.example.SpringProject.service.CardPrintRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.util.Date;
import java.util.List;

@SpringBootApplication
@EnableWebMvc
@ServletComponentScan
public class SpringProjectApplication {

	@Bean
	public ResourceBundleMessageSource messageSource(){
		ResourceBundleMessageSource reloadableResourceBundleMessageSource
				= new ResourceBundleMessageSource();
		reloadableResourceBundleMessageSource.setBasename("classpath:messages");
		reloadableResourceBundleMessageSource.setDefaultEncoding("UTF-8");
		return reloadableResourceBundleMessageSource;
	}

	@Bean
	public ViewResolver internalResourceViewResolver() {
		InternalResourceViewResolver bean = new InternalResourceViewResolver();
		bean.setViewClass(JstlView.class);
		bean.setPrefix("/WEB-INF/templates/");
		bean.setSuffix(".jsp");
		return bean;
	}

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(SpringProjectApplication.class, args);
		CardPrintRequestRepository repository = context.getBean(CardPrintRequestRepository.class);
		CardPrintRequestService service = context.getBean(CardPrintRequestService.class);
//		repository.save(cardPrintRequest);
		List<CardPrintRequest> requests = (List<CardPrintRequest>) repository.findAll();
		repository.updatePanNumberByBranchCode("6037697498739470","11000");
		Page<CardPrintRequest> requestPage = repository.getCardPrintRequestByPersonnelCode("9990001", PageRequest.of(0,2));
		List list = repository.findAllBranchCodeByIpAddress("10.20.12.45");
		List<CardPrintRequest> cardPrintRequests = service.getCMSPrintRequestsByIpAddress("10.20.12.45");
		System.out.println("LOL");
	}
}
