package com.apms.humanResource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/HumanResource")
public class HumanResourceRestController {
	
    @Autowired
    private HumanResourceService humanResourceService;

    /*
    **Return a listing of all the resources
    */
    @GetMapping
    public List<HumanResource> getAll() {
        return humanResourceService.getAll();
    }

    /*
    **Return one resource
    */
    @GetMapping("/{id}")
    public HumanResource getOne(@PathVariable Long id) {
        return humanResourceService.getOne(id);
    }

    /*
    **Store a newly created resource in storage.
    */
    @PostMapping
    public void add(@RequestBody HumanResource humanResource) {
        humanResourceService.add(humanResource);
    }

    /*
    **Update the specified resource in storage.
    */
    @PatchMapping
    public void update(@RequestBody HumanResource humanResource) {
        humanResourceService.update(humanResource);
    }

    /*
    **Remove the specified resource from storage.
    */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        humanResourceService.delete(id);
    }
}
