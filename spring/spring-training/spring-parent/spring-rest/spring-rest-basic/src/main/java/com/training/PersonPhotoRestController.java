package com.training;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/people/{id}/photo")
public class PersonPhotoRestController {

	private File root;

	@Autowired
	private PersonRepository personRepository;

	@Value("${user.home}")
	public void setUserHome(String home) {
		this.root = new File(home, "Desktop/images");
		Assert.isTrue(this.root.exists() || this.root.mkdirs(),
				"The path '" + this.root.getAbsolutePath() + "' must exist.");
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Resource> read(@PathVariable Long id) throws FileNotFoundException {
		Person person = null;
		Optional<Person> result = this.personRepository.findById(id);
		if (result.isPresent()) {
			person = result.get();
		} else {
			throw new EntityNotFoundException();
		}
		File file = fileFor(person);
		if (!file.exists()) {
			throw new FileNotFoundException(file.getAbsolutePath());
		}
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.IMAGE_JPEG);
		Resource resource = new FileSystemResource(file);
		return new ResponseEntity<Resource>(resource, httpHeaders, HttpStatus.OK);
	}

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT })
	public ResponseEntity<?> write(@PathVariable Long id, @RequestParam MultipartFile file)
			throws FileNotFoundException, IOException {
		Person person = null;
		Optional<Person> result = this.personRepository.findById(id);
		if (result.isPresent()) {
			person = result.get();
		} else {
			throw new EntityNotFoundException();
		}
		FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(fileFor(person)));
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand(id).toUri();
		return ResponseEntity.created(location).build();
	}

	private File fileFor(Person person) {
		return new File(this.root, Long.toString(person.getId()));
	}
}