package com.apms.message;

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

import com.apms.rest.RESTRequest;import java.util.logging.Logger;
import com.apms.rest.RESTResponse;

@RestController
@RequestMapping("/message")
public class MessageRestController {

	@Autowired
	private MessageService messageService;

	/*
	 ** Return a listing of all the resources
	 */
	@GetMapping
	public RESTResponse<List<Message>> getAll() {
		List<Message> res;
		try {
			res = messageService.getAll();
		} catch (Exception e) {
			Logger.getLogger(null).log(null,"F: ",e);
			return new RESTResponse<List<Message>>(RESTResponse.DBFAIL, "Inconsistencia en la base de datos.", null);
		}
		if (!res.isEmpty()) {
			return new RESTResponse<List<Message>>(RESTResponse.OK, "", res);
		} else {
			return new RESTResponse<List<Message>>(RESTResponse.FAIL, "Servicios no disponibles.",
					null);
		}
	}

	/*
	 ** Return one resource
	 */
	@GetMapping("/{id}")
	public RESTResponse<Message> getOne(@PathVariable Integer id) {
		Message res;
		try {
			res = messageService.getOne(id);
		} catch (Exception e) {
			Logger.getLogger(null).log(null,"F: ",e);
			return new RESTResponse<Message>(RESTResponse.DBFAIL, "Inconsistencia en la base de datos.", null);
		}
		if (res != null) {
			return new RESTResponse<Message>(RESTResponse.OK, "", res);
		} else {
			return new RESTResponse<Message>(RESTResponse.FAIL, "Mensaje no registrado.", null);
		}
	}

	/*
	 ** Store a newly created resource in storage.
	 */
	@PostMapping
	public RESTResponse<Message> post(@RequestBody RESTRequest<Message> message) {
		try {
			if(messageService.getOne(message.getPayload().getId()) != null)
                return new RESTResponse<Message>(RESTResponse.FAIL, "El mensaje ya existe en el sistema.", null);
			messageService.add(message.getPayload());
		} catch (Exception e) {
			Logger.getLogger(null).log(null,"F: ",e);
			return new RESTResponse<Message>(RESTResponse.FAIL,
					"Por el momento no se puede realizar el registro.", null);
		}
		return new RESTResponse<Message>(RESTResponse.OK, "Registro finalizado exitosamente.", null);
	}

	/*
	 ** Update the specified resource in storage partially.
	 */
	@PatchMapping
	public RESTResponse<Message> patch(@RequestBody RESTRequest<Message> message) {
		try {
			messageService.update(message.getPayload());
		} catch (Exception e) {
			Logger.getLogger(null).log(null,"F: ",e);
			return new RESTResponse<Message>(RESTResponse.FAIL,
					"Hubo un error al modificar. Por favor, intentelo mas tarde.", null);
		}
		return new RESTResponse<Message>(RESTResponse.OK, "Los cambios se guardaron exitosamente.", null);
	}

	/*
	 ** Update the specified resource in storage.
	 */
	@PutMapping
	public RESTResponse<Message> put(@RequestBody RESTRequest<Message> message) {
		try {
			messageService.update(message.getPayload());
		} catch (Exception e) {
			Logger.getLogger(null).log(null,"F: ",e);
			return new RESTResponse<Message>(RESTResponse.FAIL,
					"Hubo un error al modificar. Por favor, intentelo mas tarde.", null);
		}
		return new RESTResponse<Message>(RESTResponse.OK, "Los cambios se guardaron exitosamente.", null);
	}

	/*
	 ** Remove the specified resource from storage.
	 */
	@DeleteMapping("/{id}")
	public RESTResponse<Message> delete(@PathVariable Integer id) {
		try {
			messageService.delete(id);
		} catch (Exception e) {
			Logger.getLogger(null).log(null,"F: ",e);
			return new RESTResponse<Message>(RESTResponse.FAIL,
					"Por el momento no se puede realizar el registro.", null);
		}
		return new RESTResponse<Message>(RESTResponse.OK, "Los cambios se guardaron exitosamente.", null);
	}
}
