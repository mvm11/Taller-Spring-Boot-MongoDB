package com.library.publiclibrary.controller;

import com.library.publiclibrary.dto.ResourceDTO;
import com.library.publiclibrary.service.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/resource")
public class ResourceController {

    Logger logger = LoggerFactory.getLogger(ResourceController.class);
    private ResourceService resourceService;

    @Autowired
    public ResourceController(ResourceService resourceService) {
            this.resourceService = resourceService;
    }

    @PostMapping("/add") public ResponseEntity<ResourceDTO> add(@RequestBody ResourceDTO resourceDTO){
        return new ResponseEntity(resourceService.save(resourceDTO), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<ResourceDTO> findAll(){
            return new ResponseEntity(resourceService.getAll(),HttpStatus.OK);
    }

    @PutMapping("/edit")
    public ResponseEntity<ResourceDTO> edit(@RequestBody ResourceDTO resourceDTO){
        if(!resourceDTO.getId().isEmpty()){
            return new ResponseEntity(resourceService.update(resourceDTO),HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") String id){
        try {
            resourceService.delete(id);
            return new ResponseEntity(HttpStatus.OK);
        }catch (Exception e){
            logger.error("error: "+e);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/availability/{id}")
    public ResponseEntity availability(@PathVariable("id") String id){
        return new ResponseEntity(resourceService.isAvailable(id), HttpStatus.OK);
    }

    @PutMapping("/lend/{id}")
    public ResponseEntity lend(@PathVariable("id")String id) {
        return new ResponseEntity(resourceService.lend(id), HttpStatus.OK);
    }

    @PutMapping("/giveback/{id}")
    public ResponseEntity devolver(@PathVariable("id")String id) {
        return new ResponseEntity(resourceService.giveBack(id), HttpStatus.OK);
    }

    @GetMapping("/get/{type}")
    public ResponseEntity<ResourceDTO> getBytype(@PathVariable String type) {
        return new ResponseEntity(resourceService.recommendByType(type), HttpStatus.OK);
    }

    @GetMapping("/get/{thematic}")
    public ResponseEntity<ResourceDTO> getByThematic(@PathVariable String thematic) {
        return new ResponseEntity(resourceService.recommendByTheme(thematic), HttpStatus.OK);
    }

    @GetMapping("/get/{type}/{thematic}")
    public ResponseEntity<ResourceDTO> getByTypeAndSubjectArea( @PathVariable("type") String type,@PathVariable("thematic") String thematic){
        return new ResponseEntity(resourceService.recommendByTypeAndTheme(type,thematic),HttpStatus.OK);
    }
}
