package com.kanezi.blockingvsreactive.mvcsearch;

import lombok.Value;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Value
@RequestMapping("/")
public class SearchController {

    SearchService searchService;

    @GetMapping
    String showSearchForm() {
        return "search";
    }

    @PostMapping
    String search(@RequestParam String q, RedirectAttributes attributes) {

        attributes.addFlashAttribute("results", searchService.search(q));

        return "redirect:/";
    }

    @PostMapping("/search")
    String partialSearch(@RequestParam String q, Model model) {
        model.addAttribute("results", searchService.search(q));
        return "search::#search_result_cards";
    }
}
