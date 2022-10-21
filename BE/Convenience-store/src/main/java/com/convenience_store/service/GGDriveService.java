package com.convenience_store.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.Part;

import org.springframework.web.multipart.MultipartFile;

import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;

public interface GGDriveService {
	
	List<File> getAllGoogleDriveFiles() throws IOException;
	List<File> getGoogleFilesByName(String fileNameLike) throws IOException;
	File getGoogleFolderByName(String folderNameLike) throws IOException;
	Permission createPublicPermission(String googleFileId) throws IOException;
	File uploadGoogleFile(Part part, String contentType, String customFileName) throws IOException;
	File uploadGoogleFile(MultipartFile part, String contentType, String customFileName) throws IOException;
}
