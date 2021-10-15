package com.library.publiclibrary.service;

import com.library.publiclibrary.dto.ResourceDTO;
import com.library.publiclibrary.mapper.ResourceMapper;
import com.library.publiclibrary.model.Resource;
import com.library.publiclibrary.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class ResourceService {
    private ResourceRepository repository;
    private ResourceMapper mapper;

    @Autowired
    public ResourceService(ResourceRepository repository, ResourceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public ResourceDTO save(ResourceDTO resourceDTO) {
        if (resourceDTO.getTitle().isEmpty()) {
            throw new IllegalArgumentException("The title cannot be empty");
        }
        Resource resource = mapper.fromModel(resourceDTO);
        return mapper.fromDTO(repository.save(resource));
    }

    public List<ResourceDTO> getAll() {
        List<ResourceDTO> resourceDTOS = new ArrayList<>();
        repository.findAll().forEach(resource -> resourceDTOS.add(mapper.fromDTO(resource)));
        return resourceDTOS;
    }

    public ResourceDTO getById(String id) {
        Optional<Resource> resource = repository.findById(id);
        if (resource.isEmpty()) {
            throw new NoSuchElementException("The resource with the: " + id + " doesn't exist");
        }
        return mapper.fromDTO(resource.get());
    }

    public ResourceDTO update(ResourceDTO resourceDTO) {
        Resource resource = mapper.fromModel(resourceDTO);
        getById(resource.getId());
        return mapper.fromDTO(repository.save(resource));
    }

    public void delete(String id) {
        repository.delete(mapper.fromModel(getById(id)));
    }


    public String isAvailable(String id) {
        Resource resource = repository.findById(id).orElseThrow(() -> new RuntimeException("Resource not found"));
        if (resource.isAvailable() == true) {
            return "The resource is available";
        } else {
            return "The resource isn't available since: " + resource.getLoanDate();
        }
    }

    public List<ResourceDTO> recommendByType(String type) {
        List<ResourceDTO> resourceDTOS = new ArrayList<>();
        repository.findByType(type).forEach(resource -> resourceDTOS.add(mapper.fromDTO(resource)));
        return resourceDTOS;
    }

    public List<ResourceDTO> recommendByTheme(String theme) {
        List<ResourceDTO> resourceDTOS = new ArrayList<>();
        repository.findByThematic(theme).forEach(resource -> resourceDTOS.add(mapper.fromDTO(resource)));
        return resourceDTOS;
    }

    public List<ResourceDTO> recommendByTypeAndTheme(String type, String theme) {
        List<ResourceDTO> resourceDTOS = new ArrayList<>();
        List<ResourceDTO> resourceAux = new ArrayList<>();
        resourceAux.addAll(recommendByTheme(theme));
        resourceAux.addAll(recommendByType(type));
        resourceAux.stream().distinct().forEach(resourceDTO -> resourceDTOS.add(resourceDTO));
        return resourceDTOS;
    }

    public String lend(String id){
        ResourceDTO resourceDTO = getById(id);
        Resource resource = repository.findById(id).orElseThrow(()
                -> new RuntimeException("Resource not found"));
        if (resource.isAvailable() == true){
            resource.setLent(Boolean.TRUE);
            repository.save(resource);
            return "The resource has been lent";
        }else {
            return "The resource cannot be lent";
        }
    }

    public String giveBack(String id){
        Resource resource = repository.findById(id).orElseThrow(()
                -> new RuntimeException("Resource not found"));
        if (resource.isLent() == true){
            return "You can give back the resource.";
        }else {
            resource.setAvailable(Boolean.TRUE);
            repository.save(resource);
            return "The resource hasn't been given back.";
        }
    }
}
