package com.training;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

import javax.persistence.EntityNotFoundException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/people/{id}/photo")
public class PersonPhotoRestController {

	private File root;

	private static Log logger = LogFactory.getLog(PersonPhotoRestController.class);

	@Autowired
	PersonProperties personProperties;

	@Autowired
	private PersonRepository personRepository;

	@Value("${person.property.imagesDir}")
	public void setImagesDirectory(String imagesDirectory) {
		this.root = new File(imagesDirectory);
		logger.info("Images directory absolute path: " + this.root.getAbsolutePath());
		Assert.isTrue(this.root.exists() || this.root.mkdirs(),
				"The path '" + this.root.getAbsolutePath() + "' must exist.");
	}

	public File fileFor(Person p) {
		return new File(this.root, Long.toString(p.getId()));
	}

	public Person findPersonById(Long id) {
		return this.personRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
	}
	
	@GetMapping
	public ResponseEntity<Resource> read(@PathVariable Long id) throws FileNotFoundException {
		Person person = findPersonById(id);
		File file = fileFor(person);
		if (!file.exists()) {
			throw new FileNotFoundException(file.getAbsolutePath());
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		Resource resource = new FileSystemResource(file);
		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	}
	
	@PostMapping
	@PutMapping
	public ResponseEntity<?> write(@PathVariable Long id, @RequestParam MultipartFile file) throws FileNotFoundException, IOException {
		Person person = findPersonById(id);
		FileCopyUtils.copy(file.getInputStream() , new FileOutputStream(fileFor(person)));
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand(id).toUri();
		return ResponseEntity.created(location).build();
	}
}
