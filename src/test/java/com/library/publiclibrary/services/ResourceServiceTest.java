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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
    @DisplayName("It tests the resource's availability")
    void availabilityTest() {
        // Arrange
        var resource1 = new ResourceDTO();
        resource1.setId("1");
        resource1.setTitle("Viaje a pie");
        resource1.setType("Literatura");
        resource1.setThematic("Relato");
        resource1.setLoanDate(LocalDate.now());
        resource1.setLent(Boolean.FALSE);
        resource1.setAvailable(Boolean.TRUE);
        when(repository.findById(resource1.getId())).thenReturn(resources().stream().findFirst());
        // Act
        var result = resourceService.isAvailable(resource1.getId());
        // Assert
        Assertions.assertEquals("The resource is" + resources().stream().findFirst().get().isAvailable(), result);
    }

    private List<Resource> resources() {

        var resource1 = new Resource();
        resource1.setId("216");
        resource1.setTitle("El hombre de busca de sentido");
        resource1.setType("Psicologia");
        resource1.setThematic("Relato");
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
        resource2.setAvailable(Boolean.TRUE);

        var recursos = new ArrayList<Resource>();
        recursos.add(resource1);
        recursos.add(resource2);
        return recursos;
    }

}
