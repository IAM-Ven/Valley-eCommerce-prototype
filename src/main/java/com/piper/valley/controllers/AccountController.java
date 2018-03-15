package com.piper.valley.controllers;

import com.piper.valley.auth.CurrentUser;
import com.piper.valley.forms.AddStoreForm;
import com.piper.valley.forms.UserCreateForm;
import com.piper.valley.models.service.StoreService;
import com.piper.valley.models.service.UserService;
import com.piper.valley.validators.AddStoreFormValidator;
import com.piper.valley.validators.UserCreateFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class AccountController {
	/////////////////////////*  SERVICES, REPOSITORIES AND VALIDATORS SECTION  */////////////////////////////
	@Autowired
	private UserService userService;

	@Autowired
	private StoreService storeService;

	@Autowired
	private UserCreateFormValidator userCreateFormValidator;

	@Autowired
	private AddStoreFormValidator addStoreFormValidator;
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////*  VALIDATORS BINDING SECTION  *//////////////////////////////////////
	@InitBinder("addStoreForm")
	public void addStoreFormInitBinder(WebDataBinder binder) {
		binder.addValidators(addStoreFormValidator);
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////


	@InitBinder("registerForm")
	public void registerFormInitBinder(WebDataBinder binder) {
		binder.addValidators(userCreateFormValidator);
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////*  CONTROLLER ACTION  *///////////////////////////////////////////

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView getLoginPage(@RequestParam Optional<String> error) {
		return new ModelAndView("user/login", "error", error.isPresent() ? error : null);
	}


	//@PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #pathVariable)")
	//@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register(@ModelAttribute("registerForm") UserCreateForm registerForm) {
		return new ModelAndView("user/register", "registerForm", registerForm);
	}

	//@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView register(@Valid @ModelAttribute("registerForm") UserCreateForm registerForm, BindingResult bindingResult, HttpServletRequest request) {
		if (bindingResult.hasErrors())
			return new ModelAndView("user/register", "registerForm", registerForm);

		//Save to DB
		userService.register(registerForm);

		//Login
		try {
			request.changeSessionId();
			request.login(registerForm.getUsername(), registerForm.getPassword());
		} catch (ServletException e) {
			e.printStackTrace();
		}

		return new ModelAndView("redirect:/");
	}
	//@PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #pathVariable)")
	//@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/add/store", method = RequestMethod.GET)
	public ModelAndView addStore(@ModelAttribute("addStoreForm") AddStoreForm addStoreForm) {
		return new ModelAndView("user/addstore", "addStoreForm", addStoreForm);
	}

	//@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/add/store", method = RequestMethod.POST)
	public ModelAndView addStore(@Valid @ModelAttribute("addStoreForm") AddStoreForm addStoreForm, BindingResult bindingResult, HttpServletRequest request, CurrentUser currentUser) {
		if (bindingResult.hasErrors())
			return new ModelAndView("user/addstore", "addStoreForm", addStoreForm);

		//Save to DB
		storeService.add(addStoreForm,currentUser);

		return new ModelAndView("redirect:/");
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
}
