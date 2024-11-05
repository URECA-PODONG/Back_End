package com.ureca.sole_paradise.petItem.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ureca.sole_paradise.petItem.db.dto.PetItemCommentDTO;
import com.ureca.sole_paradise.petItem.service.PetItemCommentService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;


@RestController
@RequestMapping(value = "/petItemComments", produces = MediaType.APPLICATION_JSON_VALUE)
public class PetItemCommentController {


    private final PetItemCommentService petItemCommentService;

    public PetItemCommentController(final PetItemCommentService petItemCommentService) {
        this.petItemCommentService = petItemCommentService;
    }

    @GetMapping
    public ResponseEntity<List<PetItemCommentDTO>> getAllPetItemComments() {
        return ResponseEntity.ok(petItemCommentService.findAll());
    }

    @GetMapping("/{petItemCommentId}")
    public ResponseEntity<PetItemCommentDTO> getPetItemComment(
            @PathVariable(name = "petItemCommentId") final Integer petItemCommentId) {
        return ResponseEntity.ok(petItemCommentService.get(petItemCommentId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createPetItemComment(
            @RequestBody @Valid final PetItemCommentDTO petItemCommentDTO) {
        final Integer createdPetItemCommentId = petItemCommentService.create(petItemCommentDTO);
        return new ResponseEntity<>(createdPetItemCommentId, HttpStatus.CREATED);
    }

    @PutMapping("/{petItemCommentId}")
    public ResponseEntity<Integer> updatePetItemComment(
            @PathVariable(name = "petItemCommentId") final Integer petItemCommentId,
            @RequestBody @Valid final PetItemCommentDTO petItemCommentDTO) {
        petItemCommentService.update(petItemCommentId, petItemCommentDTO);
        return ResponseEntity.ok(petItemCommentId);
    }

    @DeleteMapping("/{petItemCommentId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePetItemComment(
            @PathVariable(name = "petItemCommentId") final Integer petItemCommentId) {
        petItemCommentService.delete(petItemCommentId);
        return ResponseEntity.noContent().build();
    }
	
}
