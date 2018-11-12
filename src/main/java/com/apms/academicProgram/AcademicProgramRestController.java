package com.apms.academicProgram;

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

import com.apms.restResponse.RESTRequest;
import com.apms.restResponse.RESTResponse;

@RestController
@RequestMapping("/AcademicProgram")
public class AcademicProgramRestController {

	@Autowired
	private AcademicProgramService academicProgramService;

	/*
	 ** Return a listing of all the resources
	 */
	@GetMapping
	public RESTResponse<List<AcademicProgram>> getAll() {
		return new RESTResponse<List<AcademicProgram>>(1, "", academicProgramService.getAll());
	}

	/*
	 ** Return one resource
	 */
	@GetMapping("/{id}")
	public RESTResponse<AcademicProgram> getOne(@PathVariable Integer id) {
		return new RESTResponse<AcademicProgram>(1, "", academicProgramService.getOne(id));
	}

	/*
	 ** Store a newly created resource in storage.
	 */
	@PostMapping
	public void add(@RequestBody RESTRequest<AcademicProgram> req) {
		academicProgramService.add(req.getPayload());
	}

	/*
	 ** Update the specified resource in storage.
	 */
	@PatchMapping
	public void update(@RequestBody RESTRequest<AcademicProgram> req) {
		academicProgramService.update(req.getPayload());
	}

	/*
	 ** Remove the specified resource from storage.
	 */
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Integer id) {
		academicProgramService.delete(id);
	}

	@GetMapping("/AcademicProgramsByWorkPlaceId/{id}")
	public RESTResponse<List<AcademicProgram>> getAcademicProgramsByWorkPlaceId(@PathVariable Integer id) {
		return new RESTResponse<List<AcademicProgram>>(1, "",
				academicProgramService.getAcademicProgramsByWorkPlaceId(id));
	}
}
