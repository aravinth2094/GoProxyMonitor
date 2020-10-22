package com.proxy.monitor.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proxy.monitor.util.CommonUtils;

@RestController
@RequestMapping("/api")
public class ProxyController {

	private static final Logger log = LoggerFactory.getLogger(ProxyController.class);

	@Value("${proxy.pac:" + ResourceUtils.CLASSPATH_URL_PREFIX + "public/proxy.pac" + "}")
	private String pacFile;

	@Value("${proxy.port:1080}")
	public int port;

	@GetMapping("/pac")
	public String pacFile() {
		try {
			if (this.pacFile.contains(ResourceUtils.CLASSPATH_URL_PREFIX)) {
				String ip = CommonUtils.getIp();
				ClassPathResource pacFileResource = new ClassPathResource(
						pacFile.replace(ResourceUtils.CLASSPATH_URL_PREFIX, ""));
				StringBuffer strBuff = new StringBuffer();
				try (InputStream inputStream = pacFileResource.getInputStream();
						Scanner read = new Scanner(inputStream)) {
					while (read.hasNextLine()) {
						strBuff.append(read.nextLine().replace("#ipAddress#", ip).replace("#port#", port + ""));
						strBuff.append(System.lineSeparator());
					}
				}
				return strBuff.toString();
			} else {
				return Files.readString(Paths.get(pacFile));
			}
		} catch (IOException e) {
			log.error("PAC Error", e);
			return null;
		}
	}

}
