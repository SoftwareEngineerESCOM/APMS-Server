package com.apms.user;

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

import com.apms.humanResource.HumanResourceService;
import com.apms.humanResource.HumanResource;

import com.apms.rest.RESTRequest;import java.util.logging.Logger;
import com.apms.rest.RESTResponse;

@RestController
@RequestMapping("/user")
public class UserRestController {

	@Autowired
	private UserService userService;

	@Autowired
	private HumanResourceService humanResourceService;

	/*
	 ** Return a listing of all the resources
	 */
	@GetMapping
	public RESTResponse<List<User>> getAll() {
		List<User> res;
		try {
			res = userService.getAll();
		} catch (Exception e) {
			Logger.getLogger(null).log(null,"F: ",e);
			return new RESTResponse<List<User>>(RESTResponse.DBFAIL, "Inconsistencia en la base de datos.", null);
		}
		if (!res.isEmpty()) {
			return new RESTResponse<List<User>>(RESTResponse.OK, "", res);
		} else {
			return new RESTResponse<List<User>>(RESTResponse.FAIL, "Servicios no disponibles.", null);
		}
	}

	/*
	 ** Return one resource
	 */
	@GetMapping("/{id}")
	public RESTResponse<User> getOne(@PathVariable Integer id) {
		User res;
		try {
			res = userService.getOne(id);
		} catch (Exception e) {
			Logger.getLogger(null).log(null,"F: ",e);
			return new RESTResponse<User>(RESTResponse.DBFAIL, "Inconsistencia en la base de datos.", null);
		}
		if (res != null) {
			return new RESTResponse<User>(RESTResponse.OK, "", res);
		} else {
			return new RESTResponse<User>(RESTResponse.FAIL, "Usuario no registrado.", null);
		}
	}

	/*
	 ** Store a newly created resource in storage.
	 */
	@PostMapping
	public RESTResponse<User> post(@RequestBody RESTRequest<User> req) {
		try {
			User dbUser = userService.getOne(req.getPayload().getId());
			if (dbUser != null || userService.getByEmail(req.getPayload().getEmail()) != null) {
				return new RESTResponse<User>(RESTResponse.FAIL, "El usuario ya existe en el sistema.", null);
			}
			req.getPayload().getHumanResource()
					.setId(humanResourceService.add(req.getPayload().getHumanResource()).getId());
			userService.add(req.getPayload());
		} catch (Exception e) {
			Logger.getLogger(null).log(null,"F: ",e);
			return new RESTResponse<User>(RESTResponse.FAIL, "Por el momento no se puede realizar el registro.", null);
		}
		return new RESTResponse<User>(RESTResponse.OK, "Registro finalizado exitosamente.", null);
	}

	/*
	 ** Update the specified resource in storage partially.
	 */
	@PatchMapping
	public RESTResponse<User> patch(@RequestBody RESTRequest<User> req) {
		try {
			User dbUserById = userService.getOne(req.getPayload().getId());
			if (!req.getPayload().getEmail().equalsIgnoreCase(dbUserById.getEmail())){
				if(userService.getByEmail(req.getPayload().getEmail()) != null)
					return new RESTResponse<User>(RESTResponse.FAIL, "El usuario ya existe en el sistema.", null);
			}
			humanResourceService.update(req.getPayload().getHumanResource());
			req.getPayload().getHumanResource()
					.setId(humanResourceService.update(req.getPayload().getHumanResource()).getId());
			userService.update(req.getPayload());
		} catch (Exception e) {
			Logger.getLogger(null).log(null,"F: ",e);
			return new RESTResponse<User>(RESTResponse.FAIL,
					"Hubo un error al modificar. Por favor, intentelo mas tarde.", null);
		}
		return new RESTResponse<User>(RESTResponse.OK, "Los cambios se guardaron exitosamente.", null);
	}

