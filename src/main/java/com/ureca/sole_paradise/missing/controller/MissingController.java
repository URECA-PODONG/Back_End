package com.ureca.sole_paradise.missing.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ureca.sole_paradise.missing.db.dto.MissingDTO;
import com.ureca.sole_paradise.missing.service.MissingService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/missings", produces = MediaType.APPLICATION_JSON_VALUE)
public class MissingController {
	private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";

	@Autowired
	private final MissingService missingService;

	public MissingController(final MissingService missingService) {
	    this.missingService = missingService;
	}

	@GetMapping
	public ResponseEntity<List<MissingDTO>> getAllMissings() {
	    return ResponseEntity.ok(missingService.findAll());
	}

	@GetMapping("/{missingId}")
	public ResponseEntity<MissingDTO> getMissing(
	        @PathVariable(name = "missingId") final Integer missingId) {
	    return ResponseEntity.ok(missingService.get(missingId));
	}

	@PostMapping
	public ResponseEntity<?> uploadMissingPet(
	        @RequestParam("petId") int petId,
	        @RequestParam("walkroute") int walkroute,
	        @RequestParam("alarmName") String alarmName,
	        @RequestParam("location") String location,
	        @RequestParam("missingDate") OffsetDateTime missingDate,
	        @RequestParam("createdAt") OffsetDateTime createdAt,
	        @RequestParam("alertradiuskm") int alertradiuskm,
	        @RequestParam("missingstatus") int missingstatus,
	        @RequestParam("missingDetails") String missingDetails,
			@RequestParam("contactNumber") String contactNumber,
	        @RequestParam(value = "imageUrl", required = false) MultipartFile file) {

	    try {
	        MissingDTO missingDTO = new MissingDTO();
	        missingDTO.setAlarmName(alarmName);
	        missingDTO.setLocation(location);
	        missingDTO.setMissingDate(missingDate);
	        missingDTO.setAlertRadiusKm(alertradiuskm);
	        missingDTO.setMissingStatus(missingstatus);
	        missingDTO.setMissingDetails(missingDetails);
	        missingDTO.setCreatedAt(createdAt);
	        missingDTO.setPet(petId);
			missingDTO.setContactNumber(contactNumber);
	        missingDTO.setWalkroute(walkroute);


	        if (file != null && !file.isEmpty()) {
	            String fileName = System.currentTimeMillis() + "";
	            String[] exts = file.getOriginalFilename().split("\\.");
	            String ext = exts[exts.length - 1]; // 확장자
	            Path filePath = Paths.get(UPLOAD_DIR + fileName + "." + ext);
	            Files.createDirectories(filePath.getParent());
	            Files.copy(file.getInputStream(), filePath);
	            String fp = filePath.toString();
	            System.out.println("fp=" + fp);
	            int staticIndex = fp.lastIndexOf("uploads");
	            String ss = fp.substring(staticIndex + 8);
	            missingDTO.setAlarmPicture(ss);
	            System.out.println("ss=" + ss);
	        }

	        final Integer createdMissingId = missingService.create(missingDTO);
	        return new ResponseEntity<>(createdMissingId, HttpStatus.CREATED);
	    } catch (IOException e) {
	        return ResponseEntity.internalServerError().body("Could not upload the petItem: " + e.getMessage());
	    }
	}

	@ApiResponse(responseCode = "201")
	public ResponseEntity<Integer> createMissing(@RequestBody @Valid final MissingDTO missingDTO) {
	    final Integer createdMissingId = missingService.create(missingDTO);
	    return new ResponseEntity<>(createdMissingId, HttpStatus.CREATED);
	}

	@PutMapping("/{missingId}")
	public ResponseEntity<Integer> updateMissing(
	        @PathVariable(name = "missingId") final Integer missingId,
	        @RequestBody @Valid final MissingDTO missingDTO) {
	    missingService.update(missingId, missingDTO);
	    return ResponseEntity.ok(missingId);
	}

	@DeleteMapping("/{missingId}")
	@ApiResponse(responseCode = "204")
	public ResponseEntity<Void> deleteMissing(
	        @PathVariable(name = "missingId") final Integer missingId) {
	    missingService.delete(missingId);
	    return ResponseEntity.noContent().build();
	}

}
