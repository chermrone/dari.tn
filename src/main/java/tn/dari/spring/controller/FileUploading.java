package tn.dari.spring.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import tn.dari.spring.controller.utils.FilenameUtils;
import tn.dari.spring.dto.FournitureAdDto;
import tn.dari.spring.entity.FournitureAd;
import tn.dari.spring.entity.LocalFile;
import tn.dari.spring.repository.LocalFileRepository;
import tn.dari.spring.service.FournitureAdService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/dari/File")
public class FileUploading {

	@Autowired
	FournitureAdService fournitureAdService;
	@Autowired
	LocalFileRepository localFileRepository;

	private static final Logger log = LoggerFactory.getLogger(FileUploading.class);
	@Value("${UPLOADED_FOLDER}")
	public String UPLOADED_FOLDER;
	// public String UPLOADED_FOLDER = "C:\\Users\\Seif\\Desktop\\file";

	@PostMapping("/upload") // new annotation since 4.3
	public String singleFileUpload(@RequestParam("previewFile") MultipartFile previewFile,
			@RequestParam("faID") String faID) {

		if (previewFile.isEmpty()) {
			return "File is Empty";
		}
		log.info("going to try upload");
		try {
			log.info(previewFile.getOriginalFilename());
			String extension = FilenameUtils.getExtension(previewFile.getOriginalFilename());

			String FolderName = UPLOADED_FOLDER + "\\fournitureAd\\" + faID + extension;
			//String FolderName = UPLOADED_FOLDER + "//fournitureAd//" + faID + extension;

			// Get the file and save it somewhere
			byte[] bytes = previewFile.getBytes();
			Files.createDirectories(Paths.get(FolderName));

			File F = new File(FolderName);
			int num = 1;
			if (F.list().length != 0) {
				List<Integer> nums = new ArrayList<>();
				for (String name : F.list()) {
					nums.add(Integer.parseInt(name.split("fournitureAd_")[1].split("_")[0]));
				}
				Collections.sort(nums);
				num = nums.get(nums.size() - 1) + 1;
				log.info("id file is " + num);

			}

			String FileName = FolderName + "\\fournitureAd_" + num + "_preview_File." + extension;
			// String FileName = FolderName + "//fournitureAd_" + num + "_preview_File." + extension;

			Path path = Paths.get(FileName);
			Files.write(path, bytes);
			FournitureAd c = null;
			try {
				c = fournitureAdService.getAdById(Long.parseLong(faID));
			} catch (Exception e) {
				log.info("fourniture ad not found");
			}
			LocalFile l = new LocalFile();
			l.setPath(FileName);
			if (c != null) {
				l.setFournitureAd(c);
				localFileRepository.save(l);
				return "Success";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "Done";
	}

	@DeleteMapping("/delete")
	@Transactional
	public Boolean deleteByPath(@RequestBody FileDelRequest request) {
		// invert slash for windows or linux
		 String path = UPLOADED_FOLDER.replace("assets\\file", "") + request.getPath();
		//String path = UPLOADED_FOLDER.replace("assets/file", "") + request.getPath();
		File F = new File(path);
		F.delete();
		log.info("path: " + path);
		// path = path.replace("\\", "\\");
		int result = localFileRepository.deleteByPath(path);
		return result == 1;

	}

}
