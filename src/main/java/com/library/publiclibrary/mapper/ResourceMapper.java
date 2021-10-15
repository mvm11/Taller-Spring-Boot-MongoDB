package com.library.publiclibrary.mapper;

import com.library.publiclibrary.dto.ResourceDTO;
import com.library.publiclibrary.model.Resource;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class ResourceMapper {

    private ModelMapper mapper;

    public ResourceMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public ResourceDTO fromDTO(Resource resource){
        ResourceDTO resourceDTO = mapper.map(resource, ResourceDTO.class);
        return resourceDTO;
    }
    public Resource fromModel(ResourceDTO resourceDTO){
        Resource resource= mapper.map(resourceDTO,Resource.class);
        return resource;
    }
}
