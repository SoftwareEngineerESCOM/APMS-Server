package com.apms.semester;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apms.rest.RESTRequest;
import com.apms.rest.RESTResponse;

@RestController
@RequestMapping("/semester")
public class SemesterRestController {

	@Autowired
	private SemesterService semesterService;

	/*
	 ** Return a listing of all the resources
	 */
	@GetMapping
	public RESTResponse<List<Semester>> getAll() {
		List<Semester> res;
		try {
			res = semesterService.getAll();
		} catch (Exception e) {
			e.printStackTrace();
			return new RESTResponse<List<Semester>>(RESTResponse.DBFAIL, "Inconsistencia en la base de datos.", null);
		}
		if (!res.isEmpty()) {
			return new RESTResponse<List<Semester>>(RESTResponse.OK, "", res);
		} else {
			return new RESTResponse<List<Semester>>(RESTResponse.FAIL, "Los catalogos necesarios no se han cargado.",
					null);
		}
	}

	/*
	 ** Return one resource
	 */
	@GetMapping("/{id}")
	public RESTResponse<Semester> getOne(@PathVariable Integer id) {
		Semester res;
		try {
			res = semesterService.getOne(id);
		} catch (Exception e) {
			e.printStackTrace();
			return new RESTResponse<Semester>(RESTResponse.DBFAIL, "Inconsistencia en la base de datos.", null);
		}
		if (res != null) {
			return new RESTResponse<Semester>(RESTResponse.OK, "", res);
		} else {
			return new RESTResponse<Semester>(RESTResponse.FAIL, "Semester no registrado.", null);
		}
	}

	/*
	 ** Store a newly created resource in storage.
	 */
	@PostMapping
	public RESTResponse<Semester> post(@RequestBody RESTRequest<Semester> semester) {
		try {
			semesterService.add(semester.getPayload());
		} catch (Exception e) {
			e.printStackTrace();
			return new RESTResponse<Semester>(RESTResponse.FAIL,
					"Hubo un error en el registro. Por favor, intentelo mas tarde.", null);
		}
		return new RESTResponse<Semester>(RESTResponse.OK, "Registro finalizado exitosamente.", null);
	}

	/*
	 ** Update the specified resource in storage partially.
	 */
	@PatchMapping
	public RESTResponse<Semester> patch(@RequestBody RESTRequest<Semester> semester) {
		try {
			semesterService.update(semester.getPayload());
		} catch (Exception e) {
			e.printStackTrace();
			return new RESTResponse<Semester>(RESTResponse.FAIL,
					"Hubo un error al modificar. Por favor, intentelo mas tarde.", null);
		}
		return new RESTResponse<Semester>(RESTResponse.OK, "Semester modificado.", null);
	}

	/*
	 ** Update the specified resource in storage.
	 */
	@PutMapping
	public RESTResponse<Semester> put(@RequestBody RESTRequest<Semester> semester) {
		try {
			semesterService.update(semester.getPayload());
		} catch (Exception e) {
			e.printStackTrace();
			return new RESTResponse<Semester>(RESTResponse.FAIL,
					"Hubo un error al modificar. Por favor, intentelo mas tarde.", null);
		}
		return new RESTResponse<Semester>(RESTResponse.OK, "Semester modificado.", null);
	}

	/*
	 ** Remove the specified resource from storage.
	 */
	@DeleteMapping("/{id}")
	public RESTResponse<Semester> delete(@PathVariable Integer id) {
		try {
			semesterService.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
			return new RESTResponse<Semester>(RESTResponse.FAIL,
					"Hubo un error en el registro. Por favor, intentelo mas tarde.", null);
		}
		return new RESTResponse<Semester>(RESTResponse.OK, "Semester modificado.", null);
	}

	@GetMapping("/SemestersByStudyPlanId/{id}")
	public RESTResponse<List<Semester>> getSemestersByStudyPlanId(@PathVariable Integer id) {
		return new RESTResponse<List<Semester>>(1, "", semesterService.getSemestersByStudyPlanId(id));
	}
}