	/*
	 ** Update the specified resource in storage.
	 */
	@PutMapping
	public RESTResponse<User> put(@RequestBody RESTRequest<User> req) {
		try {
			User dbUserById = userService.getOne(req.getPayload().getId());
			if (!req.getPayload().getEmail().equalsIgnoreCase(dbUserById.getEmail())){
				if(userService.getByEmail(req.getPayload().getEmail()) != null)
					return new RESTResponse<User>(RESTResponse.FAIL, "El usuario ya existe en el sistema.", null);
			}
			req.getPayload().getHumanResource()
					.setId(humanResourceService.update(req.getPayload().getHumanResource()).getId());
			userService.update(req.getPayload());
		} catch (Exception e) {
			Logger.getLogger(null).log(null,"F: ",e);
			return new RESTResponse<User>(RESTResponse.FAIL,
					"Hubo un error al modificar. Por favor, intentelo mas tarde.", null);
		}
		return new RESTResponse<User>(RESTResponse.OK, "Los cambios se guardaron exitosamente.", null);
	}

	/*
	 ** Remove the specified resource from storage.
	 */
	@DeleteMapping("/{id}")
	public RESTResponse<User> delete(@PathVariable Integer id) {
		User res;
		try {
			res = userService.getOne(id);
			if (res != null) {
				res.setAccountBlocked(true);
				userService.update(res);
			} else {
				return new RESTResponse<User>(RESTResponse.FAIL, "Usuario no registrado.", null);
			}
		} catch (Exception e) {
			Logger.getLogger(null).log(null,"F: ",e);
			return new RESTResponse<User>(RESTResponse.FAIL, "Por el momento no se puede realizar el registro.", null);
		}
		return new RESTResponse<User>(RESTResponse.OK, "Los cambios se guardaron exitosamente.", null);
	}

	@GetMapping("/usersByWorkplaceIdAndPositionId/{idW}/{idP}")
	public RESTResponse<List<User>> getUsersByWorkplaceIdAndPositionId(@PathVariable Integer idW,
			@PathVariable Integer idP) {
		List<User> res = userService.getUsersByWorkplaceIdAndPositionId(idW, idP);
		if (!res.isEmpty()) {
			return new RESTResponse<List<User>>(RESTResponse.OK, "", res);
		} else {
			return new RESTResponse<List<User>>(RESTResponse.FAIL, "No hay usuarios registrados con ese cargo.", res);
		}
	}

	@PostMapping("/userByEmailAndPassword")
	public RESTResponse<User> getUserByEmailAndPassword(@RequestBody RESTRequest<User> req) {
		List<User> res;
		try {
			res = userService.getUserByEmailAndPassword(req.getPayload().getEmail(), req.getPayload().getPassword());
		} catch (Exception e) {
			Logger.getLogger(null).log(null,"F: ",e);
			return new RESTResponse<User>(RESTResponse.DBFAIL, "Inconsistencia en la base de datos.", null);
		}
		if (!res.isEmpty()) {
			return new RESTResponse<User>(RESTResponse.OK, "", res.get(0));
		} else {
			return new RESTResponse<User>(RESTResponse.FAIL, "Correo y/o contraseña no válidas.", null);
		}
	}

	@GetMapping("/usersByWorkplaceId/{id}")
	public RESTResponse<List<User>> getUsersByWorkplaceId(@PathVariable Integer id) {
		List<User> res;
		try {
			res = userService.getUsersByWorkplaceId(id);
		} catch (Exception e) {
			Logger.getLogger(null).log(null,"F: ",e);
			return new RESTResponse<List<User>>(RESTResponse.DBFAIL, "Inconsistencia en la base de datos.", null);
		}
		if (!res.isEmpty()) {
			return new RESTResponse<List<User>>(RESTResponse.OK, "", res);
		} else {
			return new RESTResponse<List<User>>(RESTResponse.FAIL, "Servicios no disponibles.", null);
		}
	}

	@GetMapping("/userByEmail/{email}")
	public RESTResponse<User> userByEmail(@PathVariable String email) {
		User res;
		try {
			res = userService.getByEmail(email);
		} catch (Exception e) {
			Logger.getLogger(null).log(null,"F: ",e);
			return new RESTResponse<User>(RESTResponse.DBFAIL, "Inconsistencia en la base de datos.", null);
		}
		if (res != null) {
			return new RESTResponse<User>(RESTResponse.OK, "", res);
		} else {
			return new RESTResponse<User>(RESTResponse.FAIL,
					"El correo ingresado no se encuentra registrado en el sistema.", null);
		}
	}

