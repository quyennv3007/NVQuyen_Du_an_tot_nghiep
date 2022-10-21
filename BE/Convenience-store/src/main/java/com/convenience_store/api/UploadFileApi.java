package com.convenience_store.api;

import java.nio.file.Files;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.convenience_store.service.GGDriveService;
//import com.google.api.services.drive.model.File;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/api/upload/file")
public class UploadFileApi {

	@Autowired
	private GGDriveService googleDriveService;

//	@PostMapping("")
//	public ResponseEntity<?> uploadFile(@RequestParam("files") MultipartFile[] files) {
//		HashMap<String, String> response = new HashMap<>();
//		try {
//			for (MultipartFile file : files) {
//				File fileResponse = googleDriveService.uploadGoogleFile(file, file.getContentType(),
//						file.getOriginalFilename());
//				String href = fileResponse.getWebContentLink().replace("&export=download", "");
//				response.put(file.getOriginalFilename(), href);
//			}
//			return ResponseEntity.ok(response);
//		} catch (Exception ex) {
//			ex.printStackTrace();
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
//	}
	
//	@PostMapping("")
//	public ResponseEntity<?> uploadFile(@PathParam("files") MultipartFile[] files) {
//		System.out.println("run");
//		Set<String> response = new HashSet<>();
//		try {
//			for (MultipartFile file : files) {
//				File fileResponse = googleDriveService.uploadGoogleFile(file, file.getContentType(),
//						file.getOriginalFilename());
//				String href = fileResponse.getWebContentLink().replace("&export=download", "");
//				response.add(href);
//			}
//			return ResponseEntity.ok(response);
//		} catch (Exception ex) {
//			ex.printStackTrace();
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
//	}
	
	@PostMapping("")
	public ResponseEntity<?> uploadFile(@PathParam("files") MultipartFile[] files) {
		System.out.println("run");
		Set<String> response = new HashSet<>();
		try {
			for (MultipartFile file : files) {
				save(file,"img");
				response.add(file.getOriginalFilename());
			}
			return ResponseEntity.ok(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	public File save(MultipartFile file, String folder) {
		Path root = Paths.get("src/main/resources/static/assets/"+folder);
		File dir = new File("src/main/resources/static/assets/"+folder);
		if(!dir.exists()) {
			dir.mkdirs();
		}
		System.out.println("dir: "+dir.getAbsolutePath());
		String filename = file.getOriginalFilename();

		try {
			Files.copy(file.getInputStream(), root.resolve(file.getOriginalFilename()),StandardCopyOption.REPLACE_EXISTING);
			File savedFile = new File(dir,filename);
			return savedFile;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}
}
