package com.convenience_store.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.Part;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.convenience_store.constant.GGDriveConst;
import com.convenience_store.service.GGDriveService;
import com.convenience_store.util.GGDriveUtils;
import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.Permission;

@Service
public class GGDriveServiceImpl implements GGDriveService {

	@Override
	public List<File> getAllGoogleDriveFiles() throws IOException {
		Drive driveService = GGDriveUtils.getDriveService();
		FileList result = driveService.files().list()
				.setFields("nextPageToken, files(id, name, parents, mimeType, webViewLink)").execute();
		return result.getFiles();
	}

	@Override
	public List<File> getGoogleFilesByName(String fileNameLike) throws IOException {
		Drive driveService = GGDriveUtils.getDriveService();
		String pageToken = null;
		List<File> list = new ArrayList<File>();

		String query = " name contains '" + fileNameLike + "' " //
				+ " and mimeType != 'application/vnd.google-apps.folder' ";

		do {
			FileList result = driveService.files().list().setQ(query).setSpaces("drive") //
					// Fields will be assigned values: id, name, createdTime, mimeType
					.setFields("nextPageToken, files(id, name, createdTime, mimeType, webViewLink, webContentLink)")//
					.setPageToken(pageToken).execute();
			for (File file : result.getFiles()) {
				list.add(file);
			}
			pageToken = result.getNextPageToken();
		} while (pageToken != null);
		return list;
	}

	@Override
	public File getGoogleFolderByName(String folderNameLike) throws IOException {
		Drive driveService = GGDriveUtils.getDriveService();
		String pageToken = null;
		String query = " name contains '" + folderNameLike + "' " //
				+ " and mimeType == 'application/vnd.google-apps.folder' ";
		List<File> list = new ArrayList<File>();
		do {
			FileList result = driveService.files().list().setQ(query).setSpaces("drive") //
					// Fields will be assigned values: id, name, createdTime, mimeType
					.setFields("nextPageToken, files(id, name, createdTime, mimeType, webViewLink, webContentLink)")//
					.setPageToken(pageToken).execute();
			for (File file : result.getFiles()) {
					list.add(file);
				}
			pageToken = result.getNextPageToken();
		} while (pageToken != null);
		return list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public Permission createPublicPermission(String googleFileId) throws IOException {
		// All values: user - group - domain - anyone
		String permissionType = "anyone";
		// All values: organizer - owner - writer - commenter - reader
		String permissionRole = "reader";

		Permission newPermission = new Permission();
		newPermission.setType(permissionType);
		newPermission.setRole(permissionRole);

		Drive driveService = GGDriveUtils.getDriveService();
		return driveService.permissions().create(googleFileId, newPermission).execute();
	}

	@Override
	public File uploadGoogleFile(Part part, String contentType, String customFileName) throws IOException {
		InputStream inputStream = part.getInputStream();
		AbstractInputStreamContent uploadStreamContent = new InputStreamContent(contentType, inputStream);
		return createGoogleFile(contentType, customFileName, uploadStreamContent);
	}
	
	@Override
	public File uploadGoogleFile(MultipartFile file, String contentType, String customFileName) throws IOException {
		InputStream inputStream = file.getInputStream();
		AbstractInputStreamContent uploadStreamContent = new InputStreamContent(contentType, inputStream);
		return createGoogleFile(contentType, customFileName, uploadStreamContent);
	}

	private static File createGoogleFile(String contentType, String customFileName,
			AbstractInputStreamContent uploadStreamContent) throws IOException {

		File fileMetadata = new File();
		fileMetadata.setName(customFileName);

		List<String> parents = Arrays.asList(GGDriveConst.ROOT_FOLDER_ID);
		fileMetadata.setParents(parents);

		Drive driveService = GGDriveUtils.getDriveService();

		File file = driveService.files().create(fileMetadata, uploadStreamContent)
				.setFields("id, webContentLink, webViewLink, parents").execute();

		return file;
	}
}
