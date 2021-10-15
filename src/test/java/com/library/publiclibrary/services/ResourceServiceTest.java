package com.library.publiclibrary.services;

import com.library.publiclibrary.dto.ResourceDTO;
import com.library.publiclibrary.mapper.ResourceMapper;
import com.library.publiclibrary.model.Resource;
import com.library.publiclibrary.repository.ResourceRepository;
import com.library.publiclibrary.service.ResourceService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;


@SpringBootTest
public class ResourceServiceTest {

    @MockBean
    private ResourceRepository repository;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private ResourceMapper mapper;

    @Test
    @DisplayName("Save resource")
    void save(){
        //Arrange
        var resource1 = new ResourceDTO();
        resource1.setId("216");
        resource1.setTitle("Fahrenheit 451");
        resource1.setType("libro");
        resource1.setThematic("Novela");
        resource1.setLoanDate(LocalDate.now());
        resource1.setLent(Boolean.FALSE);
        resource1.setAvailable(Boolean.TRUE);
        Mockito.when(repository.save(Mockito.any())).thenReturn(resources().get(0));
        // Act
        var result = resourceService.save(resource1);
        //Assert

        Assertions.assertEquals("Fahrenheit 451", result.getTitle());
        Assertions.assertEquals("libro", result.getType());
        Assertions.assertEquals("Novela", result.getThematic());
        Assertions.assertEquals(LocalDate.now(), result.getLoanDate());
        Assertions.assertEquals(Boolean.FALSE,result.isLent());
        Assertions.assertEquals(Boolean.TRUE,result.isAvailable());
    }

    @Test
    @DisplayName("Get All resources")
    void getAll(){

        Mockito.when(repository.findAll()).thenReturn(resources());

        var result = resourceService.getAll();

        Assertions.assertEquals("216", result.get(0).getId());
        Assertions.assertEquals("Fahrenheit 451", result.get(0).getTitle());
        Assertions.assertEquals("libro", result.get(0).getType());
        Assertions.assertEquals("Novela", result.get(0).getThematic());
        Assertions.assertEquals(LocalDate.now(), result.get(0).getLoanDate());
        Assertions.assertEquals(Boolean.FALSE,result.get(0).isLent());
        Assertions.assertEquals(Boolean.TRUE,result.get(0).isAvailable());
    }

    @Test
    @DisplayName("Get resources by id")
    void getById() {

        Mockito.when(repository.findById(Mockito.any())).thenReturn(resources().stream().findFirst());

        var result = resourceService.getById(resources().get(0).getId());

        Assertions.assertEquals("Fahrenheit 451", result.getTitle());
        Assertions.assertEquals("libro", result.getType());
        Assertions.assertEquals("Novela", result.getThematic());
        Assertions.assertEquals(LocalDate.now(), result.getLoanDate());
        Assertions.assertEquals(Boolean.FALSE,result.isLent());
        Assertions.assertEquals(Boolean.TRUE,result.isAvailable());
    }

    @Test
    @DisplayName("Update resource")
    void update() {
        var recurso2 = new ResourceDTO();
        recurso2.setId("216");
        recurso2.setTitle("Fahrenheit 451");
        recurso2.setType("libro");
        recurso2.setThematic("Novela");
        recurso2.setLoanDate(LocalDate.now());
        recurso2.setLent(Boolean.FALSE);
        recurso2.setAvailable(Boolean.TRUE);

        Mockito.when(repository.save(Mockito.any())).thenReturn(mapper.fromModel(recurso2));
        Mockito.when(repository.findById(recurso2.getId())).thenReturn(resources().stream().findFirst());
        var result = resourceService.update(recurso2);

        Assertions.assertEquals("Fahrenheit 451", result.getTitle());
        Assertions.assertEquals("libro", result.getType());
        Assertions.assertEquals("Novela", result.getThematic());
        Assertions.assertEquals(LocalDate.now(), result.getLoanDate());
        Assertions.assertEquals(Boolean.FALSE,result.isLent());
        Assertions.assertEquals(Boolean.TRUE,result.isAvailable());
    }

    @Test
    @DisplayName("Delete resource")
    void delete(){
        var resource3 = new ResourceDTO();
        resource3.setId("216");

        Mockito.when(repository.findById(resource3.getId())).thenReturn(resources().stream().findFirst());
        resourceService.delete("216");
        Mockito.verify(repository).deleteById("216");
    }



    @Test
    @DisplayName("It tests the resource's availability")
    void availabilityTest() {
        // Arrange
        var resource6 = new ResourceDTO();
        resource6.setId("10");
        resource6.setTitle("Viaje a pie");
        resource6.setType("libro");
        resource6.setThematic("Relato");
        resource6.setLoanDate(LocalDate.now());
        resource6.setLent(Boolean.FALSE);
        resource6.setAvailable(Boolean.TRUE);
        when(repository.findById(resource6.getId())).thenReturn(resources().stream().findFirst());
        // Act
        var result = resourceService.isAvailable(resource6.getId());
        // Assert
        Assertions.assertEquals("The resource is available", result);
    }

    private List<Resource> resources() {

        var resource1 = new Resource();
        resource1.setId("216");
        resource1.setTitle("Fahrenheit 451");
        resource1.setType("libro");
        resource1.setThematic("Novela");
        resource1.setLoanDate(LocalDate.now());
        resource1.setLent(Boolean.FALSE);
        resource1.setAvailable(Boolean.TRUE);

        var resource2 = new Resource();
        resource2.setId("21");
        resource2.setTitle("Manga");
        resource2.setType("La duquesa de alma de vacía");
        resource2.setThematic("Novela");
        resource2.setLoanDate(LocalDate.now());
        resource2.setLent(Boolean.FALSE);
        resource2.setAvailable(Boolean.FALSE);

        var recursos = new ArrayList<Resource>();
        recursos.add(resource1);
        recursos.add(resource2);
        return recursos;
    }

}
