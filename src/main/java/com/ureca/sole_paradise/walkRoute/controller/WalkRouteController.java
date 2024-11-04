package com.ureca.sole_paradise.walkRoute.controller;

import com.ureca.sole_paradise.walkRoute.db.dto.WalkRouteDTO;
import com.ureca.sole_paradise.walkRoute.service.WalkRouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/walkRoutes")
@RequiredArgsConstructor
public class WalkRouteController {

    private final WalkRouteService walkRouteService;

    @PostMapping
    public ResponseEntity<WalkRouteDTO> registerWalkRoute(@RequestBody WalkRouteDTO walkRouteDTO) {
        WalkRouteDTO createdWalkRoute = walkRouteService.registerWalkRoute(walkRouteDTO);
        return new ResponseEntity<>(createdWalkRoute, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<WalkRouteDTO>> getAllWalkRoutes() {
        List<WalkRouteDTO> walkRoutes = walkRouteService.getAllWalkRoutes();
        return new ResponseEntity<>(walkRoutes, HttpStatus.OK);
    }

    @DeleteMapping("/{walkrouteId}")
    public ResponseEntity<Void> deleteWalkRoute(@PathVariable Integer walkrouteId) {
        walkRouteService.deleteWalkRoute(walkrouteId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
