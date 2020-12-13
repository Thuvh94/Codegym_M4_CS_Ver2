package com.codegym.case4.controller;

import com.codegym.case4.model.*;
import com.codegym.case4.service.Category.ICategoryService;
import com.codegym.case4.service.Request.IRequestService;
import com.codegym.case4.service.User.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

@Controller
@RequestMapping("/client/request")
public class RequestController {

    @Autowired
    IUserService userService;

    @Autowired
    ICategoryService categoryService;

    @Autowired
    IRequestService requestService;

    @Value("${upload.path}")
    private String fileUpload;

    @ModelAttribute("allCategories")
    public Iterable<Category> getAllCategories() {
        return categoryService.findAll();
    }

    @GetMapping("/list")
    public ModelAndView listRequest(@PageableDefault(size = 5) Pageable pageable) {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(userPrincipal.getUsername());
        Page<Request> requests = requestService.findEachUserRequest(user.getUserId(), pageable);
        ModelAndView modelAndView = new ModelAndView("/request/listClient");
        modelAndView.addObject("requests", requests);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCreateForm() {
        RequestForm requestForm = new RequestForm();
        ModelAndView modelAndView = new ModelAndView("/request/create");
        modelAndView.addObject("requestForm", requestForm);
        return modelAndView;
    }

    @PostMapping("/create")
    public RedirectView saveRequest(@ModelAttribute("requestForm") RequestForm requestForm) {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(userPrincipal.getUsername());
        Request request = new Request(requestForm.getRequestId(), user, requestForm.getTitle(),
                null, requestForm.getDescription(), requestForm.getPublishedDate(), requestForm.getPages(), requestForm.getCategories(),
                requestForm.getAuthor(), requestForm.getRequestStatus(), LocalDate.now());
        MultipartFile multipartFile = requestForm.getCoverImg();
        String fileName = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(requestForm.getCoverImg().getBytes(), new File(this.fileUpload + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        request.setCoverImg(fileName);
        System.out.println(request);
        requestService.save(request);
        return new RedirectView("/client/request/list");
    }
}
