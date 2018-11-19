package com.apms.thematicUnit;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThematicUnitService {

	@Autowired
	private ThematicUnitRepository thematicUnitRepository;

	public List<ThematicUnit> getAll() {
		List<ThematicUnit> records = new ArrayList<>();
		thematicUnitRepository.findAll().forEach(records::add);
		return records;
	}

	public ThematicUnit getOne(Integer id) {
		return thematicUnitRepository.findById(id).isPresent() ? thematicUnitRepository.findById(id).get() : null;
	}

	public void add(ThematicUnit thematicUnit) {
		thematicUnitRepository.save(thematicUnit);
	}

	public void update(ThematicUnit thematicUnit) {
		// if exists updates otherwise inserts
		thematicUnitRepository.save(thematicUnit);
	}

	public void delete(Integer id) {
		thematicUnitRepository.deleteById(id);
	}
}