package com.islack.photograph.api;

import com.islack.photograph.domain.entity.Category;
import com.islack.photograph.domain.entity.Photograph;
import com.islack.photograph.domain.entity.Tag;
import com.islack.photograph.repository.PhotographRepository;
import com.islack.photograph.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("photographs")
public class PhotographController {

    @Autowired
    TagRepository tagRepository;

    @Autowired
    PhotographRepository photographRepository;
    @Autowired
    private Environment environment;

    @RequestMapping("test")
    public ResponseEntity<String> test() {
        String test = environment.getProperty("my.property.test");
        Photograph p = new Photograph();
        return new ResponseEntity<>(test, HttpStatus.OK);
    }

    @GetMapping("test2")
    public List<Tag> test2() {
        List<Tag> list = new ArrayList<>();
        String[] tags = {"tag1", "tag4", "tag9", "tag10"};
        for (String tag : tags) {
            Tag t = new Tag();
            t.setTag(tag);
            list.add(t);
        }

        return tagRepository.findOrCreate(list);
    }

    @PostMapping("test31")
    public List<Tag> test31(@RequestParam("tags") List<Tag> tags, @RequestParam("categories")List<Category> categories) {
        return tags;
    }

    @PostMapping("test32")
    public List<Category> test32(@RequestParam("tags") List<Tag> tags, @RequestParam("categories")List<Category> categories) {
        return categories;
    }
    @PostMapping("test4")
    public Photograph test4(Photograph photograph) {
        return photographRepository.save(photograph);
    }

}
