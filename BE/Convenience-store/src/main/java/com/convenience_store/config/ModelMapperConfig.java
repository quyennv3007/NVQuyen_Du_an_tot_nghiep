package com.convenience_store.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

	@Bean // create instance for ModelMapper and use
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration() // set mapping level
				.setMatchingStrategy(MatchingStrategies.STRICT); // matchingStrategies: mapping level ( Standard, loose,
																	// strict)
		return modelMapper;
	}
}
