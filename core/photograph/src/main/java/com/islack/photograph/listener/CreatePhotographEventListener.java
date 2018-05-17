package com.islack.photograph.listener;

import com.islack.photograph.domain.entity.Photograph;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler(Photograph.class)
public class CreatePhotographEventListener {

    @HandleAfterCreate
    protected void onAfterCreate(Photograph entity) {
        System.out.println(entity);
        // TO-DO: send info to AI microservice
    }

}
