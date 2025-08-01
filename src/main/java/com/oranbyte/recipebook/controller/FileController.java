package com.oranbyte.recipebook.controller;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oranbyte.recipebook.constant.AppConstants;

@RestController
@RequestMapping("/uploads")
public class FileController {

	@GetMapping("/{directory}/{filename:.+}")
	public ResponseEntity<Resource> getFile(
	        @PathVariable String directory,
	        @PathVariable String filename) throws MalformedURLException {

	    Path filePath = Paths.get(AppConstants.UPLOAD_DIR).resolve(directory).resolve(filename);
	    Resource resource = new UrlResource(filePath.toUri());

	    if (!resource.exists()) {
	        return ResponseEntity.notFound().build();
	    }

	    return ResponseEntity.ok().body(resource);
	}

	
}
