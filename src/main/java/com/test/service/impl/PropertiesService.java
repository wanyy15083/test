package com.test.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PropertiesService {
	@Value("${FILE_PATH}")
	public String FILE_PATH;
}