	@GetMapping("/userByName/{name}/{first_surname}/{second_surname}")
	public RESTResponse<User> userByName(@PathVariable String name, @PathVariable String first_surname,
			@PathVariable String second_surname) {
		User res;
		try {
			res = userService.getOne(name, first_surname, second_surname);
		} catch (Exception e) {
			Logger.getLogger(null).log(null,"F: ",e);
			return new RESTResponse<User>(RESTResponse.DBFAIL, "Inconsistencia en la base de datos.", null);
		}
		if (res != null) {
			return new RESTResponse<User>(RESTResponse.OK, "", res);
		} else {
			return new RESTResponse<User>(RESTResponse.FAIL, "Usuario no registrado.", null);
		}
	}

	@GetMapping("/activeUsers")
	public RESTResponse<List<User>> activeUsers() {
		List<User> res;
		try {
			res = userService.getActiveUsers();
		} catch (Exception e) {
			Logger.getLogger(null).log(null,"F: ",e);
			return new RESTResponse<List<User>>(RESTResponse.DBFAIL, "Inconsistencia en la base de datos.", null);
		}
		if (!res.isEmpty()) {
			return new RESTResponse<List<User>>(RESTResponse.OK, "", res);
		} else {
			return new RESTResponse<List<User>>(RESTResponse.FAIL, "Usuario no registrado.", null);
		}
	}

	@GetMapping("/activeUsersForUser/{id}")
	public RESTResponse<List<User>> activeUsersForUser(@PathVariable Integer id) {
		User res;
		try {
			res = userService.getOne(id);
		} catch (Exception e) {
			Logger.getLogger(null).log(null,"F: ",e);
			return new RESTResponse<List<User>>(RESTResponse.DBFAIL, "Inconsistencia en la base de datos.", null);
		}
		if (res != null) {
			List<User> aux;
			try {
				if (res.getHumanResource().getWorkplace().getAbbreviation().equalsIgnoreCase("DES")) {
					aux = userService.getActiveUsersForUserForDES(id);
				} else {
					aux = userService.getActiveUsersForUser(id, res.getHumanResource().getWorkplace().getId());
				}
				System.out.println(6);
			} catch (Exception e) {
				Logger.getLogger(null).log(null,"F: ",e);
				return new RESTResponse<List<User>>(RESTResponse.DBFAIL, "Inconsistencia en la base de datos.", null);
			}
			if (!aux.isEmpty()) {
				return new RESTResponse<List<User>>(RESTResponse.OK, "", aux);
			} else {
				return new RESTResponse<List<User>>(RESTResponse.OK, "", null);
			}
		} else {
			return new RESTResponse<List<User>>(RESTResponse.FAIL, "Usuario no registrado.", null);
		}
	}

	@GetMapping("/activeUsersForUserByRole/{id}/{id_role}")
	public RESTResponse<List<User>> activeUsersForUserByRole(@PathVariable Integer id, @PathVariable Integer id_role) {
		User res;
		try {
			res = userService.getOne(id);
		} catch (Exception e) {
			Logger.getLogger(null).log(null,"F: ",e);
			return new RESTResponse<List<User>>(RESTResponse.DBFAIL, "Inconsistencia en la base de datos.", null);
		}
		if (res != null) {
			List<User> aux;
			try {
				if (res.getHumanResource().getWorkplace().getAbbreviation().equalsIgnoreCase("DES")) {
					aux = userService.getActiveUsersForUserByRoleForDES(id_role);
				} else {
					aux = userService.getActiveUsersForUserByRole(res.getHumanResource().getWorkplace().getId(),
							id_role);
				}
			} catch (Exception e) {
				Logger.getLogger(null).log(null,"F: ",e);
				return new RESTResponse<List<User>>(RESTResponse.DBFAIL, "Inconsistencia en la base de datos.", null);
			}
			if (!aux.isEmpty()) {
				return new RESTResponse<List<User>>(RESTResponse.OK, "", aux);
			} else {
				return new RESTResponse<List<User>>(RESTResponse.FAIL, "No hay usuarios registrados con ese cargo.",
						null);
			}
		} else {
			return new RESTResponse<List<User>>(RESTResponse.FAIL, "Usuario no registrado.", null);
		}
	}
}
