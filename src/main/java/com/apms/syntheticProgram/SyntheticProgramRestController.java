package com.apms.syntheticProgram;

import java.util.Iterator;
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

import com.apms.content.Content;
import com.apms.content.ContentService;
import com.apms.evaluationAccreditationUA.EvaluationAccreditationUAService;
import com.apms.rest.RESTRequest;
import com.apms.rest.RESTResponse;

@RestController
@RequestMapping("/syntheticProgram")
public class SyntheticProgramRestController {

	@Autowired
	private SyntheticProgramService syntheticProgramService;

	@Autowired
	private ContentService contentService;

	@Autowired
	private EvaluationAccreditationUAService evaluationAccreditationUAService;

	/*
	 ** Return a listing of all the resources
	 */
	@GetMapping
	public RESTResponse<List<SyntheticProgram>> getAll() {
		List<SyntheticProgram> res;
		try {
			res = syntheticProgramService.getAll();
		} catch (Exception e) {
			e.printStackTrace();
			return new RESTResponse<List<SyntheticProgram>>(RESTResponse.DBFAIL, "Inconsistencia en la base de datos.",
					null);
		}
		if (!res.isEmpty()) {
			return new RESTResponse<List<SyntheticProgram>>(RESTResponse.OK, "", res);
		} else {
			return new RESTResponse<List<SyntheticProgram>>(RESTResponse.FAIL, "Servicios no disponibles.", null);
		}
	}

	/*
	 ** Return one resource
	 */
	@GetMapping("/{id}")
	public RESTResponse<SyntheticProgram> getOne(@PathVariable Integer id) {
		SyntheticProgram res;
		try {
			res = syntheticProgramService.getOne(id);
		} catch (Exception e) {
			e.printStackTrace();
			return new RESTResponse<SyntheticProgram>(RESTResponse.DBFAIL, "Inconsistencia en la base de datos.", null);
		}
		if (res != null) {
			return new RESTResponse<SyntheticProgram>(RESTResponse.OK, "", res);
		} else {
			return new RESTResponse<SyntheticProgram>(RESTResponse.FAIL, "Programa sintetico no registrado.", null);
		}
	}

	/*
	 ** Store a newly created resource in storage.
	 */
	@PostMapping
	public RESTResponse<SyntheticProgram> post(@RequestBody RESTRequest<SyntheticProgram> req) {
		try {
			if (syntheticProgramService.getOne(req.getPayload().getId()) != null)
				return new RESTResponse<SyntheticProgram>(RESTResponse.FAIL,
						"El Programa sintetico  ya existe en el sistema.", null);
			for (Iterator<Content> i = req.getPayload().getContent().iterator(); i.hasNext();)
				i.next().setId(contentService.add(i.next()).getId());
			req.getPayload().getEvaluationAccreditationUA().setId(
					evaluationAccreditationUAService.add(req.getPayload().getEvaluationAccreditationUA()).getId());
			syntheticProgramService.add(req.getPayload());
		} catch (Exception e) {
			e.printStackTrace();
			return new RESTResponse<SyntheticProgram>(RESTResponse.FAIL,
					"Hubo un error en el registro. Por favor, intentelo mas tarde.", null);
		}
		return new RESTResponse<SyntheticProgram>(RESTResponse.OK, "Registro finalizado exitosamente.", null);
	}

	/*
	 ** Update the specified resource in storage partially.
	 */
	@PatchMapping
	public RESTResponse<SyntheticProgram> patch(@RequestBody RESTRequest<SyntheticProgram> req) {
		try {
			syntheticProgramService.update(req.getPayload());
		} catch (Exception e) {
			e.printStackTrace();
			return new RESTResponse<SyntheticProgram>(RESTResponse.FAIL,
					"Hubo un error al modificar. Por favor, intentelo mas tarde.", null);
		}
		return new RESTResponse<SyntheticProgram>(RESTResponse.OK, "Programa sintetico modificado.", null);
	}

	/*
	 ** Update the specified resource in storage.
	 */
	@PutMapping
	public RESTResponse<SyntheticProgram> put(@RequestBody RESTRequest<SyntheticProgram> req) {
		try {
			syntheticProgramService.update(req.getPayload());
		} catch (Exception e) {
			e.printStackTrace();
			return new RESTResponse<SyntheticProgram>(RESTResponse.FAIL,
					"Hubo un error al modificar. Por favor, intentelo mas tarde.", null);
		}
		return new RESTResponse<SyntheticProgram>(RESTResponse.OK, "Programa sintetico modificado.", null);
	}

	/*
	 ** Remove the specified resource from storage.
	 */
	@DeleteMapping("/{id}")
	public RESTResponse<SyntheticProgram> delete(@PathVariable Integer id) {
		try {
			syntheticProgramService.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
			return new RESTResponse<SyntheticProgram>(RESTResponse.FAIL,
					"Hubo un error en el registro. Por favor, intentelo mas tarde.", null);
		}
		return new RESTResponse<SyntheticProgram>(RESTResponse.OK, "Programa sintetico modificado.", null);
	}
}
