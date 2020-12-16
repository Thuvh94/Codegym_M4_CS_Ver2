package com.codegym.case4.controller;

import com.codegym.case4.model.*;
import com.codegym.case4.service.Author.IAuthorService;
import com.codegym.case4.service.Book.IBookService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

@Controller
public class RequestController {

    @Autowired
    IUserService userService;

    @Autowired
    IBookService bookService;

    @Autowired
    ICategoryService categoryService;

    @Autowired
    IAuthorService authorService;

    @Autowired
    IRequestService requestService;

    @Value("${upload.path}")
    private String fileUpload;

    @ModelAttribute("allCategories")
    public Iterable<Category> getAllCategories() {
        return categoryService.findAll();
    }

    @ModelAttribute("allAuthors")
    public Page<Author> getAllAuthors(Pageable pageable) {
        return authorService.findAll(pageable);
    }

    @GetMapping("/client/request/list")
    public ModelAndView listRequestEachClient(@PageableDefault(size = 5) Pageable pageable) {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(userPrincipal.getUsername());
        Page<Request> requests = requestService.findEachUserRequest(user.getUserId(), pageable);
        ModelAndView modelAndView = new ModelAndView("/request/listClient");
        modelAndView.addObject("requests", requests);
        return modelAndView;
    }

    @GetMapping("/client/request/create")
    public ModelAndView showCreateForm() {
        RequestForm requestForm = new RequestForm();
        ModelAndView modelAndView = new ModelAndView("/request/create");
        modelAndView.addObject("requestForm", requestForm);
        return modelAndView;
    }

//    @PostMapping("/client/request/create")
//    public RedirectView saveRequest(@ModelAttribute("requestForm") RequestForm requestForm) {
//        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        User user = userService.findByUsername(userPrincipal.getUsername());
//        Request request = new Request(requestForm.getRequestId(), user, requestForm.getTitle(),
//                null, requestForm.getDescription(), requestForm.getPublishedDate(), requestForm.getPages(), requestForm.getCategories(),
//                requestForm.getAuthor(), requestForm.getRequestStatus(), LocalDate.now());
//        MultipartFile multipartFile = requestForm.getCoverImg();
//        String fileName = multipartFile.getOriginalFilename();
//        try {
//            FileCopyUtils.copy(requestForm.getCoverImg().getBytes(), new File(this.fileUpload + fileName));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        request.setCoverImg(fileName);
//        System.out.println(request);
//        requestService.save(request);
//
//        return new RedirectView("/client/request/create");
//    }

    @PostMapping("/client/request/create")
    public ModelAndView saveRequest(@ModelAttribute("requestForm") RequestForm requestForm) {
        ModelAndView modelAndView = new ModelAndView("/request/listClient");
        modelAndView.addObject("message", "New request created successfully");
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
        modelAndView.addObject("requestForm",new RequestForm());
        return modelAndView;
    }

    @GetMapping("/admin/request")
    public ModelAndView listRequestAdmin(@PageableDefault(size = 5) Pageable pageable) {
        Page<Request> requests = requestService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("/request/listAdmin");
        modelAndView.addObject("requests", requests);
        return modelAndView;
    }

    @GetMapping("/admin/request/new")
    public ModelAndView listNewRequestAdmin(@PageableDefault(size = 5) Pageable pageable) {
        Page<Request> requests = requestService.findAllByRequestStatusIs0(pageable);
        ModelAndView modelAndView = new ModelAndView("/request/listAdmin");
        modelAndView.addObject("requests", requests);
        return modelAndView;
    }

//    @GetMapping("/admin/request/inprogress")
//    public ModelAndView listProgressRequestAdmin(@PageableDefault(size = 5) Pageable pageable) {
//        Page<Request> requests = requestService.findAllByRequestStatusIs1(pageable);
//        ModelAndView modelAndView = new ModelAndView("/request/listAdmin");
//        modelAndView.addObject("requests", requests);
//        return modelAndView;
//    }

    @GetMapping("/admin/request/done")
    public ModelAndView listDoneRequestAdmin(@PageableDefault(size = 5) Pageable pageable) {
        Page<Request> requests = requestService.findAllByRequestStatusIs2(pageable);
        ModelAndView modelAndView = new ModelAndView("/request/listAdmin");
        modelAndView.addObject("requests", requests);
        return modelAndView;
    }

    @GetMapping("/admin/request/reject")
    public ModelAndView listRejectRequestAdmin(@PageableDefault(size = 5) Pageable pageable) {
        Page<Request> requests = requestService.findAllByRequestStatusIs3(pageable);
        ModelAndView modelAndView = new ModelAndView("/request/listAdmin");
        modelAndView.addObject("requests", requests);
        return modelAndView;
    }

    @GetMapping("/admin/request/addBook/{requestId}")
    public ModelAndView showAddRequestForm(@PathVariable Long requestId) {
        Request request = requestService.findById(requestId).get();
        BookForm bookForm = new BookForm(null, null, request.getTitle(), request.getDescription(),
                false, request.getPublishedDate(), request.getPages(), null, null);
        ModelAndView modelAndView = new ModelAndView("/request/addRequest");
        modelAndView.addObject("bookForm", bookForm);
        modelAndView.addObject("coverImgLink", request.getCoverImg());
        modelAndView.addObject("requestCategory", request.getCategories());
        modelAndView.addObject("requestAuthor", request.getAuthor());
        modelAndView.addObject("requestId", requestId);
        return modelAndView;
    }

    @PostMapping("/admin/request/addBook")
    public RedirectView addRequest(@ModelAttribute BookForm bookForm, @ModelAttribute("requestId") Long requestId) {
        MultipartFile multipartFile = bookForm.getCoverImg();
        String fileName = multipartFile.getOriginalFilename();
        Book editedBook = new Book(bookForm.getBookId(),fileName, bookForm.getTitle(), bookForm.getDescription(), bookForm.isDeleted(),
                bookForm.getPublishedDate(), bookForm.getPages(), bookForm.getCategories(), bookForm.getAuthorId());
        if (fileName.equals("")) {
            Request request = requestService.findById(requestId).get();
            fileName = request.getCoverImg();
            editedBook.setCoverImg(fileName);
        } else {
            try {
                FileCopyUtils.copy(bookForm.getCoverImg().getBytes(), new File(this.fileUpload + fileName));
                editedBook.setCoverImg(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        bookService.save(editedBook);
        System.out.println(editedBook);
        Request request = requestService.findById(requestId).get();
        request.setRequestStatus(2);
        requestService.save(request);
        return new RedirectView("/admin/request");
    }

    @GetMapping("/admin/request/rejectBook/{requestId}")
    public RedirectView rejectBook(@PathVariable Long requestId) {
        Request request = requestService.findById(requestId).get();
        request.setRequestStatus(3);
        requestService.save(request);
        return new RedirectView("/admin/request");
    }

}
