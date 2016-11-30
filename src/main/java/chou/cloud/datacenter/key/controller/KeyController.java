package chou.cloud.datacenter.key.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import chou.cloud.datacenter.key.entity.Key;
import chou.cloud.datacenter.key.service.KeyService;

/**
 * Created by Koala Cheung on 2016/10/31.
 */
@RestController
@RequestMapping("/keys")
public class KeyController {
	@Autowired
	KeyService keyService;

	@RequestMapping(method = RequestMethod.GET)
	List<Key> getKeys() {
		return keyService.findAll();
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	Key insertKey(@Validated @RequestBody Key key) {
		return keyService.save(key);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	Key updateKey(@PathVariable("id") Long id, @Validated @RequestBody Key key) {
		key.setId(id);
		return keyService.save(key);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	void deleteKey(@PathVariable("id") Long id) {
		keyService.delete(id);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	Key getKey(@PathVariable("id") Long id) {
		return keyService.find(id);
	}
}
